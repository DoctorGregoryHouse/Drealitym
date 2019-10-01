package com.example.ruben.drealitym.HelperClasses;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.ruben.drealitym.Data.RealityCheckEntry;
import com.example.ruben.drealitym.Data.RealityCheckViewModel;
import com.example.ruben.drealitym.R;
import com.example.ruben.drealitym.UiClasses.RealityCheckActivity;

import java.util.List;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;

    //CONSTANTS
    private static final String LOG_TAG = "CustExpListAdapter";



    // Data for the ListView
    private List<String> exlvTitle;
    private List<RealityCheckEntry> realityCheckEntries;
    private RealityCheckViewModel viewModel;

    private OnItemClickListener listener;

    public CustomExpandableListAdapter(Context context, List<String> exlvTitle) {
        this.context = context;
        this.exlvTitle = exlvTitle;
    }

    @Override
    public int getGroupCount() {
        //return 7;
        return realityCheckEntries.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 3;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return realityCheckEntries.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return realityCheckEntries.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        String groupTitle = exlvTitle.get(groupPosition);
        if (convertView == null){
            LayoutInflater inflater  = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_ex_lv_reality_check,null);
        }
        TextView tvTitle = convertView.findViewById(R.id.item_reality_check_title);
        tvTitle.setText(groupTitle);

        Switch enableSwitch = (Switch) convertView.findViewById(R.id.reality_check_item_switch);
        enableSwitch.setClickable(false);
        final RealityCheckEntry mEntry = realityCheckEntries.get(groupPosition);


        if (mEntry.getNotification() == RealityCheckActivity.GET_NOTIFIED) {
            enableSwitch.setChecked(true);
        }else{
            enableSwitch.setChecked(false);
        }

        enableSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null){
                     /*
                     Best practice would be to use an onCheckChangeListener, but when using it the LiveDataObserver triggers every time it is changed, independent whether
                     the user does changes it or it gets changed programmatically. This ends up in a loop
                     A solution approach would be Android Data Binding
                      */
                    listener.onItemSwitchClick(groupPosition, mEntry.getNotification());
                }
                Log.d(LOG_TAG, "GroupPosition: " + groupPosition + " isChecked: " + mEntry.getNotification());

            }
        });
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        String string  = new String() ;
        String[] strings = getDataStrings(groupPosition);
        String groupChild = strings[childPosition];

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.subitem_ex_lv_reality_check, null);
        }

        TextView tvContent  = convertView.findViewById(R.id.subitem_tv_reality_check);
        tvContent.setText(groupChild);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null && groupPosition != ExpandableListView.INVALID_POSITION)

                    if(childPosition != 0 && childPosition != 1){
                        //start spinner to choose Interval
                        listener.onItemClick(groupPosition,childPosition, true);

                    }else {
                        listener.onItemClick(groupPosition, childPosition);
                    }
            }
        });


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void setRealityCheckEntries(List<RealityCheckEntry> realityCheckEntries){
        this.realityCheckEntries = realityCheckEntries;
        notifyDataSetChanged();
    }


    private String[] getDataStrings(int groupPosition){

        RealityCheckEntry entry = realityCheckEntries.get(groupPosition);

        StringBuilder stringBuilder = new StringBuilder();
        int startHour = entry.getStartHour();
        int startMinute = entry.getStartMinute();

        if(startHour < 10 ){
            stringBuilder.append("0");
        }
        stringBuilder.append(startHour);
        stringBuilder.append(":");
        if(startMinute < 10 ){
            stringBuilder.append("0");
        }
        stringBuilder.append(startMinute);

        String startTime = stringBuilder.toString();

        stringBuilder = new StringBuilder();
        int stopHour = entry.getStopHour();
        int stopMinute = entry.getStopMinute();

        if(stopHour < 10 ){
            stringBuilder.append("0");
        }
        stringBuilder.append(stopHour);
        stringBuilder.append(":");
        if(stopMinute < 10 ){
            stringBuilder.append("0");
        }
        stringBuilder.append(stopMinute);

        String stopTime = stringBuilder.toString();

        int interval = entry.getInterval();
        stringBuilder = new StringBuilder();
        stringBuilder.append("Interval: ");
        stringBuilder.append(interval);

        String intervalTime = stringBuilder.toString();

        String[] returnArray = {startTime,stopTime,intervalTime};
        return returnArray;
    }



    //Interface implemented in the RealityCheckActivity to handle the click on the childItem
    public interface OnItemClickListener {
        void onItemClick(int groupPosition, int childPosition);
        void onItemClick(int groupPosition, int childPosition, boolean clickedInterval);
        void onItemSwitchClick(int groupPosition, int getNotified); //handle click on the switch
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }


}

