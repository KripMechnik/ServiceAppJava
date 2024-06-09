package com.example.serviceappjava.data;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.room.Room;

import java.util.List;

public interface ItemsRepository {
    LiveData<List<ListItem>> getAllItems();
    long insertItem(ListItem item);
    void updateItem(ListItem item);
    void deleteItem(ListItem item);
    void deleteAllItems();
    void checkAll();

    class Base implements ItemsRepository {
        private static final String DATABASE_NAME = "to_do_list_database";
        private ItemsDatabase room;
        private ListItemDAO dao;

        public Base(Context context) {
            room = Room.databaseBuilder(context, ItemsDatabase.class, DATABASE_NAME).build();
            dao = room.listItemDAO();
        }

        @Override
        public LiveData<List<ListItem>> getAllItems() {
            return dao.getAllItems();
        }

        @Override
        public long insertItem(ListItem item) {
            return dao.insertItem(item);
        }

        @Override
        public void updateItem(ListItem item) {
            dao.updateItem(item);
        }

        @Override
        public void deleteItem(ListItem item) {
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
