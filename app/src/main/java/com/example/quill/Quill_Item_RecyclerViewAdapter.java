package com.example.quill;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quill.database.entities.Quill;

import java.util.List;


public class Quill_Item_RecyclerViewAdapter extends RecyclerView.Adapter<Quill_Item_RecyclerViewAdapter.MyViewHolder> {

    private List<Quill> quillsList;

    public Quill_Item_RecyclerViewAdapter(List<Quill> quillsList) {
        this.quillsList = quillsList;
    }

    @NonNull
    @Override
    public Quill_Item_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quill_item_recycler_view_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Quill_Item_RecyclerViewAdapter.MyViewHolder holder, int position) {

        Quill quill = quillsList.get(position);
        holder.quillItemTitle.setText(quill.getTitle());
    }

    @Override
    public int getItemCount() {
        return quillsList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView quillItemTitle;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            quillItemTitle = itemView.findViewById(R.id.quillItemTextView);
        }
    }

}
