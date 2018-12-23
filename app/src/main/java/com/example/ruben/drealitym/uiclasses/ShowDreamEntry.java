package com.example.ruben.drealitym.uiclasses;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.ruben.drealitym.HelperClasses.DreamContract;
import com.example.ruben.drealitym.HelperClasses.DreamDbHelper;
import com.example.ruben.drealitym.R;

import org.w3c.dom.Text;

public class ShowDreamEntry extends AppCompatActivity {

    int position;
    Cursor cursor;
    DreamDbHelper dreamDbHelper;
    SQLiteDatabase db;

    //columns queried by the cursor
    String[] columns =  {
            DreamContract.DreamEntry.COLUMN_DREAM_TITLE,
            DreamContract.DreamEntry.COLUMN_DREAM_TEXT,
            DreamContract.DreamEntry.COLUMN_DREAM_DATE,
            DreamContract.DreamEntry.COLUMN_DREAM_FAV};


    private final String TAG = "ShowDreamEntry";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_dream_entry);

        //intnent form MyAdapter class passes the position of the cursor
        Intent intent = getIntent();
        position = intent.getIntExtra("position",0);


        dreamDbHelper = new DreamDbHelper(this);
        db = dreamDbHelper.getReadableDatabase();

        //make query to get the dream information, @param: position gets passed by the Intnet started in the MyAdapter.class
        cursor = db.query(DreamContract.DreamEntry.TABLE_NAME, columns, null, null, null,null,null);
        cursor.move(position +1);

        //get strings from the cursor
       String mTitle =  cursor.getString(0 );
       String mText = cursor.getString(1);
       String mDate = cursor.getString(2);



       //text boxes which display the informations of the dream ( gets its information from the cursor)
        TextView title = findViewById(R.id.title_text_view);
        title.setText(mTitle);

        TextView text = findViewById(R.id.text_text_view);
        text.setText(mText);

        TextView date = findViewById(R.id.date_text_view);
        date.setText(mDate);


    }


    @Override
    protected void onStop() {
        super.onStop();
        //finishes the activity, so the position gets destroyed.
        finish();

    }
}
