package com.example.serviceappjava.ui.to_do_list;

import android.os.Handler;
import android.os.Looper;
import androidx.lifecycle.*;
import com.example.serviceappjava.data.ItemsRepository;
import com.example.serviceappjava.data.ListItem;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ToDoListViewModel extends ViewModel {
    private ItemsRepository repository;
    private MutableLiveData<ListItem> item = new MutableLiveData<>();

    public ToDoListViewModel(ItemsRepository repository) {
        this.repository = repository;
    }

    public MutableLiveData<ListItem> getItem() {
        return item;
    }

    public void observeItems(LifecycleOwner owner, Observer<List<ListItem>> observer) {
        repository.getAllItems().observe(owner, observer);
    }

    public void save() {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(new Runnable() {
            @Override
            public void run() {
                repository.insertItem(new ListItem(0, item.getValue().getTitle(), item.getValue().getDescription(), false));
            }
        });




    }

    public void delete(ListItem item) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(new Runnable() {
            @Override
            public void run() {
                repository.deleteItem(item);
            }
        });

    }

    public void update(ListItem item) {
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