package com.example.ruben.drealitym.HelperClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.ruben.drealitym.Data.RealityCheckEntry;
import com.example.ruben.drealitym.R;

import java.util.List;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;

    // Data for the ListView
    private List<String> exlvTitle;
    List<RealityCheckEntry> realityCheckEntries;

    //DUMMY
    List<String> exlvContent;


    OnItemClickListener listener;

    public CustomExpandableListAdapter(Context context, List<String> exlvTitle, List<String> exlvContent) {
        this.context = context;
        this.exlvTitle = exlvTitle;

        //DUMMY
        exlvContent.add("Von: 8:00");
        exlvContent.add("Bis: 16:00");
        exlvContent.add("Alle 20 minuten");
        exlvContent.add("Uhrzeicheck");
        exlvContent.add("Notifications on");
    }

    @Override
    public int getGroupCount() {
        return exlvTitle.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 7;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return exlvTitle.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return exlvContent.get(childPosition);
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
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        String groupTitle = exlvTitle.get(groupPosition);
        if (convertView == null){
            LayoutInflater inflater  = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_ex_lv_reality_check,null);
        }
        TextView tvTitle = convertView.findViewById(R.id.item_reality_check_title);
        tvTitle.setText(groupTitle);
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        String groupChild = exlvContent.get(childPosition);
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
                listener.onItemClick(groupPosition, childPosition);
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
    }


    private String[] prepareData(int groupPosition){
        RealityCheckEntry entry = realityCheckEntries.get(groupPosition);
        //TODO: create data strings to display 
        int startHour = entry.getStartHour();
        int startMinute = entry.getStartMinute();
        return null;
    }



    //Interface implemented in the RealityCheckActivity to handle the click on the childItem
    public interface OnItemClickListener {
        void onItemClick(int groupPosition, int childPosition);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }


}

