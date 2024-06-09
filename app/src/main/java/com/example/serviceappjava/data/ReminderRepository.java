package com.example.serviceappjava.data;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.room.Room;

import java.util.List;

public interface ReminderRepository {
    LiveData<List<ReminderItem>> getAllItems();
    long insertItem(ReminderItem item);
    void updateItem(ReminderItem item);
    void deleteItem(ReminderItem item);
    void deleteAllItems();
    void checkAll();

    class Base implements ReminderRepository {
        private static final String DATABASE_NAME = "to_do_list_database";
        private ItemsDatabase room;
        private ReminderItemDAO dao;

        public Base(Context context) {
            room = Room.databaseBuilder(context, ItemsDatabase.class, DATABASE_NAME).build();
            dao = room.reminderItemDAO();
        }
        @Override
        public LiveData<List<ReminderItem>> getAllItems() {
            return dao.getAllItems();
        }
        @Override
        public long insertItem(ReminderItem item) {
            return dao.insertItem(item);
        }
        @Override
        public void updateItem(ReminderItem item) {
            dao.updateItem(item);
        }
        @Override
        public void deleteItem(ReminderItem item) {
            dao.deleteItem(item);
        }
        @Override
        public void deleteAllItems() {
            dao.deleteAll();
        }
        @Override
        public void checkAll() {
            dao.checkAll();
        }
    }
}
