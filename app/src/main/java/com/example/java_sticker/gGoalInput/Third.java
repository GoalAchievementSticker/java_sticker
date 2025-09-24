package com.example.java_sticker.gGoalInput;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.java_sticker.R;
import com.example.java_sticker.group.GroupDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Third extends Fragment  {
    private View view;

    String uid;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference("GroupDialog");
    DatabaseReference categoryReference = firebaseDatabase.getReference("Category");
    DatabaseReference profile_name = firebaseDatabase.getReference();

    String name;
    //First에서 받은 정보  get
    int count = 0;



    public Third(){}
    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        assert inflater != null;
        view = inflater.inflate(R.layout.custom_g_input2, container, false);

        EditText authentication = view.findViewById(R.id.auth);
        Button preBtn = view.findViewById(R.id.preBtn);
        Button nxtBtn = view.findViewById(R.id.nxtBtn);

        gGoalInputActivity frag = ((gGoalInputActivity) this.getActivity());
        assert frag != null;

        user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        uid = user.getUid();


        preBtn.setOnClickListener(view -> {
            assert getFragmentManager() != null;
            getFragmentManager().popBackStack();
        });
        nxtBtn.setOnClickListener(view -> {
            //Bundle bundle_g = this.getArguments();
            //입력한값 형 변환
            String auth = authentication.getText().toString();

            if (auth.matches(""))
                Toast.makeText(getContext(), "인증방식을 입력해주세요.", Toast.LENGTH_SHORT).show();
            else {

                Bundle bundle_g = this.getArguments();
                assert bundle_g != null;

                String cate=bundle_g.getString("cate");
                count = bundle_g.getInt("count");
                String goal = bundle_g.getString("goal");
                int limit = bundle_g.getInt("limit");



//        //고유키와 함께 저장히기 위한 장치
                String key = databaseReference.push().getKey();
                assert key != null;
                DatabaseReference keyRef = databaseReference.child(uid).child("dialog_group").child(key);
                DatabaseReference categoryRef = categoryReference.child(cate).child(key);

                //이름 가져오는 함수
                Read_name();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //list에 추가
                        GroupDialog groupDialog = new GroupDialog(count, goal, limit, auth, key, 0, cate, 1,uid,name, uid,false, "");
                        //수,목표,제한,인증,카테고리,작성자uid,user 이름

                        Log.d("TAG", String.valueOf(groupDialog));

                        //생성된 레코드 파이어베이스 저장
                        keyRef.setValue(groupDialog);
                        //uid 정보값 push()키로 저장하기
                        keyRef.child("uid").child(uid).setValue(uid);

                        //카테고리 레코드 파이어베이스에도 저장
                        categoryRef.setValue(groupDialog);
                        categoryRef.child("uid").child(uid).setValue(uid);


                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                getActivity().finish();
                            }
                        },1000);
                    }
                },400);


            }

        });
        return view;
    }


    private void Read_name(){
        profile_name.child("user").child(uid).child("userName").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name = snapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}

