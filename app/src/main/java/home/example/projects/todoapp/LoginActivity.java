package home.example.projects.todoapp;


import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private SQLiteOpenHelper dbhelper;
    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText userIdText = (EditText) findViewById(R.id.user_id);
        final EditText userPasswordText = (EditText) findViewById(R.id.user_password);
        Button logInButton = (Button) findViewById(R.id.sign_in_button);
        TextView registerText = (TextView) findViewById(R.id.sign_up_text);

        dbhelper = new SQLiteDBHelper(this);
        db = dbhelper.getReadableDatabase();

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = userIdText.getText().toString();
                String pass = userPasswordText.getText().toString();

                cursor = db.rawQuery("SELECT *FROM "+SQLiteDBHelper.USERS_TABLE +" WHERE "+SQLiteDBHelper.COLUMN_EMAIL+"=? AND "+SQLiteDBHelper.COLUMN_PASSWORD+"=?",new String[] {email,pass});
                if (cursor != null) {
                    if(cursor.getCount() > 0) {

                        cursor.moveToFirst();
                        String userEmail = cursor.getString(cursor.getColumnIndex(SQLiteDBHelper.COLUMN_EMAIL));
                        String userName= cursor.getString(cursor.getColumnIndex(SQLiteDBHelper.COLUMN_FULLNAME));
                        Toast.makeText(LoginActivity.this, getString(R.string.login_success), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this,LoginSuccessActivity.class);
                        intent.putExtra("fullname",userName);
                        intent.putExtra("email",userEmail);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setTitle(getString(R.string.login_warning));
                        builder.setMessage(getString(R.string.login_warning_message));
                        builder.setPositiveButton(getString(R.string.continue_text), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                dialogInterface.dismiss();

                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }

            }
        });

        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

    }
}
