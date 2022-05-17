package com.example.toprakkokusu;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.ViewHolder> {

    ArrayList<String> images=new ArrayList<>();

    public SliderAdapter(ArrayList<String> images){
        this.images=images;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Uri imageUri = Uri.parse(images.get(position).toString());
        Picasso.get()
                .load(imageUri)
                .noFade()
                .into(viewHolder.imageView);
    }

    @Override
    public int getCount() {
        return images.size();
    }


    public class ViewHolder extends SliderViewAdapter.ViewHolder{
        ImageView imageView;

        public ViewHolder(View itemView){
            super(itemView);

            imageView=itemView.findViewById(R.id.slider_item_image);
        }
    }
}
