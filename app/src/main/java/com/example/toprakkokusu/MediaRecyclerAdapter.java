package com.example.toprakkokusu;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MediaRecyclerAdapter extends RecyclerView.Adapter<MediaRecyclerAdapter.ViewHolder> {

    private ArrayList<Uri> uriArrayList;

    public MediaRecyclerAdapter(ArrayList<Uri> uriArrayList) {
        this.uriArrayList = uriArrayList;
    }

    @NonNull
    @Override
    public MediaRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.custom_single_image,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MediaRecyclerAdapter.ViewHolder holder, int position) {
        Log.e("LOG","Veri "+uriArrayList.get(position));
        Uri imageUri = Uri.parse(uriArrayList.get(position).toString());
        Picasso.get()
                .load(imageUri)
                .noFade()
                .into(holder.imageView);
       /*Glide.with(context)
                .load(new File(imageUri.getPath()))
                .transform(new CenterCrop())
    .into(holder.imageView);*/ //Glide ile kullanım olmuyor
        //holder.imageView.setImageURI(uriArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return uriArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.imagee);
        }
    }
}
