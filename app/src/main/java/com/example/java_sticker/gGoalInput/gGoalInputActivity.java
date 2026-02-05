package com.example.java_sticker.gGoalInput;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.java_sticker.Fragment.FragJoin;
import com.example.java_sticker.Fragment.w_FragJoin;
import com.example.java_sticker.R;

import java.util.ArrayList;

import com.google.android.material.tabs.TabLayout;
import com.example.java_sticker.gGoalInput.gGoalInputAdapter;

public class gGoalInputActivity extends AppCompatActivity {
    private FrameLayout fl;



    Fragment fragment = null;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    gGoalInputAdapter gGoalInputAdapter;
    private TabLayout tabLayout;

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_g_input);

        fl = findViewById(R.id.input_framelayout);
        tabLayout = findViewById(R.id.tab_layout);


        fragment = new First();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.input_framelayout, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();

        //viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        gGoalInputAdapter adapter = new gGoalInputAdapter(getSupportFragmentManager(), 3);
        //viewPager.setAdapter(adapter);


    }

    //fragment 생성
    public void createFragment() {
//        first = new First();
//        second = new Second();
//        third = new Third();
    }

    //viewpager 및 어댑터 생성
    public void createViewpager() {
//        viewPager = findViewById(R.id.input_viewPager);
//        gGoalInputAdapter = new gGoalInputAdapter(getSupportFragmentManager(), 3);
//        gGoalInputAdapter.addFragment(first);
//        gGoalInputAdapter.addFragment(second);
//        gGoalInputAdapter.addFragment(third);
//
//        viewPager.setAdapter(gGoalInputAdapter);
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        //viewPager.setUserInputEnabled(false);//터치 스크롤 막음
    }

    //tablayout - viewpager 연결
    public void settingTabLayout() {
//        tabLayout = findViewById(R.id.tab_layout);
//        tabLayout.addOnTabSelectedListener(new com.google.android.material.tabs.TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(com.google.android.material.tabs.TabLayout.Tab tab) {
//                int pos = tab.getPosition();
//
//                switch (pos) {
//                    case 0:
//                        viewPager.setCurrentItem(0);
//                        break;
//                    case 1:
//                        viewPager.setCurrentItem(1);
//                        break;
//                    case 2:
//                        viewPager.setCurrentItem(2);
//                        break;
//                }
//            }
//
//            @Override
//            public void onTabUnselected(com.google.android.material.tabs.TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(com.google.android.material.tabs.TabLayout.Tab tab) {
//
//            }
//
//
//        });
    }

    private void setViewPager() {

//        ViewPager viewPager = findViewById(R.id.viewPager);
//        viewPager.setAdapter(adapter);
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//        tabLayout = view.findViewById(R.id.tab_layout);
//        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
        //       });
    }

    // @TargetApi(Build.VERSION_CODES.N)
    private void setTabLayout() {
//        tabNames.forEach(name -> tabLayout.addTab(tabLayout.newTab().setText(name)));
    }

    private void loadTabName() {
//        tabNames.add("그룹골1");
//        tabNames.add("그룹골2");
//        tabNames.add("그룹골3");
    }
}
