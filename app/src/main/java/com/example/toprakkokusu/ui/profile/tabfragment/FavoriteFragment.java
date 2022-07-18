package com.example.toprakkokusu.ui.profile.tabfragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.toprakkokusu.CampModel;
import com.example.toprakkokusu.R;
import com.example.toprakkokusu.UserModel;
import com.example.toprakkokusu.ui.home.HomeAdapter;
import com.example.toprakkokusu.ui.profile.FavoriteAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment {

    private List<UserModel> userModelList;
    private List<String> favoriteCampIdList;
    private List<CampModel> campModelList;
    private FirebaseAuth mAuth;

    private RecyclerView recyclerView;
    private HomeAdapter favoriteAdapter;

    private DatabaseReference userDbRef,campDbRef;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        userModelList=new ArrayList<>();
        favoriteCampIdList=new ArrayList<>();
        campModelList=new ArrayList<>();

        recyclerView=view.findViewById(R.id.favorite_recycler_view);

        favoriteAdapter=new HomeAdapter(campModelList,getContext());

        mAuth = FirebaseAuth.getInstance();
        userDbRef = FirebaseDatabase.getInstance().getReference().child("Users");
        campDbRef=FirebaseDatabase.getInstance().getReference().child("Camp");

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(favoriteAdapter);
        recyclerView.addItemDecoration(new
                DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));


        ValueEventListener favoriteIdListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                favoriteCampIdList.clear();
                for (DataSnapshot ds : dataSnapshot.child(mAuth.getUid()).child("Favorites").getChildren()) {
                    favoriteCampIdList.add(ds.getValue().toString());
                    Log.e("TAG",favoriteCampIdList.size()+" "+favoriteCampIdList.get(0));
                }
                // ..
                ValueEventListener favoriteCampListener=new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        campModelList.clear();
                        for(int i=0;i<favoriteCampIdList.size();i++){
                            Log.e("TAG",snapshot.child(favoriteCampIdList.get(i)).getValue().toString());
                            campModelList.add(snapshot.child(favoriteCampIdList.get(i)).getValue(CampModel.class));
                            favoriteAdapter.notifyDataSetChanged();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.w("TAG", "loadPost:onCancelled", error.toException());
                    }
                };
                campDbRef.addValueEventListener(favoriteCampListener);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException());
            }
        };

        userDbRef.addValueEventListener(favoriteIdListener);

        return view;
    }
}
