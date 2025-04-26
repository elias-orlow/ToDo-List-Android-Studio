package com.elias.todo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.elias.todo.database.DatabaseHandler;
import com.elias.todo.databinding.ActivityMainBinding;
import com.elias.todo.model.Task;

import java.util.Calendar;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private final Calendar calendar = Calendar.getInstance();
    DatabaseHandler db = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_folder,
                R.id.navigation_history, R.id.navigation_calendar).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_activity_main);
        NavigationUI.setupWithNavController(binding.navView, navController);

        binding.fab.setOnClickListener(view -> showCreateDialog());
    }

    private void showCreateDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_sheet_layout);

        LinearLayout layoutNewTask = dialog.findViewById(R.id.layoutNewTask);
        LinearLayout layoutNewFolder = dialog.findViewById(R.id.layoutNewFolder);
        ImageView cancelButton = dialog.findViewById(R.id.cancelButton);

        layoutNewTask.setOnClickListener(view -> {
            dialog.dismiss();
            showNewTaskDialog();
        });

        layoutNewFolder.setOnClickListener(view -> {
            dialog.dismiss();
            Toast.makeText(MainActivity.this, "New Folder create", Toast.LENGTH_LONG).show();
        });

        cancelButton.setOnClickListener(view -> dialog.dismiss());

        dialog.show();
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(android.view.Gravity.BOTTOM);
    }

    private void showNewTaskDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.create_task_sheetlayout);

        ImageView cancelButton = dialog.findViewById(R.id.cancelButton);
        EditText edit_description = dialog.findViewById(R.id.edit_description);
        ImageView star_favorite = dialog.findViewById(R.id.star_favorite);
        EditText edit_time = dialog.findViewById(R.id.edit_time);
        Button btn_pick_datetime = dialog.findViewById(R.id.btn_pick_datetime);
        EditText edit_folder = dialog.findViewById(R.id.edit_folder);
        Button create_new_task = dialog.findViewById(R.id.create_new_task);

        AtomicBoolean isFavorite = new AtomicBoolean(false);

        cancelButton.setOnClickListener(view -> dialog.dismiss());

        star_favorite.setOnClickListener(view ->  {
                boolean newValue = !isFavorite.get();
                isFavorite.set(newValue);
                if (newValue) {
                    star_favorite.setImageResource(R.drawable.ic_star_filled_24);
                } else {
                    star_favorite.setImageResource(R.drawable.ic_star_border_24);
                }
            }
        );

        edit_time.setOnClickListener(view -> showDataTimePicker(edit_time));
        btn_pick_datetime.setOnClickListener(view -> showDataTimePicker(edit_time));

        create_new_task.setOnClickListener(view -> {
                if (edit_description.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter a task description", Toast.LENGTH_SHORT).show();
                    return;
                } else if (edit_time.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please select a time", Toast.LENGTH_SHORT).show();
                    return;
                } else if (edit_folder.getText().toString().isEmpty()) {
                    edit_folder.setText("Uncategorized");
                }

                Toast.makeText(MainActivity.this, "New Task created", Toast.LENGTH_LONG).show();
                db.addTask(new Task(edit_description.getText().toString(),
                                    edit_time.getText().toString(),
                                    isFavorite.get() ? "1" : "0",
                                    edit_folder.getText().toString(),
                              "1"));
                dialog.dismiss();
            }
        );

        dialog.show();
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(android.view.Gravity.BOTTOM);
    }

    private void showDataTimePicker(EditText editTime) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                R.style.CustomDatePickerDialog,
                (view, year, month, dayOfMonth) -> {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this,
                            R.style.CustomDatePickerDialog,
                            (view1, hourOfDay, minute) -> {
                                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                calendar.set(Calendar.MINUTE, minute);

                                String formattedDateTime = String.format("%02d-%02d-%04d %02d:%02d", dayOfMonth, month + 1, year, hourOfDay, minute);
                                editTime.setText(formattedDateTime);
                            },
                            calendar.get(Calendar.HOUR_OF_DAY),
                            calendar.get(Calendar.MINUTE),
                            true);
                    timePickerDialog.show();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }
}