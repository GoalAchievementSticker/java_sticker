package com.example.java_sticker.gGoalInput;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.android.car.ui.toolbar.TabLayout;
import com.example.java_sticker.R;
import com.example.java_sticker.group.g_GridItem;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

public class Second extends Fragment {

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
    private View view;
    Bundle bundle = new Bundle();
    TabLayout.Tab tab;




    @Override
    public void onResume() {
        super.onResume();
    }



    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        assert inflater != null;
        view = inflater.inflate(R.layout.custom_g_input1, container, false);


        EditText goal = view.findViewById(R.id.sticker_goal);
        EditText count = view.findViewById(R.id.sticker_count);
        EditText limit = view.findViewById(R.id.limitNOP);
        Button noBtn = view.findViewById(R.id.noBtn);
        Button nxtBtn = view.findViewById(R.id.nxtBtn);




        noBtn.setOnClickListener(view -> {
            assert getFragmentManager() != null;
            getFragmentManager().popBackStack();
        });

        nxtBtn.setOnClickListener(view -> {
            Bundle bundle_g = this.getArguments();
            String cate=bundle_g.getString("cate");

            String _goal = goal.getText().toString();
            String _count = count.getText().toString();
            String _limit = limit.getText().toString();


            if (_goal.matches("") || _count.matches("") || _limit.matches(""))
                Toast.makeText(getContext(), "빈 입력칸이 있습니다.", Toast.LENGTH_SHORT).show();

            else {
                //입력한값 형 변환
                int vi = Integer.parseInt(_count);
                int l = Integer.parseInt(_limit);


                Third third=new Third();

                bundle.putInt("count", vi);
                bundle.putInt("limit", l);
                bundle.putString("goal", _goal);
                bundle.putString("cate",cate);
                third.setArguments(bundle);


                assert getFragmentManager() != null;
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.input_framelayout, third);
                //프래그먼트 트랜잭션을 백스택에 push
                transaction.addToBackStack(null);
                //프래그먼트 상태전환 최적화
                transaction.setReorderingAllowed(true);
                transaction.commit();

            }


        });
        return view;
    }
}
