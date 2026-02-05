package com.example.java_sticker.personal;

import static java.lang.Integer.parseInt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.java_sticker.CustomProgress;
import com.example.java_sticker.Group_main;
import com.example.java_sticker.R;
import com.example.java_sticker.mypage;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import in.srain.cube.views.GridViewWithHeaderAndFooter;

public class MainActivity extends AppCompatActivity {


    private TextView header_goal;
    private Custom_pAdapter adapter;
    ArrayList<GridItem> items;
    //GridItem gitems;
    ArrayList<personalDialog> pDialog;
    Dialog custom_dialog;
    Custom_p_item_adapter pAdapter;
    RecyclerView p_goal_recycler;
    //ProgressBar circleProgressBar;
    com.example.java_sticker.CustomProgress customProgress;
    TextView custom_p_goal_tittle;

    String uid;
    GridViewWithHeaderAndFooter gridView;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference("personalDialog");
    DatabaseReference profile_databaseReference = firebaseDatabase.getReference();

    //네비게이션 드로우
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle barDrawerToggle;
    FloatingActionButton fab_main;

    //FAB
    boolean isFabOpen;

    //nav속 이미지,이메일, 이름
    TextView nav_name;
    TextView user_email;
    CircleImageView nav_img;

    //그리드뷰 데이터 저장
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    DatabaseReference ds;
    GridItem gd;

    View view;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //네비게이션 설정
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawerLayout = (DrawerLayout) findViewById(R.id.layout_drawer);
        navigationView = (NavigationView) findViewById(R.id.nav);
        navigationView.setItemIconTintList(null);

        //파이어베이스
        user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        uid = user.getUid();


        //Drawer 토글버튼 생성
        barDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        //삼선아이콘 모양으로 보이기, 동기맞춤
        barDrawerToggle.syncState();
        //삼선 아이콘 화살표 아이콘 자동 변환
        drawerLayout.addDrawerListener(barDrawerToggle);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);


        //네비게이션 프로필 이름,이메일, 이미지 가져오기
        profile_databaseReference.child("user").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child("userName").getValue(String.class);
                String uri = snapshot.child("profileImageUrl").getValue(String.class);
                String email=snapshot.child("userEmail").getValue(String.class);
                nav_img = findViewById(R.id.iv_header);
                Glide.with(navigationView).load(uri).into(nav_img);
                nav_name = findViewById(R.id.nav_name);
                user_email=findViewById(R.id.user_email);
                user_email.setText(email);
                nav_name.setText(name+"님");


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //FAT
        fab_main = findViewById(R.id.fab);
        isFabOpen = false; // Fab 버튼 default는 닫혀있음

        //FAB 클릭 시
        fab_main.setOnClickListener(view -> toggleFab());


//        //네비게이션뷰의 아이템 클릭시
//        navigationView.setNavigationItemSelectedListener(item -> {
//            switch (item.getItemId()) {
//                case R.id.group_goal:
//                    Intent groupIntent = new Intent(MainActivity.this, Group_main.class);
//                    startActivity(groupIntent);
//                    finish();
//                    //Toast.makeText(MainActivity.this, "그룹도장판", Toast.LENGTH_SHORT).show();
//                    break;
//            }
//
//            drawerLayout.closeDrawer(navigationView);
//
//            return false;
//        });


        // 네비게이션뷰의 아이템 클릭시
        navigationView.setNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.group_goal) {
                Intent groupIntent = new Intent(MainActivity.this, Group_main.class);
                startActivity(groupIntent);
                finish();
                //Toast.makeText(MainActivity.this, "그룹도장판", Toast.LENGTH_SHORT).show();
            }

            // 만약 다른 아이템들이 있다면 else if를 추가하여 처리할 수 있습니다.
            // else if (itemId == R.id.some_other_item) {
            //     // ... 다른 아이템에 대한 코드 ...
            // }

            drawerLayout.closeDrawer(navigationView);

            return false;
        });


        //다이얼로그 선언
        pDialog = new ArrayList<>();
        gridView = (GridViewWithHeaderAndFooter) findViewById(R.id.gridView);
        //Button btn = (Button) findViewById(R.id.dialogButton);


        //리사이클러뷰 선언
        p_goal_recycler = (RecyclerView) findViewById(R.id.recyclerview_p_goal);
        //circleProgressBar = (ProgressBar) findViewById(R.id.custom_p_goal_progressbar);
        customProgress = (CustomProgress) findViewById(R.id.customProgress_g_goal_c);
        custom_p_goal_tittle = (TextView) findViewById(R.id.custom_p_goal_tittle);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        p_goal_recycler.setLayoutManager(linearLayoutManager);

        //리사이클러뷰 어댑터 연결
        pAdapter = new Custom_p_item_adapter(this, pDialog);
        p_goal_recycler.setAdapter(pAdapter);
        //리사이클러뷰 클릭했을때 나오는 도장판 연결
        items = new ArrayList<>();
        adapter = new Custom_pAdapter(this, items);


        //다이얼로그 값 저장된게 있다면
        if (pDialog != null) {
            ReadPersonalDialog();

        }


    }

    //메인 FAB 클릭 시
    @SuppressLint("Recycle")
    private void toggleFab() {
        // 플로팅 액션 버튼 열기
        showDialog();
        ObjectAnimator.ofFloat(fab_main, View.ROTATION, 0f, 45f).start();
    }


    public void showDialog() {
        custom_dialog = new Dialog(MainActivity.this);
        custom_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        custom_dialog.setContentView(R.layout.custom_p_dialog);
        custom_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //다이얼로그를 보여준다
        custom_dialog.show();


        //다이얼로그에 있는 텍스트들 연결
        EditText sticker_count = (EditText) custom_dialog.findViewById(R.id.sticker_count);
        EditText sticker_goal = (EditText) custom_dialog.findViewById(R.id.sticker_goal);

        //예스, 노 버튼 연결
        Button noBtn = custom_dialog.findViewById(R.id.noBtn);
        Button yesBtn = custom_dialog.findViewById(R.id.yesBtn);

        custom_dialog.setOnDismissListener(view -> {
                    ObjectAnimator.ofFloat(fab_main, View.ROTATION, 45f, 0f).start();
                }
        );
        //노버튼 클릭시 다이얼로그 닫기
        noBtn.setOnClickListener(view -> {
            custom_dialog.dismiss();
        });

        //예스 버튼 클릭시 다이얼로그 동작
        yesBtn.setOnClickListener(view -> {

            //다이얼로그에 입력한값 형 변환
            int vi = Integer.parseInt(sticker_count.getText().toString());
            String goal = sticker_goal.getText().toString();
            //파이어베이스 저장
            //고유키와 함께 저장히기 위한 장치
            String key = databaseReference.push().getKey();
            DatabaseReference keyRef = databaseReference.child(uid).child("dialog_personal").child(key);
            //list에 추가
            personalDialog personalDialog = new personalDialog(vi, goal, key, 0, "");
            pDialog.add(personalDialog);

            pAdapter.notifyDataSetChanged();


            //생성된 레코드 파이어베이스 저장
            keyRef.setValue(personalDialog);


            //도장판 gridview 데이터 저장
            ds = databaseReference.child(uid).child("goal_personal").child(key).child("도장판");
            for (int i = 0; i < vi; i++) {
                items.add(addGoal(i));
            }

            new Handler().postDelayed(() -> ReadPersonalDialog(), 400);


            custom_dialog.dismiss();


        });


    }


    //도장판칸 생성
    private GridItem addGoal(int i) {
        // Handle any errors
        storageRef.child("not2.png").getDownloadUrl()
                .addOnSuccessListener(uri -> {
                    // Got the download URL for 'plus.png'
                    gd = new GridItem(String.valueOf(i), uri.toString());
                    ds.child(String.valueOf(i)).setValue(gd);

                }).addOnFailureListener(Throwable::printStackTrace);

        return gd;
    }

    //다이얼로그 저장된 함수 가져오기
    private void ReadPersonalDialog() {

        databaseReference.child(uid).child("dialog_personal").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pDialog.clear();
                // Log.d("TAG", String.valueOf(snapshot));
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String key = dataSnapshot.getKey();
                    personalDialog read_p = dataSnapshot.getValue(personalDialog.class);
                    assert read_p != null;
                    read_p.key = key;
                    //Log.d("TAG", key);

                    pDialog.add(read_p);

                }
                pAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(MainActivity.this, "불러오기 실패", Toast.LENGTH_SHORT).show();
            }
        });

    }


}