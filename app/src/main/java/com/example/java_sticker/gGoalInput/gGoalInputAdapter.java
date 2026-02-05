package com.example.java_sticker.gGoalInput;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class gGoalInputAdapter extends FragmentPagerAdapter {

//    private final List<Fragment> mFragmentList = new ArrayList<>();

    private int tabCount;


    public gGoalInputAdapter(@NonNull FragmentManager fragmentManager, int tabCount) {
        super(fragmentManager, tabCount);
        this.tabCount = tabCount;
    }

    @NonNull
    @Override
    //position에 맞게 해당 fragment를 return 해주기
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new First();
            case 1:
                return new Second();
            case 2:
                return new Third();
            default:
                return null;
        }
    }


    //전체 페이지 개수
    @Override
    public int getCount() {
        return tabCount;
    }


}
