package com.example.quill;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quill.database.QuillRepository;
import com.example.quill.database.entities.Liked;
import com.example.quill.database.entities.Quill;

import java.util.List;


public class Quill_Item_RecyclerViewAdapter extends RecyclerView.Adapter<Quill_Item_RecyclerViewAdapter.MyViewHolder> {

    private  final QuillRecyclerViewInterface quillRecyclerViewInterface;
    List<Quill> quillsList;
    int userId;
    QuillRepository quillRepository;

    public Quill_Item_RecyclerViewAdapter(List<Quill> quillsList, QuillRecyclerViewInterface quillRecyclerViewInterface, int userId, QuillRepository quillrepo) {
        this.quillsList = quillsList;
        this.quillRecyclerViewInterface= quillRecyclerViewInterface;
        this.userId= userId;
        this.quillRepository = quillrepo;
    }

    @NonNull
    @Override
    public Quill_Item_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quill_item_recycler_view_row, parent, false);
        return new MyViewHolder(view, quillRecyclerViewInterface, userId, quillRepository, quillsList);
    }

    @Override
    public void onBindViewHolder(@NonNull Quill_Item_RecyclerViewAdapter.MyViewHolder holder, int position) {

        Quill quill = quillsList.get(position);
        holder.quillItemTitle.setText(quill.getTitle());

        if ("Health".equalsIgnoreCase(quill.getCategory())) {
            holder.quillCardView.setBackgroundResource(R.drawable.health_gradient);
            holder.quillItemBadgeImgView.setImageResource(R.drawable.healthbadge);
        } else if ("Sports".equalsIgnoreCase(quill.getCategory())) {
            holder.quillCardView.setBackgroundResource(R.drawable.sports_gradient);
            holder.quillItemBadgeImgView.setImageResource(R.drawable.sportsbadge);
        } else if ("Science".equalsIgnoreCase(quill.getCategory())) {
            holder.quillCardView.setBackgroundResource(R.drawable.science_gradient);
            holder.quillItemBadgeImgView.setImageResource(R.drawable.sciencebadge);
        } else {
            // Default badge if category doesn't match
            holder.quillItemBadgeImgView.setImageResource(R.drawable.createitempageselectedbox);
        }

    }

    @Override
    public int getItemCount() {
        return quillsList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView quillItemBadgeImgView;
        TextView quillItemTitle;
        CardView quillCardView;
        ImageButton likedItem;
        QuillRepository repository;

        List<Quill> quills;

        public MyViewHolder(@NonNull View itemView, QuillRecyclerViewInterface quillRecyclerViewInterface, int userId, QuillRepository quillRepo, List<Quill> quillList) {
            super(itemView);
            quillItemTitle = itemView.findViewById(R.id.quillItemTextView);
            quillItemBadgeImgView = itemView.findViewById(R.id.quillItemBadgeImgView);
            quillCardView = itemView.findViewById(R.id.quillCardView);
            likedItem = itemView.findViewById(R.id.quillItemHeartIcon);
            repository = quillRepo;
            quills = quillList;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(quillRecyclerViewInterface != null){

                        int pos = getAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION){
                            quillRecyclerViewInterface.onItemClick(pos);
                        }

                    }
                }
            });

            likedItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(), "Liked item!", Toast.LENGTH_SHORT).show();

                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        Quill clickedQuill = quills.get(position);
                        Liked liked = new Liked(clickedQuill.getTitle(), clickedQuill.getContent(), clickedQuill.getCategory(), userId);
                        repository.insertLikedQuill(liked);
                    }
                }
            });
        }
    }

}
