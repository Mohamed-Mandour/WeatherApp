package com.example.mandour.stormy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mandour.stormy.R;
import com.example.mandour.stormy.weather.Day;

public class DayAdapter extends BaseAdapter{

    private Context mContext;
    private Day[] mDays;

    public DayAdapter(Context context, Day [] days){
        this.mContext = context;
        this.mDays = days;
    }

    @Override
    public int getCount() {
        return mDays.length;
    }

    @Override
    public Object getItem(int position) {
        return mDays[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.daily_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.iconImageView = convertView.findViewById(R.id.iconImageView);
            viewHolder.tempertureLabel = convertView.findViewById(R.id.temperatureLabel);
            viewHolder.dayNameLabel = convertView.findViewById(R.id.dayNameLabel);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Day day = mDays[position];
        viewHolder.iconImageView.setImageResource(day.getIconId());
        viewHolder.tempertureLabel.setText(day.getTempertureMax() + "");
        viewHolder.dayNameLabel.setText(day.getDayOfTheWeek());
        return convertView;
    }


    private static class ViewHolder {
        ImageView iconImageView;
        TextView tempertureLabel;
        TextView dayNameLabel;
    }
}
