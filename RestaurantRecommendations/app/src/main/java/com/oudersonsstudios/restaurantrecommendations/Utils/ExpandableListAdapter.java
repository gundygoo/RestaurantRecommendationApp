package com.oudersonsstudios.restaurantrecommendations.Utils;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.oudersonsstudios.restaurantrecommendations.Models.Food;
import com.oudersonsstudios.restaurantrecommendations.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Erik on 4/2/2017.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> restaurantNames;
    private HashMap<String, List<Food>> restaurantFoodItemsMapped;

    public ExpandableListAdapter(Context context, List<String> restaurantNames,
                                       HashMap<String, List<Food>> restaurantFoodItemsMapped) {
        this.context = context;
        this.restaurantNames = restaurantNames;
        this.restaurantFoodItemsMapped = restaurantFoodItemsMapped;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.restaurantFoodItemsMapped.get(this.restaurantNames.get(listPosition))
                .get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        Food expandedListItem = (Food) getChild(listPosition, expandedListPosition);
        final String expandedListText = expandedListItem.getName();
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.activity_restaurant_list_item, null);
        }
        TextView expandedListTextView = (TextView) convertView
                .findViewById(R.id.expanded_item_text);
        expandedListTextView.setText(expandedListText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.restaurantFoodItemsMapped.get(this.restaurantNames.get(listPosition))
                .size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.restaurantNames.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.restaurantNames.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.activity_restaurant_list_group, null);
        }
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.listTitle);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}
