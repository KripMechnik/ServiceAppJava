package com.example.serviceappjava.ui.reminder;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.*;
import android.widget.CompoundButton;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.example.serviceappjava.R;
import com.example.serviceappjava.data.ListItem;
import com.example.serviceappjava.data.ReminderItem;
import com.example.serviceappjava.databinding.ListItemBinding;
import com.example.serviceappjava.databinding.ReminderItemBinding;
import com.example.serviceappjava.ui.reminder.reminder_tools.AndroidReminderScheduler;
import com.example.serviceappjava.ui.to_do_list.ToDoListAdapter;
import com.example.serviceappjava.ui.to_do_list.ToDoListViewModel;
import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ItemsViewHolder> {

    private List<ReminderItem> items;
    private AndroidReminderScheduler scheduler;
    private ReminderViewModel viewModel;

    private FragmentActivity context;
    public ReminderAdapter(List<ReminderItem> items, ReminderViewModel viewModel, AndroidReminderScheduler scheduler, FragmentActivity context) {
        this.items = items;
        this.viewModel = viewModel;
        this.scheduler = scheduler;
        this.context = context;
    }

    @NotNull
    @Override
    public ItemsViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int i) {
        return new ReminderAdapter.ItemsViewHolder(ReminderItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ItemsViewHolder itemsViewHolder, int i) {
        itemsViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                setPosition(itemsViewHolder.getLayoutPosition());
                return false;
            }
        });

        itemsViewHolder.set(items.get(i));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ItemsViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        private ReminderItemBinding binding;

        public ItemsViewHolder(ReminderItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void set(ReminderItem item) {
            binding.getRoot().setOnCreateContextMenuListener(this);
            Date date = new Date();
            String[] parts = item.time.split(",");
            Calendar cal = Calendar.getInstance();
            int hours = cal.get(Calendar.HOUR_OF_DAY);
            int minutes = cal.get(Calendar.MINUTE);
            if (item.checked){
                if(Integer.parseInt(parts[0]) >= hours && Integer.parseInt(parts[1]) > minutes){
                    scheduler.schedule(item);
                }
                binding.checkItem.setChecked(true);
            } else {
                scheduler.cancel(item);
                binding.checkItem.setChecked(false);
            }
            binding.checkItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (item.isChecked() != isChecked){
                        item.checked = isChecked;
                        viewModel.update(item);
                    }

                }
            });
            binding.tvTitle.setText(item.title);
            if (parts[0].length()<2){
                parts[0] = "0" + parts[0];
            }
            if (parts[1].length()<2){
                parts[1] = "0" + parts[1];
            }
            binding.tvTime.setText(parts[0] + ":" + parts[1]);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            contextMenu.add(ContextMenu.NONE, 0, ContextMenu.NONE, "Удалить");
        }
    }
    public void setList(List<ReminderItem> items){
        this.items = items;
    }

    public void deleteItem(int pos){
        viewModel.delete(items.get(pos));
    }


    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
