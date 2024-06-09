package com.example.serviceappjava.ui.reminder;

import android.util.Log;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import com.example.serviceappjava.data.ItemsRepository;
import com.example.serviceappjava.data.ListItem;
import com.example.serviceappjava.data.ReminderItem;
import com.example.serviceappjava.data.ReminderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReminderViewModel extends ViewModel {
    private ReminderRepository repository;

    private MutableLiveData<ReminderItem> item = new MutableLiveData<>();

    public ReminderViewModel(ReminderRepository repository) {
        this.repository = repository;
    }

    public MutableLiveData<ReminderItem> getItem() {
        return item;
    }

    public void observeItems(LifecycleOwner owner, Observer<List<ReminderItem>> observer) {
        repository.getAllItems().observe(owner, observer);
    }

    public void save() {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(new Runnable() {
            @Override
            public void run() {
                repository.insertItem(new ReminderItem(0, item.getValue().getTitle(), item.getValue().time, item.getValue().isChecked()));
            }
        });




    }

    public void delete(ReminderItem item) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(new Runnable() {
            @Override
            public void run() {
                repository.deleteItem(item);
            }
        });

    }

    public void update(ReminderItem item) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                repository.updateItem(item);
            }
        });

    }

    public void deleteAll() {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(new Runnable() {
            @Override
            public void run() {
                repository.deleteAllItems();
            }
        });


    }

    public void checkAll() {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(new Runnable() {
            @Override
            public void run() {
                repository.checkAll();
            }
        });

    }
}