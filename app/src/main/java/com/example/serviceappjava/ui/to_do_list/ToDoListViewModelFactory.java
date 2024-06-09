package com.example.serviceappjava.ui.to_do_list;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.example.serviceappjava.data.ItemsRepository;
import org.jetbrains.annotations.NotNull;

public class ToDoListViewModelFactory implements ViewModelProvider.Factory {
    private ItemsRepository repository;

    public ToDoListViewModelFactory(ItemsRepository repository) {
        this.repository = repository;
    }

    @NotNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ToDoListViewModel.class)) {
            return (T) new ToDoListViewModel(repository);
        } else {
            throw new IllegalArgumentException("Unknown View Model class");
        }
    }
}
