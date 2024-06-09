package com.example.serviceappjava.ui.reminder;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.example.serviceappjava.data.ItemsRepository;
import com.example.serviceappjava.data.ReminderRepository;
import com.example.serviceappjava.ui.to_do_list.ToDoListViewModel;
import org.jetbrains.annotations.NotNull;

public class ReminderViewModelFactory implements ViewModelProvider.Factory {
    private ReminderRepository repository;

    public ReminderViewModelFactory(ReminderRepository repository) {
        this.repository = repository;
    }

    @NotNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ReminderViewModel.class)) {
            return (T) new ReminderViewModel(repository);
        } else {
            throw new IllegalArgumentException("Unknown View Model class");
        }
    }
}
