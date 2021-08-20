package com.inhatc.garim;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SignatureAdapter extends RecyclerView.Adapter<SignatureAdapter.SignatureViewHolder> {

    private ArrayList<Ordinance> arrayList;
    private Context context;

    public SignatureAdapter(ArrayList<Ordinance> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public SignatureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.signature_list, parent, false);
        SignatureViewHolder holder = new SignatureViewHolder(view);

        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull SignatureViewHolder holder, int position) {

        holder.ordinanceNum.setText(String.valueOf(arrayList.get(position).getNum()));
        holder.ordinanceTitle.setText(arrayList.get(position).getTitle());
        holder.ordinanceTerm.setText(arrayList.get(position).getTerm());

    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class SignatureViewHolder extends RecyclerView.ViewHolder {

        TextView ordinanceNum;
        TextView ordinanceTitle;
        TextView ordinanceTerm;

        public SignatureViewHolder(@NonNull View listView) {

            super(listView);
            this.ordinanceNum = listView.findViewById(R.id.ordinanceNum);
            this.ordinanceTitle = listView.findViewById(R.id.ordinanceTitle);
            this.ordinanceTerm = listView.findViewById(R.id.ordinanceTerm);

        }
    }
}
