package com.example.java_sticker.Fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.java_sticker.Group_main;
import com.example.java_sticker.R;
import com.example.java_sticker.group.Custom_g_item_adapter;
import com.example.java_sticker.group.GroupDialog;
import com.example.java_sticker.group.g_GridItem;
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
import java.util.List;

public class j_FragJoin extends Fragment {
    View view;

    //툴바유저네임
    TextView g_user_name;
    //g_goal_iv
    ImageView g_pics;

    //FB
    String uid;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference profile_databaseReference = firebaseDatabase.getReference();
    DatabaseReference databaseReference = firebaseDatabase.getReference("GroupDialog");
    DatabaseReference categoryReference = firebaseDatabase.getReference("Category");

    //그리드뷰 데이터 저장
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    DatabaseReference ds;
    g_GridItem gd;


    //ProgressBar circleProgressBar;
    com.example.java_sticker.CustomProgress customProgress;


    TextView custom_g_goal_tittle;

    ArrayList<g_GridItem> items;

    //dialog
    ArrayList<GroupDialog> gDialog;
    Dialog custom_dialog;


    //Recycler
    RecyclerView g_goal_recycler;
    Custom_g_item_adapter gAdapter;
    View cv;

    Group_main group_main;


    @Override
    public void onResume() {
        super.onResume();
        ReadGroupDialog();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        group_main = null;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.j_fragjoin, container, false);
        //파이어베이스
        user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        uid = user.getUid();

        //다이얼로그 선언
        gDialog = new ArrayList<>();


        //리사이클러뷰 선언
        g_goal_recycler = view.findViewById(R.id.recyclerview_g_goal);
        customProgress = view.findViewById(R.id.customProgress_g_goal_c);
        custom_g_goal_tittle = view.findViewById(R.id.custom_g_goal_tittle);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        g_goal_recycler.setLayoutManager(linearLayoutManager);

        //리사이클러뷰 어댑터 연결
        gAdapter = new Custom_g_item_adapter(getContext(), gDialog);
        g_goal_recycler.setAdapter(gAdapter);
        //리사이클러뷰 클릭했을때 나오는 도장판 연결
        items = new ArrayList<g_GridItem>();



        //툴바 유저 네임 설정
        profile_databaseReference.child("user").child(uid).child("userName").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.getValue(String.class);
                g_user_name = view.findViewById(R.id.userN);
                g_user_name.setText(name + "님");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        cv = inflater.inflate(R.layout.custom_g_goal, container, false);
        //그룹방 이미지(일단 프사로 설정) -> 어댑터에서 적용함
        profile_databaseReference.child("user").child(uid).child("profileImageUrl").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //항상 카드뷰 읽어오기
        ReadGroupDialog();
        return view;
    }

    //다이얼로그 저장된 함수 가져오기
    private void ReadGroupDialog() {
        databaseReference.child(uid).child("dialog_group").addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                gDialog.clear();
                List<String> uid = new ArrayList<>();
                //Log.d("TAG", String.valueOf(snapshot));
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String key = dataSnapshot.getKey();
                    GroupDialog read_g = dataSnapshot.getValue(GroupDialog.class);
                    assert read_g != null;
                    read_g.key = key;

                    //제한인원이랑 참가인원이 같은것만 가져오기 or +(추가)마감버튼 눌렀다는 true변수이면 가져오기
                    if(read_g.isClose() == true){
                        gDialog.add(read_g);
                    }


                }
                gAdapter.notifyDataSetChanged();
                g_goal_recycler.setAdapter(gAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getContext(), "불러오기 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
