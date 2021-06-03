package com.example.ast2_phonebook.Activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ast2_phonebook.R;
import com.example.ast2_phonebook.Room.Entities.Phonebook;

import java.util.ArrayList;

public class ListPageActivityAdapter extends RecyclerView.Adapter<ListPageActivityAdapter.MyViewHolder>{
    Context context;
    ArrayList<Phonebook> dataSet;
    private OnItemClickListener mOnItemClickListener;

    public ListPageActivityAdapter(Context context,ArrayList<Phonebook> dataSet) {
        this.context = context;
        this.dataSet = dataSet;
    }

    public void reloadContactList (ArrayList<Phonebook> phoneList) {
        this.dataSet = phoneList;
        this.notifyDataSetChanged();
    }
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public ListPageActivityAdapter(ArrayList<Phonebook> itemsData,
                             OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
        this.dataSet = itemsData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);

        final MyViewHolder viewHolder = new MyViewHolder(itemView);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(v, viewHolder.getAdapterPosition());
            }
        });



        viewHolder.container.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mOnItemClickListener.onItemClick(v, viewHolder.getAdapterPosition());
            }
        });

        viewHolder.txtName.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                dataSet.get( viewHolder.getAdapterPosition() );

            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txtName.setText(dataSet.get(position).firstName + "  " +dataSet.get(position).lastName);
        holder.txtPhone.setText(dataSet.get(position).phone);
        holder.txtEmail.setText(dataSet.get(position).email);
    }

    @Override
    public int getItemCount() {
        return dataSet == null? 0: dataSet.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView txtName;
        TextView txtPhone;
        TextView txtEmail;
        View container;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView;
            txtName = (TextView)  itemView.findViewById(R.id.txt_main_item_name);
            txtPhone = (TextView)  itemView.findViewById(R.id.txt_main_item_phone);
            txtEmail = (TextView)  itemView.findViewById(R.id.txt_main_item_email);
        }


    }



}
