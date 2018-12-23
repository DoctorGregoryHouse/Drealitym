package com.example.ruben.drealitym.uiclasses;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ruben.drealitym.R;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO: IMPLEMENT FONTCHANGER
        //   https://stackoverflow.com/questions/2973270/using-a-custom-typeface-in-android/16275257#16275257

        final Button addDreamBtn =  (Button) findViewById(R.id.add_dream_button);
        final Button myDiaryBtn = (Button) findViewById(R.id.my_diary_button);
        final Button remindersBtn = (Button) findViewById(R.id.reminders_button);
        Button settingsBtn = new Button(this);

        addDreamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent addDreamIntent = new Intent(MainActivity.this, AddDreamActivity.class);
                startActivity(addDreamIntent);
            }
        });

        myDiaryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myDiaryIntent = new Intent(MainActivity.this, MyDiaryActivity.class);
                startActivity(myDiaryIntent);
            }
        });

        remindersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent remindersIntent = new Intent(MainActivity.this, RemindersActivity.class);
                startActivity(remindersIntent);
            }
        });

        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(settingsIntent);
            }
        });

    }



}
