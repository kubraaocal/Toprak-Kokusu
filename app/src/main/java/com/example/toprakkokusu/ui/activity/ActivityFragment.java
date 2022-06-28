package com.example.toprakkokusu.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toprakkokusu.MapFragment;
import com.example.toprakkokusu.databinding.FragmentActivityBinding;
import com.example.toprakkokusu.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ActivityFragment extends Fragment implements View.OnClickListener {

    private FragmentActivityBinding binding;
    private RecyclerView recyclerView;
    private ActivityAdapter activityAdapter;
    private List<ActivityModel> activityModelList;
    private ImageButton addActivityButton;

    private DatabaseReference activityDbRef;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ActivityViewModel activityViewModel =
                new ViewModelProvider(this).get(ActivityViewModel.class);

        binding = FragmentActivityBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        activityDbRef = FirebaseDatabase.getInstance().getReference().child("Activity");

        activityModelList=new ArrayList<>();

        addActivityButton=binding.imageAddActivityButton;
        addActivityButton.setOnClickListener(this);

        recyclerView=binding.activityRecyclerView;
        activityAdapter=new ActivityAdapter(activityModelList,getContext());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(activityAdapter);
        recyclerView.addItemDecoration(new
                DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));

        ValueEventListener activityListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                activityModelList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    ActivityModel activityModel = ds.getValue(ActivityModel.class);
                    activityModelList.add(activityModel);
                    Log.e("TAG","list size içerisi "+activityModelList.get(0).getText());
                    activityAdapter.notifyDataSetChanged();
                }
                // ..
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException());
            }

        };

        activityDbRef.addValueEventListener(activityListener);
        Log.e("TAG","list size dışarısı "+activityModelList.size());


        //final TextView textView = binding.textNotifications;
        //notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);



        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.image_add_activity_button:
                startActivity(new Intent(getContext(), CreateActivityActivity.class));
                break;
        }
    }
}