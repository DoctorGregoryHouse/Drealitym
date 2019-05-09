package com.example.ruben.drealitym.uiclasses;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;

import com.example.ruben.drealitym.HelperClasses.DreamContract;
import com.example.ruben.drealitym.HelperClasses.DreamDbHelper;
import com.example.ruben.drealitym.HelperClasses.MyAdapter;
import com.example.ruben.drealitym.R;

public class MyDiaryActivity extends AppCompatActivity {

    private final String TAG = "MyDiaryActivity";



    DreamDbHelper dreamDbHelper;

    String[] columns =  {
            DreamContract.DreamEntry.COLUMN_DREAM_TITLE,
            DreamContract.DreamEntry.COLUMN_DREAM_TEXT,
            DreamContract.DreamEntry.COLUMN_DREAM_DATE,
            DreamContract.DreamEntry.COLUMN_DREAM_FAV};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_diary);
        Log.d(TAG, "onCreate: called.");



        RecyclerView recyclerView =  findViewById(R.id.recycler_view);


        //SQL Helper-class
        dreamDbHelper = new DreamDbHelper(this);

        //instanziiert die DB read only
        SQLiteDatabase db = dreamDbHelper.getReadableDatabase();

        //cursor wird erstellt und frägt alle relevanten informationen für den Recycleriview ab
        Cursor cursor = db.query(DreamContract.DreamEntry.TABLE_NAME, columns, null, null, null,null,null);


        MyAdapter adapter = new MyAdapter(cursor, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));








    }



}
