package com.example.serviceappjava.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {ListItem.class, ReminderItem.class}, version = 2)
abstract public class ItemsDatabase extends RoomDatabase{
    abstract ListItemDAO listItemDAO();
    abstract ReminderItemDAO reminderItemDAO();
}
