package com.example.comppress.adapter;

import java.util.LinkedHashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.comppress.R;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {
    //TODO Cleanup this mess
    private Context context;
    private List<String> expandableListTitle;
    private LinkedHashMap<String, List<String>> expandableListDetail;
    private int[][] ratings = new int[3][4];
    private ChildViewHolder childViewHolder;
    private GroupViewHolder groupViewHolder;

    public void setRatings(int[][] ratings) {
        this.ratings = ratings;
    }

    public int getRatingPosition(int x, int y){
        return this.ratings[x][y];
    }

    public int[][] getRatings() {
        return ratings;
    }

    public CustomExpandableListAdapter(Context context, List<String> expandableListTitle,
                                       LinkedHashMap<String, List<String>> expandableListDetail) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .get(expandedListPosition);

    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

//    @Override
//    public View getChildView(final int listPosition, final int expandedListPosition,
//                             boolean isLastChild, View convertView, ViewGroup parent) {
//        final String expandedListText = (String) getChild(listPosition, expandedListPosition);
//        ViewHolder holder;
//
//        if (convertView == null) {
//            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = layoutInflater.inflate(R.layout.expandable_list_item, null);
//            holder = new ViewHolder(convertView);
//            convertView.setTag(holder);
//        } else {
//            holder = (ViewHolder)convertView.getTag();
//        }
//
//        holder = (ViewHolder) convertView.getTag();
//        holder.ratingbar.setRating(arr[listPosition][expandedListPosition]);
//
//        holder.ratingbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
//            @Override
//            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
//                arr[listPosition][expandedListPosition] = (int) ratingBar.getRating();
//            }
//        });
//
////        TextView expandedListTextView = (TextView) convertView
////                .findViewById(R.id.subCriterion);
////        expandedListTextView.setText(expandedListText);
//        return convertView;
//    }

    @Override
    public View getChildView(final int listPosition, final int expandedListPosition, boolean b, View view, ViewGroup viewGroup)
    {
        final String childText = (String)getChild(listPosition,expandedListPosition);
        ChildViewHolder childViewHolder;

        if(view==null)
        {
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.expandable_list_item, null);

            childViewHolder = new ChildViewHolder(view);
            view.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder)view.getTag();
        }

        childViewHolder = (ChildViewHolder)view.getTag();

//        childViewHolder.ratingbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener()
//        {
//            @Override
//            public void onRatingChanged(RatingBar ratingBar, float v, boolean b)
//            {
//                ratings[listPosition][expandedListPosition+1] = (int) ratingBar.getRating();
//            }
//        });

        childViewHolder.features.setText(childText);
//        childViewHolder.ratingbar.setTag(expandedListPosition+1);
//        childViewHolder.ratingbar.setRating(ratings[listPosition][expandedListPosition+1]);

        return view;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(final int listPosition, boolean isExpanded, View view, ViewGroup parent)
    {
/*        String listTitle = (String) getGroup(listPosition);
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.expandable_list_group, null);
        }
        TextView listTitleTextView = (TextView) view
                .findViewById(R.id.topCriterion);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);
        return view;*/
        final String listTitle = (String) getGroup(listPosition);
        GroupViewHolder groupViewHolder;

        if(view==null)
        {
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.expandable_list_group, null);

            groupViewHolder = new GroupViewHolder(view);
            view.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder)view.getTag();
        }

        groupViewHolder = (GroupViewHolder)view.getTag();

        groupViewHolder.ratingbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener()
        {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b)
            {
                ratings[listPosition][0] = (int) ratingBar.getRating();
            }
        });

        groupViewHolder.features.setText(listTitle);
        groupViewHolder.ratingbar.setTag(0);
        groupViewHolder.ratingbar.setRating(ratings[listPosition][0]);

        return view;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }

    private static class ChildViewHolder
    {
        RatingBar ratingbar;
        TextView features;

        public ChildViewHolder(View view) {
//            ratingbar = view.findViewById(R.id.subRatingBar);
            features = view.findViewById(R.id.subCriterion);
        }
    }

    private static class GroupViewHolder
    {
        RatingBar ratingbar;
        TextView features;

        public GroupViewHolder(View view) {
            ratingbar = view.findViewById(R.id.topRatingBar);
            features = view.findViewById(R.id.topCriterion);
        }
    }
}
