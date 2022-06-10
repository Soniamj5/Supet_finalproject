package com.animalhelpapp.supet_finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    Context context;
    ArrayList<PetInfo> petInfoArrayList;


    public Adapter(Context context, ArrayList<PetInfo> petInfoArrayList) {
        this.context = context;
        this.petInfoArrayList = petInfoArrayList;
    }

    @NonNull
    @Override
    public Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_homefragment, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.MyViewHolder holder, int position) {

        PetInfo petInfo = petInfoArrayList.get(position);
        holder.petName.setText(petInfo.petName);
        holder.title.setText(petInfo.title);
        holder.description.setText(petInfo.description);

    }

    @Override
    public int getItemCount() {
        return petInfoArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView petName, title, description;
        ImageView btn_delete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            /*conectar con la parte gráfica*/
            petName = itemView.findViewById(R.id.petNameCard);
            title = itemView.findViewById(R.id.titleCard);
            description = itemView.findViewById(R.id.descriptionCard);
        }
    }
}
