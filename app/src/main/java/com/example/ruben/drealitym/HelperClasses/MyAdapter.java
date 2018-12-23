package com.example.ruben.drealitym.HelperClasses;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ruben.drealitym.uiclasses.ShowDreamEntry;
import com.example.ruben.drealitym.R;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{


    private static final String TAG = "MyAdapter";
    Cursor cursor;
    Context context;
    private boolean valid;

    Intent intent;

    public MyAdapter( Cursor cursor, Context context  ) {
        Log.d(TAG, "MyAdapter: constructor called.");




        //checkt ob es ein Cursor gibt und setzt den bool wert valid entsprechend
        valid = cursor != null;
        this.cursor = cursor;
        this.context = context;

        //Anzahl der Items in der SQL-db
        int anzahl = cursor.getCount();
        Toast.makeText(context, "Anzahl: " + anzahl, Toast.LENGTH_SHORT).show();

        //this checks to make sure you don't have an empty set
        cursor.moveToFirst();
        Log.d(TAG, "MyAdapter: cursor moved to first row");

    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Log.d(TAG, "onCreateViewHolder: called.");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false );
        MyViewHolder viewHolder = new MyViewHolder(view);
        cursor.moveToFirst();

        return viewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //this method gets called every time add item is added to the listview
        Log.d(TAG, "onBindViewHolder: called");

        //every time this method is called, this var is created
        final int mPosition = position;


            cursor.move(position);



        //these lines sets the text from the cursor into the item
        holder.title.setText(cursor.getString(0));
        holder.text.setText(cursor.getString(1));
        holder.date.setText(cursor.getString(2));
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if the user clicks on the item, the intent gets called and shows detailed information. the position gets passed by the intent.
                Intent intent = new Intent(context, ShowDreamEntry.class);

                intent.putExtra("position", mPosition);
                context.startActivity(intent);

            }
        });

    }



    @Override
    public int getItemCount() {

        Log.d(TAG, "getItemCount: called.");
        //gibt an wie viele rows der cursor hat, so viele Views soll es letztendlich geben

        //minus eins weil die rows bei null anfangen
        return cursor.getCount() ;
    }



    //ViewHolder  for the certain item.
    public static class MyViewHolder extends RecyclerView.ViewHolder {

         TextView title;
         TextView text;
         TextView date;
         ImageView starIcon;
         RelativeLayout parentLayout;


        //Viewholder hält die Einträge in dem Arbeitsspeicher dass der RecyclerView alles schnell wieder darstellen kann
        public MyViewHolder(View itemView ) {
            super(itemView);

            Log.d(TAG, "MyViewHolder: called.");

            title = itemView.findViewById(R.id.text_view_title);
            text = itemView.findViewById(R.id.text_view_intro);
            date = itemView.findViewById(R.id.text_view_date);
            starIcon = itemView.findViewById(R.id.image_view_star);
            parentLayout = itemView.findViewById(R.id.parent_layout);

        }
    }




}
