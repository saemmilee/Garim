package com.inhatc.garim;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ApplicationAdapter extends RecyclerView.Adapter<ApplicationAdapter.ApplicationViewHolder> {

    private static final String TAG = "App_Adapter";

    private ArrayList<Ordinance> arrayList;
    private Context context;

    public ApplicationAdapter(ArrayList<Ordinance> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ApplicationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.application_list, parent, false);
        ApplicationViewHolder holder = new ApplicationViewHolder(view);

        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull ApplicationViewHolder holder, int position) {

        holder.ordinanceNum.setText(String.valueOf(arrayList.get(position).getNum()));
        holder.ordinanceTitle.setText(arrayList.get(position).getTitle());
        holder.ordinanceStatus.setText(arrayList.get(position).getStatus());

        String strStatus = arrayList.get(position).getStatus();

        if(("success").equals(strStatus)) {
            holder.ordinanceStatus.setTextColor(Color.parseColor("#FF0040FF"));
        } else if(("fail").equals(strStatus)) {
            holder.ordinanceStatus.setTextColor(Color.parseColor("#FFFF4D4D"));
        }

        holder.tblRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SignActivity.class);
                intent.putExtra("ordinanceNum", String.valueOf(arrayList.get(position).getNum()));
                intent.putExtra("moveFrom", "ApplicationAdapter");
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class ApplicationViewHolder extends RecyclerView.ViewHolder {

        TableRow tblRow;
        TextView ordinanceNum;
        TextView ordinanceTitle;
        TextView ordinanceStatus;

        public ApplicationViewHolder(@NonNull View listView) {

            super(listView);
            this.tblRow = listView.findViewById(R.id.tblRow);
            this.ordinanceNum = listView.findViewById(R.id.ordinanceNum);
            this.ordinanceTitle = listView.findViewById(R.id.ordinanceTitle);
            this.ordinanceStatus = listView.findViewById(R.id.ordinanceStatus);

        }
    }
}
