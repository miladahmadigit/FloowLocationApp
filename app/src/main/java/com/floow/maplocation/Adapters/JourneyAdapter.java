package com.floow.maplocation.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.floow.maplocation.Model.Journey;
import com.floow.maplocation.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by milad on 11/21/2017.
 */

public class JourneyAdapter extends BaseAdapter
{
    private ArrayList<Journey> journeyList = new ArrayList<>();
    private Context context;
    private ViewHolder holder = null;
    private int posSelect = -1;

    public JourneyAdapter(Context context_, List<Journey> journeyList_)
    {
        context = context_;
        journeyList.addAll(journeyList_);
    }


    @Override
    public int getCount()
    {
        return journeyList.size();
    }


    @Override
    public Journey getItem(int position)
    {
        return journeyList.get(position);
    }


    @Override
    public long getItemId(int position)
    {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.journey_item, parent, false);
            holder.rlJourneyItem = convertView.findViewById(R.id.rlJourneyItem);
            holder.journeyName = convertView.findViewById(R.id.journeyName);
            holder.journeyStartTime = convertView.findViewById(R.id.journeyStartTime);
            holder.journeyEndTime = convertView.findViewById(R.id.journeyEndTime);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.rlJourneyItem.setTag(position);
        holder.journeyName.setText(journeyList.get(position).getNameOfJourney());
        holder.journeyStartTime.setText(journeyList.get(position).getStartTimeOfJourney());
        holder.journeyEndTime.setText(journeyList.get(position).getEndTimeOfJourney());
        if (posSelect == position)
        {
            holder.journeyStartTime.setVisibility(View.VISIBLE);
            holder.journeyEndTime.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.journeyStartTime.setVisibility(View.GONE);
            holder.journeyEndTime.setVisibility(View.GONE);
        }
        holder.rlJourneyItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                posSelect =Integer.parseInt(v.getTag().toString());
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    private static class ViewHolder
    {
        private LinearLayout rlJourneyItem;
        private TextView journeyName, journeyStartTime, journeyEndTime;

    }
}



