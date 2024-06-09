package com.example.serviceappjava.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import com.example.serviceappjava.R;
import com.example.serviceappjava.data.ListItem;
import com.example.serviceappjava.ui.to_do_list.ToDoListViewModel;

public class AddListItemDialog extends DialogFragment {
    private ToDoListViewModel toDoListViewModel;

    public AddListItemDialog(ToDoListViewModel toDoListViewModel) {
        this.toDoListViewModel = toDoListViewModel;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_item_dialog, null);
        EditText titleET = dialogView.findViewById(R.id.et_title);
        EditText descET = dialogView.findViewById(R.id.et_desc);

        builder.setView(dialogView)
                .setPositiveButton("Добавить", (dialog, which) -> {
                    toDoListViewModel.getItem().setValue(new ListItem(0, titleET.getText().toString(), descET.getText().toString(), false));
                    toDoListViewModel.save();
                })
                .setNegativeButton("Отменить", (dialog, which) -> dialog.cancel());

        return builder.create();
    }
}
