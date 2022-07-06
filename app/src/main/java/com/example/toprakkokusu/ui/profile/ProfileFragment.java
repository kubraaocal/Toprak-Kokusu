package com.example.toprakkokusu.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.toprakkokusu.CampModel;
import com.example.toprakkokusu.LoginActivity;
import com.example.toprakkokusu.UserModel;
import com.example.toprakkokusu.databinding.FragmentProfileBinding;
import com.example.toprakkokusu.ui.detail.CampDetailActivity;
import com.example.toprakkokusu.ui.home.HomeAdapter;
import com.example.toprakkokusu.ui.profile.tabfragment.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
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
    private FirebaseAuth mAuth;

    private TextView userNameSurname;
    private ImageView userPhoto;
    private ImageButton buttonLogOut;

    private DatabaseReference userDbRef,campDbRef;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mAuth=FirebaseAuth.getInstance();

        //binding = ActivityMain2Binding.inflate(getLayoutInflater());
        //setContentView(binding.getRoot());

        userModelList=new ArrayList<>();

        userNameSurname=binding.userNameSurnameTextView;
        userPhoto=binding.userProfileImageView;
        buttonLogOut=binding.buttonLogOut;

        buttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("TAG","tıklandı");
                mAuth.signOut();
                Intent intent=new Intent(getContext(), LoginActivity.class);
                startActivity(intent);


            }
        });
        //recyclerView=binding.favoriteCampRecyclerView;

        TabLayout tabLayout=binding.tabLayout;
        ViewPager2 viewPager2=binding.viewPager;

        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(this);
        viewPager2.setAdapter(viewPagerAdapter);

        String [] tabTtiles={"Favorilerim","Kamp Yerlerim","Etkinliklerim"};


        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabTtiles[position]);
            }
        }).attach();


        mAuth = FirebaseAuth.getInstance();
        userDbRef = FirebaseDatabase.getInstance().getReference().child("Users");
        campDbRef=FirebaseDatabase.getInstance().getReference().child("Camp");

        //final TextView textView = binding.textNotifications;
        //notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);


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

        userDbRef.addValueEventListener(userListener);

        return root;
    }

    private void setDatainTextView() {
        userNameSurname.setText(userModelList.get(0).getNameSurname());
        Picasso.get().load(userModelList.get(0).getPhoto()).noFade().into(userPhoto);
        Log.e("PHT",userModelList.get(0).getPhoto());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        userModelList.clear();
    }
}