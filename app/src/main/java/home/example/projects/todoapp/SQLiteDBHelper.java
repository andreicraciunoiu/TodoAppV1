package home.example.projects.todoapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


class SQLiteDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "info.db";
    private static final int DATABASE_VERSION = 1;

    static final String USERS_TABLE = "profile";

    private static final String COLUMN_ID = "userid";
    static final String COLUMN_FULLNAME = "fullname";
    static final String COLUMN_EMAIL = "email";
    static final String COLUMN_PASSWORD = "password";

    private static final String TODO_TABLE = "tasks";
    private static final String TODO_ID = "todoId";
    private static final String TODO_TASK = "todoTask";

    SQLiteDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String CREATE_USER_TABLE =
            "CREATE TABLE " + USERS_TABLE + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_FULLNAME + " TEXT NOT NULL, " +
                    COLUMN_EMAIL + " TEXT NOT NULL, " +
                    COLUMN_PASSWORD + " TEXT NOT NULL" + ")";

    private static final String CREATE_TODO_TABLE =
            "CREATE TABLE " + TODO_TABLE + " (" +
                    TODO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TODO_TASK + " TEXT NOT NULL, " + " FOREIGN KEY (" + TODO_TASK + ") REFERENCES " + USERS_TABLE + " (" + COLUMN_ID + "))";

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
        sqLiteDatabase.execSQL(CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CREATE_USER_TABLE);

        onCreate(sqLiteDatabase);
    }
}