package com.example.toprakkokusu.ui.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toprakkokusu.CampModel;
import com.example.toprakkokusu.UserModel;
import com.example.toprakkokusu.databinding.FragmentProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment  {

    private FragmentProfileBinding binding;
    private List<UserModel> userModelList;
    private List<String> favoriteCampIdList;
    private List<CampModel> campModelList;
    private FirebaseAuth mAuth;

    private TextView userNameSurname;
    private ImageView userPhoto;
    private RecyclerView recyclerView;
    private FavoriteAdapter favoriteAdapter;

    private DatabaseReference userDbRef,campDbRef;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        userModelList=new ArrayList<>();
        favoriteCampIdList=new ArrayList<>();
        campModelList=new ArrayList<>();

        userNameSurname=binding.userNameSurnameTextView;
        userPhoto=binding.userProfileImageView;
        recyclerView=binding.favoriteCampRecyclerView;

        favoriteAdapter=new FavoriteAdapter(campModelList,getContext());

        mAuth = FirebaseAuth.getInstance();
        userDbRef = FirebaseDatabase.getInstance().getReference().child("Users");
        campDbRef=FirebaseDatabase.getInstance().getReference().child("Camp");

        //final TextView textView = binding.textNotifications;
        //notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);



        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(favoriteAdapter);
        recyclerView.addItemDecoration(new
                DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));

        ValueEventListener userListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userModelList.add(snapshot.child(mAuth.getUid()).getValue(UserModel.class));
                setDatainTextView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("TAG", "loadPost:onCancelled", error.toException());
            }
        };

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




        userDbRef.addValueEventListener(userListener);
        userDbRef.addValueEventListener(favoriteIdListener);



        return root;
    }

    private void setDatainTextView() {
        userNameSurname.setText(userModelList.get(0).getNameSurname());
        Picasso.get().load(userModelList.get(0).getPhoto()).noFade().into(userPhoto);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }






}