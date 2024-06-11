package com.company.traceme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BusAdapter extends RecyclerView.Adapter<BusAdapter.MyViewHolder> {

    Context context;
    ArrayList<busdata> list;

    public BusAdapter(Context context, ArrayList<busdata> list) {

        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.busitem,parent,false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        busdata bs = list.get(position);
        holder.emails.setText(bs.getEmails());
        holder.phone.setText(bs.getPhone());
        holder.fullname.setText(bs.getFullname());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder {

        TextView emails,fullname,phone;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            emails = itemView.findViewById(R.id.busname);
            fullname = itemView.findViewById(R.id.rootno);
            phone = itemView.findViewById(R.id.owner);

        }
    }
}
