package com.example.ruben.drealitym.HelperClasses;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ruben.drealitym.R;
import com.example.ruben.drealitym.uiclasses.RemindersActivity;

import java.util.ArrayList;

public class ReminderListAdapter extends RecyclerView.Adapter {

    private static final String TAG = "ReminderListAdapter";
    private ArrayList mDreamReminderList;
    private Context context;

    public ReminderListAdapter(Context context, ArrayList<String> DreamReminderList ) {


        //TODO: change the type of the input array to a dreamReminderObject or something
        Log.d(TAG, " Constructor called");
        mDreamReminderList = DreamReminderList;
        this.context = context;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        Log.d(TAG, "onCreateViewHolder: called.");
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_reminder, viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        Log.d(TAG, "onBindViewh0lder: called");

        //TODO:

    }



    @Override
    public int getItemCount() {
        //size of the ArrayList<>
        return mDreamReminderList.size();
    }






    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView days;
        TextView interval;
        TextView time;

        ImageView editButton;
        CheckBox checkbox;
        RelativeLayout parentLayout;





        private MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.reminder_name_text_view);
            days = itemView.findViewById(R.id.reminder_days_text_view);
            time = itemView.findViewById(R.id.reminder_time_text_view);
            interval = itemView.findViewById(R.id.reminder_interval_text_view);
            editButton = itemView.findViewById(R.id.reminder_edit_button_image_view);
            checkbox = itemView.findViewById(R.id.reminder_check_box);
            parentLayout = itemView.findViewById(R.id.reminder_item_parent_layout);


        }
    }
}
