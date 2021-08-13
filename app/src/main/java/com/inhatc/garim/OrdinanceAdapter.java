package com.inhatc.garim;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrdinanceAdapter extends RecyclerView.Adapter<OrdinanceAdapter.OrdinanceViewHolder> {

    private ArrayList<Ordinance> arrayList;
    private Context context;

    public OrdinanceAdapter(ArrayList<Ordinance> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public OrdinanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ordinance_list, parent, false);
        OrdinanceViewHolder holder = new OrdinanceViewHolder(view);

        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull OrdinanceViewHolder holder, int position) {

        holder.ordinanceNum.setText(String.valueOf(arrayList.get(position).getNum()));
        holder.ordinanceTitle.setText(arrayList.get(position).getTitle());
        holder.ordinanceTerm.setText((CharSequence) arrayList.get(position).getTerm());
        //holder.ordinanceAvailability.setText(arrayList.get(position).getAvailability());

    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class OrdinanceViewHolder extends RecyclerView.ViewHolder {

        TextView ordinanceNum;
        TextView ordinanceTitle;
        TextView ordinanceTerm;
        Button ordinanceAvailability;

        public OrdinanceViewHolder(@NonNull View listView) {

            super(listView);
            this.ordinanceNum = listView.findViewById(R.id.ordinanceNum);
            this.ordinanceTitle = listView.findViewById(R.id.ordinanceTitle);
            this.ordinanceTerm = listView.findViewById(R.id.ordinanceTerm);
            this.ordinanceAvailability = listView.findViewById(R.id.ordinanceAvailability);

        }
    }
}
