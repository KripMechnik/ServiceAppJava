package com.example.serviceappjava.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import android.Manifest;
import androidx.lifecycle.ViewModelProvider;
import com.example.serviceappjava.R;
import com.example.serviceappjava.databinding.FragmentHomeBinding;
import com.example.serviceappjava.ui.dialog.ChangeMaxDialog;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel homeViewModel;
    private SensorManager sensorManager;
    private ActivityResultLauncher<String> pLauncher;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Sensor stepSensor;
    private TextView tvSteps;
    private float totalSteps = 0f;

    private final SensorEventListener sensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event == null) return;
            totalSteps = event.values[0];
            homeViewModel.setSteps((int) totalSteps);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // Do nothing for now
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        registerPermissionListener();
        checkPermission();
        sharedPreferences = requireActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvSteps = binding.tvSteps;
        binding.circularPb.setProgressMax(sharedPreferences.getFloat("max", 5000));
        binding.tvAim.setText("Текущая цель: " + (int)binding.circularPb.getProgressMax() + " шагов");
        homeViewModel.previousSteps.setValue(sharedPreferences.getInt("steps", 0));

        homeViewModel.stepCounter.observe(getViewLifecycleOwner(), steps -> {
            tvSteps.setText(String.valueOf(steps - homeViewModel.previousSteps.getValue()));
            binding.circularPb.setProgressWithAnimation(steps - homeViewModel.previousSteps.getValue());
        });

        binding.btnResetSteps.setOnClickListener(v -> {
            homeViewModel.resetSteps();
            tvSteps.setText("0");
            binding.circularPb.setProgressWithAnimation(0f);
            if (homeViewModel.previousSteps.getValue() != null){
                editor.putInt("steps", homeViewModel.previousSteps.getValue());
            }
            editor.apply();
        });

        binding.btnChangeMax.setOnClickListener(v -> new ChangeMaxDialog(binding.circularPb, binding.tvAim).show(requireActivity().getSupportFragmentManager(), "InputDialog"));
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (stepSensor != null) {
            sensorManager.registerListener(sensorListener, stepSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(requireContext(), "Step counter sensor not available", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onPause() {
        editor.putFloat("max", binding.circularPb.getProgressMax());
        editor.apply();
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        sensorManager.unregisterListener(sensorListener);
        super.onDestroyView();
    }



    private void registerPermissionListener() {
        pLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), result -> {
            if (result) {
                Toast.makeText(requireContext(), "Разрешение дано", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(requireContext(), "Разрешение не дано", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_GRANTED) {
            // Permission granted
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.ACTIVITY_RECOGNITION)) {
            Toast.makeText(requireContext(), "Нужно разрешение", Toast.LENGTH_LONG).show();
        } else {
            pLauncher.launch(Manifest.permission.ACTIVITY_RECOGNITION);
        }
    }
}