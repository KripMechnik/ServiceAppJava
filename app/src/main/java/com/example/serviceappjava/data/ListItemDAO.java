package com.example.serviceappjava.data;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import java.util.List;

@Dao
public interface ListItemDAO {
    @Insert
    public Long insertItem(ListItem item);

    @Update
    public void updateItem(ListItem item);

    @Delete
    public void deleteItem(ListItem item);

    @Query("DELETE FROM to_do_list_data_table")
    public void deleteAll();

    @Query("SELECT * FROM to_do_list_data_table")
    public LiveData<List<ListItem>> getAllItems();

    @Query("UPDATE to_do_list_data_table SET item_colored = 1")
    public void checkAll();
}
