package com.example.quill;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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

public class Liked_Item_Recycler_ViewAdapter extends RecyclerView.Adapter<Liked_Item_Recycler_ViewAdapter.MyViewHolder> {

    private final QuillRecyclerViewInterface quillRecyclerViewInterface;
    List<Liked> likedList;
    int userId;
    QuillRepository quillRepository;

    public Liked_Item_Recycler_ViewAdapter(List<Liked> likedList, QuillRecyclerViewInterface quillRecyclerViewInterface,int userId, QuillRepository quillrepo) {
        this.likedList = likedList;
        this.quillRecyclerViewInterface= quillRecyclerViewInterface;
        this.userId= userId;
        this.quillRepository = quillrepo;
    }

    @NonNull
    @Override
    public Liked_Item_Recycler_ViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quill_item_recycler_view_row, parent, false);
        return new Liked_Item_Recycler_ViewAdapter.MyViewHolder(view, quillRecyclerViewInterface, userId, quillRepository, likedList);
    }

    @Override
    public void onBindViewHolder(@NonNull Liked_Item_Recycler_ViewAdapter.MyViewHolder holder, int position) {

        Liked likedItem = likedList.get(position);
        holder.quillItemTitle.setText(likedItem.getTitle());
        holder.likedItem.setBackgroundResource(R.drawable.iconheartfilled);

        if ("Health".equalsIgnoreCase(likedItem.getCategory())) {
            holder.quillCardView.setBackgroundResource(R.drawable.health_gradient);
            holder.quillItemBadgeImgView.setImageResource(R.drawable.healthbadge);
        } else if ("Sports".equalsIgnoreCase(likedItem.getCategory())) {
            holder.quillCardView.setBackgroundResource(R.drawable.sports_gradient);
            holder.quillItemBadgeImgView.setImageResource(R.drawable.sportsbadge);
        } else if ("Science".equalsIgnoreCase(likedItem.getCategory())) {
            holder.quillCardView.setBackgroundResource(R.drawable.science_gradient);
            holder.quillItemBadgeImgView.setImageResource(R.drawable.sciencebadge);
        } else {
            // Default badge if category doesn't match
            holder.quillItemBadgeImgView.setImageResource(R.drawable.createitempageselectedbox);
        }

    }

    @Override
    public int getItemCount() {
        return likedList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView quillItemBadgeImgView;
        TextView quillItemTitle;
        CardView quillCardView;
        ImageButton likedItem;

        QuillRepository repository;
        List<Liked> likedItems;

        public MyViewHolder(@NonNull View itemView, QuillRecyclerViewInterface quillRecyclerViewInterface, int userId, QuillRepository quillRepo, List<Liked> likedList) {
            super(itemView);
            quillItemTitle = itemView.findViewById(R.id.quillItemTextView);
            quillItemBadgeImgView = itemView.findViewById(R.id.quillItemBadgeImgView);
            quillCardView = itemView.findViewById(R.id.quillCardView);
            likedItem = itemView.findViewById(R.id.quillItemHeartIcon);

            repository = quillRepo;
            likedItems = likedList;
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

                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        Liked clickedLiked = likedItems.get(position);
                        repository.deleteLikedQuill(clickedLiked);
                    }
                }
            });
        }
    }

}
