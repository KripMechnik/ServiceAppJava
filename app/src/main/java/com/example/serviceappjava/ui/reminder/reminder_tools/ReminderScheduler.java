package com.example.serviceappjava.ui.reminder.reminder_tools;

import com.example.serviceappjava.data.ReminderItem;

interface ReminderScheduler {
    void schedule(ReminderItem item);
    void cancel(ReminderItem item);
}