package home.example.projects.todoapp;

import android.support.v7.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private TextView goToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        openHelper = new SQLiteDBHelper(this);

        final EditText fullNameText = (EditText) findViewById(R.id.register_text_name);
        final EditText emailText = (EditText) findViewById(R.id.register_text_email);
        final EditText passwordText = (EditText) findViewById(R.id.register_text_password);
        Button registerButton = (Button) findViewById(R.id.register_button);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                db = openHelper.getWritableDatabase();

                String fullname = fullNameText.getText().toString();
                String email = emailText.getText().toString();
                String pass = passwordText.getText().toString();

                InsertData(fullname, email, pass);

                final AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setTitle(getString(R.string.register_successful));
                builder.setMessage(getString(R.string.register_confirmation));
                builder.setPositiveButton(getString(R.string.continue_text), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        finish();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        goToLogin = (TextView) findViewById(R.id.go_to_login);
        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

    }

    private void InsertData(String fullName, String email, String password) {

        ContentValues values = new ContentValues();
        values.put(SQLiteDBHelper.COLUMN_FULLNAME, fullName);
        values.put(SQLiteDBHelper.COLUMN_EMAIL, email);
        values.put(SQLiteDBHelper.COLUMN_PASSWORD, password);
    }

}