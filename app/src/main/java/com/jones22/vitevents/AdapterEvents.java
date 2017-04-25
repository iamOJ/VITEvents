package com.jones22.vitevents;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.List;

public class AdapterEvents extends RecyclerView.Adapter<AdapterEvents.ViewHolderEvents>{


    Context context;
    private static List<ParseObject> listEvents = new ArrayList<ParseObject>();
    private LayoutInflater layoutInflater;
    ArrayAdapter<Events> adapter;
    public AdapterEvents(Context context, ArrayAdapter<Events> adapter){
        this.context = context;
        this.adapter = adapter;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setListEvents(List<ParseObject> listEvents) {
        this.listEvents = listEvents;
    }

    @Override
    public ViewHolderEvents onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.custom_event,parent,false);
        ViewHolderEvents viewHolderEvents = new ViewHolderEvents(view);

        return viewHolderEvents;
    }

    @Override
    public void onBindViewHolder(ViewHolderEvents holder, int position) {
        //ParseObject currentEvent = listEvents.get(position);
        //holder.eventName.setText(currentEvent.getString("name"));
        //holder.eventTime.setText(currentEvent.getString("time"));
        holder.eventName.setText(adapter.getItem(position).getName());
        holder.eventTime.setText(adapter.getItem(position).getTime());
        holder.eventDate.setText(adapter.getItem(position).getDate());
    }

    @Override
    public int getItemCount() {
        return adapter.getCount();
    }

    static class ViewHolderEvents extends RecyclerView.ViewHolder{

        private TextView eventName;
        private TextView eventTime;
        private TextView eventDate;

        public ViewHolderEvents(View itemView) {
            super(itemView);
            eventName = (TextView) itemView.findViewById(R.id.textEvent);
            eventTime = (TextView) itemView.findViewById(R.id.textTime);
            eventDate = (TextView) itemView.findViewById(R.id.textDate);
        }
    }

}
