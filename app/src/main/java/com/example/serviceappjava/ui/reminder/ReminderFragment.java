package com.example.serviceappjava.ui.reminder;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.*;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import androidx.lifecycle.ViewModelProvider;
import com.example.serviceappjava.R;
import com.example.serviceappjava.data.ItemsRepository;
import com.example.serviceappjava.data.ReminderItem;
import com.example.serviceappjava.data.ReminderRepository;
import com.example.serviceappjava.databinding.FragmentReminderBinding;
import com.example.serviceappjava.ui.reminder.reminder_tools.AndroidReminderScheduler;
import com.example.serviceappjava.ui.to_do_list.ToDoListAdapter;
import com.example.serviceappjava.ui.to_do_list.ToDoListViewModel;
import com.example.serviceappjava.ui.to_do_list.ToDoListViewModelFactory;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

public class ReminderFragment extends Fragment {

    private FragmentReminderBinding binding;
    private MaterialButton addBtn;
    private MaterialButton cancelBtn;
    private MaterialTimePicker timePicker;
    private Calendar calendar;
    private ReminderAdapter adapter;

    private ReminderViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ReminderViewModelFactory factory = new ReminderViewModelFactory(new ReminderRepository.Base(requireContext()));
        viewModel = new ViewModelProvider(this, factory).get(ReminderViewModel.class);

        binding = FragmentReminderBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        requireActivity().getWindow().setNavigationBarColor(getResources().getColor(R.color.md_theme_surfaceContainer));


        AndroidReminderScheduler scheduler = new AndroidReminderScheduler(requireContext());
        adapter = new ReminderAdapter(null, viewModel, scheduler, requireActivity());
        addBtn = binding.btnAddReminder;

        viewModel.observeItems(this, items -> {

            if (!items.isEmpty()){
                adapter.setList(items);
                binding.reminderRecycler.setAdapter(adapter);
                binding.reminderRecycler.setVisibility(View.VISIBLE);
                binding.cardTip.setVisibility(View.GONE);
            } else {
                binding.reminderRecycler.setVisibility(View.GONE);
                binding.cardTip.setVisibility(View.VISIBLE);
            }

        });

        addBtn.setOnClickListener(v -> {


            timePicker = new MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_24H)
                    .setHour(12)
                    .setMinute(0)
                    .setTitleText("Выберите время")
                    .build();
            timePicker.addOnPositiveButtonClickListener(dialog -> {
                calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                calendar.set(Calendar.MINUTE, timePicker.getMinute());
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);

                ReminderItem item = new ReminderItem(0, binding.etAction.getText().toString(), calendar.get(Calendar.HOUR_OF_DAY) + "," + calendar.get(Calendar.MINUTE), true);


                viewModel.getItem().setValue(item);
                viewModel.save();
                Toast.makeText(requireContext(), "Напоминание установлено", Toast.LENGTH_SHORT).show();
                binding.etAction.setText("");
            });
            timePicker.show(requireActivity().getSupportFragmentManager(), "androidknowledge");

        });
    }

    @Override
    public boolean onContextItemSelected(@NonNull @NotNull MenuItem item) {
        int position;
        position = adapter.getPosition();
        if (item.getItemId() == 0){
            adapter.deleteItem(position);
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onPause() {
        requireActivity().getWindow().setNavigationBarColor(getResources().getColor(R.color.md_theme_surface));
        super.onPause();
    }
}