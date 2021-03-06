package com.example.toprakkokusu.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.toprakkokusu.CampModel;
import com.example.toprakkokusu.databinding.FragmentActivityBinding;
import com.example.toprakkokusu.databinding.FragmentHomeBinding;
import com.example.toprakkokusu.ui.activity.ActivityAdapter;
import com.example.toprakkokusu.ui.activity.ActivityModel;
import com.example.toprakkokusu.ui.home.suggestion.SuggestionAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView,suggestedRecyclerView;
    private HomeAdapter homeAdapter;
    private SuggestionAdapter suggestedAdapter;
    private List<CampModel> campModelList=new ArrayList<>();
    private List<CampModel> suggestedList=new ArrayList<>();
    private JSONArray oneri=new JSONArray();
    private SearchView textSearch;
    private List<String> suggestedIdList=new ArrayList<>();

    private DatabaseReference homeDbRef,userDbRef;

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        /*final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);*/
        homeDbRef = FirebaseDatabase.getInstance().getReference().child("Camp");
        userDbRef=FirebaseDatabase.getInstance().getReference().child("MyCamp");

        textSearch=binding.searchView;
        recyclerView=binding.homeRecyclerView;
        suggestedRecyclerView=binding.homeSuggestionRecyclerView;

        homeAdapter=new HomeAdapter(campModelList,getContext());
        suggestedAdapter=new SuggestionAdapter(suggestedList,getContext());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        suggestedRecyclerView.setLayoutManager(layoutManager2);
        recyclerView.setAdapter(homeAdapter);
        suggestedRecyclerView.setAdapter(suggestedAdapter);
        recyclerView.addItemDecoration(new
                DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));

        textSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                homeAdapter.getFilter().filter(newText);
                return false;
            }
        });

        Log.e("SUG","1. buras??");
        apiIstek();


        Log.e("USER","user my camp "+userDbRef);
        ValueEventListener suggestedListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                suggestedList.clear();
                suggestedIdList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    //Log.e("TAG","ds de??eri "+ds.getValue()+" ");
                    for(DataSnapshot a:ds.getChildren()){
                        //Log.e("TAG","ds de??eri "+a+" ");
                        try {
                            for(int i=0;i<oneri.length();i++){
                                if(a.getKey().equals(oneri.get(i).toString()) && a.getValue().equals(true) ){
                                    CampModel campModel = ds.getValue(CampModel.class);
                                    if(!suggestedIdList.contains(campModel.getId())){
                                        suggestedIdList.add(campModel.getId());
                                        suggestedList.add(campModel);
                                        suggestedAdapter.notifyDataSetChanged();
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                }
                Log.e("SUG","suggestion list "+suggestedList.size()+ " oneri size "+oneri.length());
                // ..
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException());
            }
        };

        ValueEventListener homeListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                campModelList.clear();
                //Log.e("TAG","ds de??eri "+campModelList.size()+" ");
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    CampModel campModel = ds.getValue(CampModel.class);
                    campModelList.add(campModel);
                    homeAdapter.notifyDataSetChanged();
                    }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException());
            }

        };

        homeDbRef.addValueEventListener(homeListener);
        homeDbRef.addValueEventListener(suggestedListener);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        Log.e("SUG","2. buras??");

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("SUG","3. buras??");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("SUG","4. buras??");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("SUG","5. buras??");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("SUG","6. buras??");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("SUG","7. buras??");
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.e("SUG","attach buras??");
        apiIstek();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("SUG","create buras??");
    }

    private void apiIstek(){
        Log.e("TAG","oneri listesi "+oneri.length()+" ");
        // RequestQueue ayarlar?? yap??l??yor.
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url ="http://10.0.2.2:5000/";

        // Sa??lanan URL???den bir dize yan??t?? istenmektedir.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            oneri=response.getJSONArray("data");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.e("TAG","error "+error);
                    }
                });


        //iste??i RequestQueue ekliyoruz.
        queue.add(jsonObjectRequest);
    }
}