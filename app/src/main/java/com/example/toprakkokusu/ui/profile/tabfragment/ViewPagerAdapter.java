package com.example.toprakkokusu.ui.profile.tabfragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new FavoriteFragment();
            case 1:
                return new CampFragment();
            default:
                return new ActivityFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
