package com.example.ruben.drealitym.HelperClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.ruben.drealitym.R;

import java.util.List;

public class RealityCheckAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<String> mDays;
    private List<String> childList;

    public RealityCheckAdapter(Context mContext, List<String> mDays, List<String> childList) {
        this.mContext = mContext;
        this.mDays = mDays;
        this.childList = childList;
            }
    //there are only seven Days, for each day one group
    @Override
    public int getGroupCount() {
        return 7;
    }
    //every group has 3 childs
    @Override
    public int getChildrenCount(int groupPosition) {
        return 3;
    }
    @Override
    public Object getGroup(int groupPosition) {
        return this.mDays.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childPosition;
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

    //this method  returns the view of the child
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        String listTitle = (String) getGroup(groupPosition);

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_ex_lv_reality_check, null);
        }
        TextView tvTitle = convertView.findViewById(R.id.item_reality_check_title);
        tvTitle.setText(listTitle);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView  = inflater.inflate(R.layout.subitem_ex_lv_reality_check, null);
        }

        TextView tvChild = (TextView) convertView.findViewById(R.id.subitem_tv_reality_check);
        tvChild.setText(childText);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }



}
