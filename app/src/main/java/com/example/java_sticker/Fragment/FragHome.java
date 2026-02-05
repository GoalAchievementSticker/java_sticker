package com.example.java_sticker.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.java_sticker.Group_main;
import com.example.java_sticker.R;
import com.example.java_sticker.mypage;
import com.example.java_sticker.personal.MainActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragHome extends Fragment {
    private View view;
    ImageButton study_category;
    ImageButton exercise_category;
    ImageButton hobby_category;
    ImageButton routin_category;

    Toolbar searchView_home;

    Group_main group_main;

    //네비게이션 드로우
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    //Toolbar toolbar;
    ActionBarDrawerToggle barDrawerToggle;

    //nav속 이미지, 이름
    TextView user_email;
    TextView nav_name;
    CircleImageView nav_img;

    String uid;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference("personalDialog");
    DatabaseReference profile_databaseReference = firebaseDatabase.getReference();

    TextView userEmail;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        group_main = (Group_main) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        group_main = null;
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fraghome, container, false);

        study_category = (ImageButton) view.findViewById(R.id.home_study_button);
        exercise_category = (ImageButton) view.findViewById(R.id.home_exercise_button);
        hobby_category = (ImageButton) view.findViewById(R.id.home_hobby_button);
        routin_category = (ImageButton) view.findViewById(R.id.home_routin_button);


        searchView_home = (Toolbar) view.findViewById(R.id.home_search);
        ((AppCompatActivity) getActivity()).setSupportActionBar(searchView_home);



        //파이어베이스
        user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        uid = user.getUid();

        drawerLayout = (DrawerLayout) view.findViewById(R.id.layout_drawer_fraghome);
        navigationView = (NavigationView) view.findViewById(R.id.nav);
        navigationView.setItemIconTintList(null);

        //Drawer 토글버튼 생성
        barDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, searchView_home, R.string.app_name, R.string.app_name);
        //삼선아이콘 모양으로 보이기, 동기맞춤
        barDrawerToggle.syncState();
        //삼선 아이콘 화살표 아이콘 자동 변환
        drawerLayout.addDrawerListener(barDrawerToggle);

        //네비게이션 프로필 이름, 이미지 가져오기
        profile_databaseReference.child("user").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child("userName").getValue(String.class);
                String uri = snapshot.child("profileImageUrl").getValue(String.class);
                String email = snapshot.child("userEmail").getValue(String.class);
                nav_img = view.findViewById(R.id.iv_header);
                Glide.with(navigationView)
                        .load(uri)
                        .into(nav_img);
                nav_name = view.findViewById(R.id.nav_name);
                nav_name.setText(name + "님");
                user_email=view.findViewById(R.id.user_email);
                user_email.setText(email);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //네비게이션뷰의 아이템 클릭시
        navigationView.setNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId(); // item.getItemId()를 변수에 할당하여 코드의 가독성을 높입니다.

            if (itemId == R.id.personal_goal) {
                Intent personalIntent = new Intent(getActivity(), MainActivity.class);
                startActivity(personalIntent);
                getActivity().finish();
                //Toast.makeText(MainActivity.this, "그룹도장판", Toast.LENGTH_SHORT).show();
            }
            // 다른 아이템이 있다면 else if를 사용하여 계속 추가할 수 있습니다.
            // else if (itemId == R.id.another_item) {
            //     // 또 다른 아이템 클릭 시 실행할 코드
            // }

            drawerLayout.closeDrawer(navigationView);

            return false;
        });

        searchView_home.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.search) {
                    group_main.onFragmentChange(4);
                }
                if (id == R.id.noti) {
                    group_main.onFragmentChange(7);
                }
                return true;
            }
        });


        setHasOptionsMenu(true);

        //0:공부, 1:운동, 2:취미, 3:루틴
        study_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                group_main.onFragmentChange(0);

            }
        });

        exercise_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                group_main.onFragmentChange(1);
            }
        });

        hobby_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                group_main.onFragmentChange(2);
            }
        });

        routin_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                group_main.onFragmentChange(3);
            }
        });

        return view;
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }
}
