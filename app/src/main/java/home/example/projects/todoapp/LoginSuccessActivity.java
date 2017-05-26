package home.example.projects.todoapp;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class LoginSuccessActivity extends AppCompatActivity {
    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_success);
        readItems();

        TextView nameText = (TextView) findViewById(R.id.welcome_user_text);
        TextView emailText = (TextView) findViewById(R.id.login_success_email);
        Button logoutButton = (Button) findViewById(R.id.logout_button);
        Intent intent = getIntent();

        String loginName = intent.getStringExtra("fullname");
        String loginEmail = intent.getStringExtra("email");
        nameText.setText(getString(R.string.activity_welcome_text) + loginName + getString(R.string.exclamation_mark));
        emailText.setText(loginEmail);

        lvItems = (ListView) findViewById(R.id.list_of_tasks);
        items = new ArrayList<>();
        itemsAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(LoginSuccessActivity.this);
                builder.setTitle(getString(R.string.logout_info));
                builder.setMessage(getString(R.string.double_check_question));
                builder.setPositiveButton(getString(R.string.confirm_logout), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(LoginSuccessActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

                builder.setNegativeButton(getString(R.string.cancel_logout), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


        Button addTaskButton = (Button) findViewById(R.id.button_add_task);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etNewItem = (EditText) findViewById(R.id.write_task);
                String itemText = etNewItem.getText().toString();
                itemsAdapter.add(itemText);
                etNewItem.setText("");
            }
        });
    }


    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            items = new ArrayList<>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            items = new ArrayList<>();
        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void setupListViewListener() {
        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapter, View item, int pos, long id) {
                        items.remove(pos);
                        itemsAdapter.notifyDataSetChanged();
                        writeItems();
                    }

                });
    }

    public void addTask(View v) {
        EditText editText = (EditText) findViewById(R.id.write_task);
        String itemText = editText.getText().toString();
        itemsAdapter.add(itemText);
        editText.setText("");
        writeItems();
    }
}

