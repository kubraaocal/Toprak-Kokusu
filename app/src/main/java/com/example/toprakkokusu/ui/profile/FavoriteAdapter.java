package com.example.toprakkokusu.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toprakkokusu.CampModel;
import com.example.toprakkokusu.R;
import com.example.toprakkokusu.ui.detail.CampDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder>{
    List<CampModel> campModelList;
    Context context;

    public FavoriteAdapter(List<CampModel> campModelList, Context context) {
        this.campModelList = campModelList;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.home_card,parent,false);
        return new FavoriteAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.ViewHolder holder, int position) {
        //Uri imageUri = Uri.parse(activityModelList.get(position).getPhoto());
        Picasso.get().load(campModelList.get(position).getImage()).noFade().into(holder.photo);
    }

    @Override
    public int getItemCount() {
        return campModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView photo;
        private ConstraintLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            photo=itemView.findViewById(R.id.camp_image_view);
            layout=itemView.findViewById(R.id.home_card_view);

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("ID","id degeri "+campModelList.get(getAdapterPosition()).getId()+" "+campModelList.get(getAdapterPosition()).getPhoto());
                    int position = getAdapterPosition();
                    Intent intent=new Intent(context, CampDetailActivity.class);
                    intent.putExtra("campid",campModelList.get(position).getId());
                    intent.putExtra("photoid",campModelList.get(position).getPhoto());
                    context.startActivity(intent);
                }
            });
        }
    }
}
