package com.elias.todo.ui.home;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elias.todo.database.DatabaseHandler;
import com.elias.todo.model.Task;
import com.elias.todo.utils.TodoAdapter;
import com.elias.todo.R;

import java.util.List;

public class HomeFragment extends androidx.fragment.app.Fragment {

    private RecyclerView recyclerView;
    private TodoAdapter adapter;
    private List<Task> allTasks;
    private DatabaseHandler databaseHandler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        databaseHandler = new DatabaseHandler(getContext());

        recyclerView = view.findViewById(R.id.recyclerViewTodos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadTodos();
        adapter = new TodoAdapter(allTasks);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void loadTodos() {
        allTasks = databaseHandler.getAllTasks();
    }
}
