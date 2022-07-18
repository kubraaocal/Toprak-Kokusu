package com.example.toprakkokusu.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.toprakkokusu.ui.activity.ActivityModel;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private ActivityModel activityModel;

    public HomeViewModel() {
        activityModel=new ActivityModel("asdasd","sdasdas","asdsadas","dd");
        mText = new MutableLiveData<>();
        mText.setValue(activityModel.getText());
    }

    public LiveData<String> getText() {
        return mText;
    }
}