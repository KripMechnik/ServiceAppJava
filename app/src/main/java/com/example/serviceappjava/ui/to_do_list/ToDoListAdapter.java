package com.example.serviceappjava.ui.to_do_list;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.example.serviceappjava.R;
import com.example.serviceappjava.data.ListItem;
import com.example.serviceappjava.databinding.ListItemBinding;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.ItemsViewHolder> {
    private List<ListItem> items;
    private ToDoListViewModel viewModel;
    private Context context;

    public ToDoListAdapter(List<ListItem> items, ToDoListViewModel viewModel, Context context) {
        this.items = items;
        this.viewModel = viewModel;
        this.context = context;
    }

    public class ItemsViewHolder extends RecyclerView.ViewHolder {
        private ListItemBinding binding;

        public ItemsViewHolder(ListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint("ResourceAsColor")
        public void set(ListItem item) {
            binding.tvTitle.setText(item.getTitle());
            binding.tvDescription.setText(item.getDescription());
            if (item.isColored()) {
                binding.card.setCardBackgroundColor(context.getColor(R.color.md_theme_inversePrimary));
            }
            binding.btnDelete.setOnClickListener(view -> viewModel.delete(item));
            binding.btnDone.setOnClickListener(view -> {
                item.colored = !item.colored;

                viewModel.update(item);
            });
        }
    }

    @NotNull
    @Override
    public ItemsViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        return new ItemsViewHolder(ListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onBindViewHolder(ItemsViewHolder holder, int position) {
        holder.set(items.get(position));
    }
}
