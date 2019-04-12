package com.example.ruben.drealitym.uiclasses;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ruben.drealitym.HelperClasses.DreamContract.DreamEntry;
import com.example.ruben.drealitym.HelperClasses.DreamDbHelper;
import com.example.ruben.drealitym.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class AddDreamActivity extends AppCompatActivity {

    private static final String LOG_TAG = "AddDreamActivity";


    //Helper for Database
    DreamDbHelper mDbHelper = new DreamDbHelper(this);

    //
    Spinner typeSpinner;
    int type;
    private static String sDate;

    //Pfad für die Audio-Datei
    private static String mFileName = null;
    //Media Players
    private MediaRecorder mRecorder = null;
    private MediaPlayer mPlayer = null;

    //variables to get the permission for the microphone
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private boolean permissionToRecordAccepted = false;
    private String[] permissions = {Manifest.permission.RECORD_AUDIO};
    //endregion


    //Schließt die Datenbank --> performance
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDbHelper.close();
    }


    @Override
    protected void onStart() {
        super.onStart();

        //Datum wird ermittelt und string wird erstellt, um das Audio file unter einem individuellen namen zu speichern.
        Date currentTime  = Calendar.getInstance().getTime();
        sDate = new SimpleDateFormat("yyyy-MM-dd").format(currentTime);


        // Record to the external cache directory for visibility
        // AudioFile wird angelegt
        //TODO: Die files werden in dem Cache angelegt und werden direkt danach gelöscht
        //TODO: Speicherort auswählen.
        mFileName = getExternalCacheDir().getAbsolutePath(); //TODO: getAbsolutePath may produce a NullPointerException
        Log.i("FILE_PATH", mFileName);
        mFileName += "/" +sDate +".3gp";

        Log.i(LOG_TAG, "Path for Audio:" + mFileName);


    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activivy_add_dream);


        //Titel der Activity setzen
        String title = getString(R.string.add_dream_title);
        setTitle(title);

        //region Spinner Setup/setOnItemSelectedListener
        //spinner connect with view
        typeSpinner =  findViewById(R.id.dream_type_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.dream_type_spinner_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        typeSpinner.setAdapter(adapter);

        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //position gibt am ende an was für eine Art traum es ist:
                //normal dream: 0
                //pre-lucid dream = 1
                //lucid dream = 2
                type = position;
                Log.i("spin", "number: " + type);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //endregion //

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);


        //region setAudioButtonLayouts

        LinearLayout ll = findViewById(R.id.audio_layout);
        RecordButton recordButton = new RecordButton(this);
        PlayButton playButton = new PlayButton(this);

        recordButton.setGravity(3);
        recordButton.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        playButton.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));

        ll.addView(recordButton);
        ll.addView(playButton);
        //endregion

        // ***************
        // * SAVE BUTTON *
        // ***************
        Button saveButton =  findViewById(R.id.add_dream_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //Traum Titel
                String titel = DreamTitle();

                //TraumText
                String text = DreamText();

                //TraumTyp
                /*TYPE 0 = normal dream
                  Type 1 = pre-lucid dream
                  Type 2 = lucid dram*/

                int dreamType = type;

                //TODO:ADD AUDIO RECORD FUNCTION
                //gibt an ob es eine Audio datei gibt oder nicht
                int checkFile = 0;

                //Datum an dem Traum erstellt wurde
                String date = sDate;

                //TODO: Favoriten funktion implementieren
                //Favorit bit
                int favourite = 0;



                //checkt ob Daten eingegeben wurden, wenn nicht wird ein Toast gepopt
                if(titel.matches("")) {

                    Toast toast = Toast.makeText(AddDreamActivity.this, R.string.toast_empty, Toast.LENGTH_LONG);
                    toast.show();
                }

                else {

                    //speichert das objekt in der Datenbank und beendet danach die Activity mit einer Toast message.
                    DatabaseObject databaseObject = new DatabaseObject(titel, text, dreamType, checkFile,date, favourite);
                    DatabaseOperations databaseOperations = new DatabaseOperations();


                    databaseOperations.execute(databaseObject, null, null);

                    Toast toast = Toast.makeText(AddDreamActivity.this,R.string.toast_not_empty , Toast.LENGTH_LONG);
                    toast.show();
                    finish(); //schließt die Activity

                    //Falls ein Datenbankeintrag nicht richtig gespeichert wird, könnte es an dem "finish()" liegen.
                }


                //databaseOperations.cancel(true);
                //TODO: Does the AsycTask have do be killed here or is it already dead after execution ?


                //check the version of the SDK and execute the asynctask with the appropriate executor



                //TODO:ADD audio

                //finish();
                //TODO: If the Object is empty u cant save fuction


                //TODO: save the entry with the date.
                //TODO: ADD an calendar
                //TODO: ADD Search function

            }
        });
    }




    //region MicPermission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {

            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted) finish();
    }
    //endregion

    //returns textvalue

    private String DreamText() {

        EditText editText =  findViewById(R.id.edit_text_field);
        String text = editText.getText().toString();

        Log.i("DreamText", text); // For debugging
        return text;
    }

    private String DreamTitle()
    {
        EditText editText = findViewById(R.id.edit_text_title);
        String text = editText.getText().toString();

        Log.i("DreamTitle", text); // For debugging
        return text;
    }

    //region MediaPlayerMethods


    private void onRecord(boolean start) {
        if (start) {
            startRecording();
        } else {
            stopRecording();
        }
    }

    private void onPlay(boolean start) {
        if (start) {
            startPlaying();
        } else {
            stopPlaying();
        }
    }

    private void startPlaying() {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    private void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
    }

    private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }
    //endregion

    //region Recordbutton class
    class RecordButton extends AppCompatButton {
        boolean mStartRecording = true;

        OnClickListener clicker = new OnClickListener() {
            public void onClick(View v) {
                onRecord(mStartRecording);
                if (mStartRecording) {
                    setText("Stop recording");
                } else {
                    setText("Start recording");
                }
                mStartRecording = !mStartRecording;
            }
        };

        public RecordButton(Context ctx) {
            super(ctx);
            setText("Start recording");
            setOnClickListener(clicker);
        }
    }
    //endregion

    //region PlayButton class
    class PlayButton extends AppCompatButton {
        boolean mStartPlaying = true;

        OnClickListener clicker = new OnClickListener() {
            public void onClick(View v) {
                onPlay(mStartPlaying);
                if (mStartPlaying) {
                    setText("Stop playing");
                } else {
                    setText("Start playing");
                }
                mStartPlaying = !mStartPlaying;
            }
        };

        public PlayButton(Context ctx) {
            super(ctx);
            setText("Start playing");
            setOnClickListener(clicker);
        }
    }

    //endregion





    //Daten für den Datenbankeintrag die in  den AsyncTask und anschließen in die Datenbank geparst werden
    public class DatabaseObject {

        String title;
        String text;
        int dreamType;
        int checkFile;
        String date;
        int favourite;

        public DatabaseObject(String titel, String text, int dreamType, int checkFile, String date, int favourite) {

            this.title = titel;
            this.text = text;           // the content of the Edittext field
            this.dreamType = dreamType; // 0,1 or 2 for the type of the dream
            this.checkFile = checkFile; // is there a  Audio FILE atm it has always the value "0"
            this.date = date;
            this.favourite = favourite;


        }

        //getters for the AddDream information
        public int getFavourite() {
            return favourite;
        }
        public String getTitle() {
            return title;
        }
        public String getDate() {
            return date;
        }
        public int getCheckFile() {
            return checkFile;
        }
        public int getDreamType() {
            return dreamType;
        }
        public String getText() {
            return text;
        }

        //TODO: Create constructor for DatabaseObject with audio bit + path !

    }

    //Im Hintergrund wird das DatabaseObject in die Datenbank eingefügt.
    public  class DatabaseOperations extends AsyncTask<DatabaseObject,Void,String> {


        final String LOG_TAG = "DatabaseOperations";

        @Override
        protected void onPreExecute() {
            Log.d(LOG_TAG,"onPreExecute executing...");
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d(LOG_TAG,"onPostExecute");

            //TODO: Close everything for better performance

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            Log.d(LOG_TAG,"OnProgressUpdate");
        }

        @Override
        protected String doInBackground(DatabaseObject ... databaseObjects) {
            //TODO: sth to return ?

            Log.d(LOG_TAG,"do in Background executed...");
            addDream(databaseObjects[0]);
            return null ;
        }



        //Traum in die Datenbank einfügen
        private void addDream(DatabaseObject databaseObject)
        {


            SQLiteDatabase db = mDbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DreamEntry.COLUMN_DREAM_TEXT, databaseObject.getText());
            values.put(DreamEntry.COLUMN_DREAM_TYPE, databaseObject.getDreamType());
            values.put(DreamEntry.COLUMN_DREAM_FILE, databaseObject.getCheckFile());
            values.put(DreamEntry.COLUMN_DREAM_DATE, databaseObject.getDate());
            values.put(DreamEntry.COLUMN_DREAM_TITLE, databaseObject.getTitle());
            values.put(DreamEntry.COLUMN_DREAM_FAV, databaseObject.getFavourite());

            long newRowId = db.insert(DreamEntry.TABLE_NAME, null,values);

            Log.d("COLUMN_ID", "ROW ID: " + newRowId);
            Log.d("DREAM_OBJECT","TEXT: " + databaseObject.getText());

            db.close();

        }
    }
}





