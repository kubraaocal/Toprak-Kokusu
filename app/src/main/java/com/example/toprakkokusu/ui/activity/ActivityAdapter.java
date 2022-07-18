package com.example.toprakkokusu.ui.activity;

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
import androidx.recyclerview.widget.RecyclerView;

import com.example.toprakkokusu.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder> {
    private List<ActivityModel> activityModelList;
    private Context context;

    public ActivityAdapter(List<ActivityModel> activityModelList, Context context) {
        this.activityModelList = activityModelList;
        this.context = context;
        Log.e("TAG","list size "+activityModelList.size());
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.activity_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.text.setText(activityModelList.get(position).getText());
        holder.title.setText(activityModelList.get(position).getTitle());
        //Uri imageUri = Uri.parse(activityModelList.get(position).getPhoto());
        Picasso.get().load(activityModelList.get(position).getPhoto()).noFade().into(holder.photo);
    }

    @Override
    public int getItemCount() {
        return activityModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView photo;
        private TextView title,text;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            photo=itemView.findViewById(R.id.activityImageFilterView);
            title=itemView.findViewById(R.id.activityTitleTextView);
            text=itemView.findViewById(R.id.activityTextTextView);
        }
    }
}
