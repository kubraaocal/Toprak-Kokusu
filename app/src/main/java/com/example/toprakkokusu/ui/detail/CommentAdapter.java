package com.example.toprakkokusu.ui.detail;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toprakkokusu.R;
import com.example.toprakkokusu.UserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    List<CommentModel> commentModelList;
    Context context;
    private DatabaseReference userDbRef;

    public CommentAdapter(List<CommentModel> commentModelList, Context context) {
        this.commentModelList = commentModelList;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.comment_card,parent,false);
        return new CommentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.comment.setText(commentModelList.get(position).getComment());
        holder.time.setText(commentModelList.get(position).getTime());
        Log.e("ID","KULLLANICI İD "+commentModelList.get(position).getUserId());
        userDbRef = FirebaseDatabase.getInstance().getReference().child("Users");
        ValueEventListener commentListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    UserModel userModel = snapshot.child(commentModelList.get(holder.getAdapterPosition()).userId).getValue(UserModel.class);
                    holder.name.setText(userModel.getNameSurname());
                    Picasso.get().load(userModel.getPhoto()).noFade().into(holder.photo);
                    Log.e("ID", "comments sayısı "+ userModel);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("TAG", "loadPost:onCancelled", error.toException());
            }
        };
        userDbRef.addValueEventListener(commentListener);
        //Uri imageUri = Uri.parse(activityModelList.get(position).getPhoto());
    }

    void clearList(){
        commentModelList.clear();
    }

    @Override
    public int getItemCount() {
        return commentModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView photo;
        private TextView name,comment,time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            photo=itemView.findViewById(R.id.user_image_view);
            name=itemView.findViewById(R.id.user_name_text_view);
            comment=itemView.findViewById(R.id.comment_text_view);
            time=itemView.findViewById(R.id.time_text_view);
        }
    }
}
