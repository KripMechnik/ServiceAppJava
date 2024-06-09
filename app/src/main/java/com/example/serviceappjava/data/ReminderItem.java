package com.example.serviceappjava.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Calendar;
import java.util.List;

@Entity(tableName = "reminder_list_data_table")
public class ReminderItem {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "item_id")
    public int id;

    @ColumnInfo(name = "item_title")
    public String title;

    @ColumnInfo(name = "item_time")
    public String time;

    @ColumnInfo(name = "item_checked")
    public boolean checked;


    public ReminderItem(int id, String title, String time, Boolean checked) {
        this.id = id;
        this.title = title;
        this.time = time;
        this.checked = checked;
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

    public Calendar getTime() {
        Calendar calendar = Calendar.getInstance();
        String[] time_sets = this.time.trim().split(",");
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time_sets[0]) % 24);
        calendar.set(Calendar.MINUTE, Integer.parseInt(time_sets[1]));
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    public void setTime(Calendar time) {
        this.time = time.get(Calendar.HOUR_OF_DAY) + "," + time.get(Calendar.MINUTE);
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
