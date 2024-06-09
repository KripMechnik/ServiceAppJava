package com.example.serviceappjava.data;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import java.util.List;

@Dao
public interface ReminderItemDAO {
    @Insert
    public Long insertItem(ReminderItem item);

    @Update
    public void updateItem(ReminderItem item);

    @Delete
    public void deleteItem(ReminderItem item);

    @Query("DELETE FROM reminder_list_data_table")
    public void deleteAll();

    @Query("SELECT * FROM reminder_list_data_table")
    public LiveData<List<ReminderItem>> getAllItems();

    @Query("UPDATE reminder_list_data_table SET item_checked = 1")
    public void checkAll();
}
