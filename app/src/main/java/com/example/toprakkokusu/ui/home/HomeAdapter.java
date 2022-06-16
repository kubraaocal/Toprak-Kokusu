package com.example.toprakkokusu.ui.home;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toprakkokusu.CampModel;
import com.example.toprakkokusu.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private List<CampModel> campModelList;
    private Context context;

    public HomeAdapter(List<CampModel> campModelList, Context context) {
        this.campModelList = campModelList;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.home_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.address.setText(campModelList.get(position).getLocation());
        holder.title.setText(campModelList.get(position).getCampName());
        //Uri imageUri = Uri.parse(activityModelList.get(position).getPhoto());
        Picasso.get().load(campModelList.get(position).getImage()).noFade().into(holder.photo);
    }

    @Override
    public int getItemCount() {
        return campModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView photo;
        private TextView title,address;
        private ConstraintLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            photo=itemView.findViewById(R.id.camp_image_view);
            title=itemView.findViewById(R.id.camp_title_text_view);
            address=itemView.findViewById(R.id.camp_address_text_view);
            layout=itemView.findViewById(R.id.home_card_view);

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("ID","id degeri "+campModelList.get(getAdapterPosition()).getId());
                }
            });
        }



    }
}
