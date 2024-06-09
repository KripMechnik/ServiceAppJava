package com.example.serviceappjava.ui.to_do_list;

import android.os.Bundle;
import android.view.*;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.serviceappjava.R;
import com.example.serviceappjava.data.ItemsRepository;
import com.example.serviceappjava.databinding.FragmentToDoListBinding;
import com.example.serviceappjava.ui.dialog.AddListItemDialog;

public class ToDoListFragment extends Fragment {
    private ToDoListViewModel toDoListviewModel;
    private FragmentToDoListBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ToDoListViewModelFactory factory = new ToDoListViewModelFactory(new ItemsRepository.Base(requireContext()));
        toDoListviewModel = new ViewModelProvider(this, factory).get(ToDoListViewModel.class);
        setHasOptionsMenu(true);
        binding = FragmentToDoListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnAddItem.setOnClickListener(v -> {
            new AddListItemDialog(toDoListviewModel).show(requireActivity().getSupportFragmentManager(), "AddListItemDialog");
        });

        binding.toDoListRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));

        toDoListviewModel.observeItems(this, items -> {
            if (!items.isEmpty()) {
                binding.cardTip.setVisibility(View.GONE);
                binding.toDoListRecycler.setVisibility(View.VISIBLE);
                binding.arrowHelp.setVisibility(View.GONE);
                binding.toDoListRecycler.setAdapter(new ToDoListAdapter(items, toDoListviewModel, requireContext()));
            } else {
                binding.cardTip.setVisibility(View.VISIBLE);
                binding.arrowHelp.setVisibility(View.VISIBLE);
                binding.toDoListRecycler.setVisibility(View.GONE);
            }

        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            CharSequence title = item.getTitle();
            if (title.equals("Удалить все")) {
                toDoListviewModel.deleteAll();
                return true;
            } else if (title.equals("Добавить")) {
                new AddListItemDialog(toDoListviewModel).show(requireActivity().getSupportFragmentManager(), "AddListItemDialog");
                return true;
            } else if (title.equals("Все сделано")) {
                toDoListviewModel.checkAll();
                return true;
            }
        } catch (Exception ignored){
        }
        return super.onOptionsItemSelected(item);

    }
}