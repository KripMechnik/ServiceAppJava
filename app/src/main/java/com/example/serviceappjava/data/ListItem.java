package com.example.serviceappjava.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "to_do_list_data_table")
public class ListItem {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "item_id")
    public int id;

    @ColumnInfo(name = "item_title")
    public String title;

    @ColumnInfo(name = "item_description")
    public String description;

    @ColumnInfo(name = "item_colored")
    public boolean colored;


    public ListItem(int id, String title, String description, Boolean colored) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.colored = colored;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isColored() {
        return colored;
    }

    public void setColored(boolean colored) {
        this.colored = colored;
    }
}

