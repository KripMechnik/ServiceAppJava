package com.example.serviceappjava.ui.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import com.example.serviceappjava.R;
import com.google.android.material.textview.MaterialTextView;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

public class ChangeMaxDialog extends DialogFragment {

    private CircularProgressBar circulaPb;
    private MaterialTextView textView;

    public ChangeMaxDialog(CircularProgressBar circulaPb, MaterialTextView textView) {
        this.circulaPb = circulaPb;
        this.textView = textView;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.change_max_dialog, null);
        EditText editText = dialogView.findViewById(R.id.et_norm_steps);

        builder.setView(dialogView)
                .setPositiveButton("Подтвердить", (dialog, which) -> {
                    String inputText = editText.getText().toString();
                    textView.setText("Текущая цель: " + inputText + " шагов");
                    circulaPb.setProgressMax(Float.parseFloat(inputText));
                })
                .setNegativeButton("Отклонить", (dialog, which) -> dialog.cancel());

        return builder.create();
    }
}
