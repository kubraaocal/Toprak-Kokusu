package com.example.toprakkokusu.ui.home;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toprakkokusu.ui.detail.CampDetailActivity;
import com.example.toprakkokusu.CampModel;
import com.example.toprakkokusu.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> implements Filterable {
    private List<CampModel> campModelList;
    private List<CampModel> campModelListFull;
    private Context context;

    public HomeAdapter(List<CampModel> campModelList, Context context) {
        this.campModelList = campModelList;
        this.campModelListFull = new ArrayList<>(campModelList);
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
        try {
            holder.address.setText(campModelList.get(position).getLocation());
            holder.title.setText(campModelList.get(position).getCampName());
            //Uri imageUri = Uri.parse(activityModelList.get(position).getPhoto());
            Picasso.get().load(campModelList.get(position).getImage()).noFade().into(holder.photo);
        }catch (Exception e){
            Log.e("TAG","Yuklenmedi");
        }

    }

    @Override
    public int getItemCount() {
        return campModelList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            List<CampModel> filteredList=new ArrayList<>();
            if(charSequence.toString().isEmpty()){
                filteredList.addAll(campModelListFull);
            }else{
                for(CampModel campModel:campModelListFull){
                    if(campModel.getCampName().toLowerCase().contains(charSequence.toString().toLowerCase())){
                        filteredList.add(campModel);
                    }
                }
            }
            FilterResults filterResults=new FilterResults();
            filterResults.values=filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            campModelList.clear();
            campModelList.addAll((Collection<? extends CampModel>) filterResults.values);
            notifyDataSetChanged();
        }
    };

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
                    //Log.e("ID","id degeri "+campModelList.get(getAdapterPosition()).getId()+" "+campModelList.get(getAdapterPosition()).getPhoto());
                    int position = getAdapterPosition();
                    Intent intent=new Intent(context,CampDetailActivity.class);
                    intent.putExtra("campid",campModelList.get(position).getId());
                    intent.putExtra("photoid",campModelList.get(position).getPhoto());
                    context.startActivity(intent);
                }
            });
        }



    }
}
