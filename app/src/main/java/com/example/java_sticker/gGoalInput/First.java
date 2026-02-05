package com.example.java_sticker.gGoalInput;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.java_sticker.R;
import com.example.java_sticker.group.GroupDialog;
import com.example.java_sticker.group.g_GridItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;


public class First extends Fragment implements View.OnClickListener {

    ToggleButton exercise;
    ToggleButton study;
    ToggleButton hobby;
    ToggleButton routine;
    Bundle bundle = new Bundle();
    Second second = new Second();

    String uid;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference("GroupDialog");
    DatabaseReference categoryReference = firebaseDatabase.getReference("Category");


    private View view;


    String cate_;

    public First() {
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        assert inflater != null;
        view = inflater.inflate(R.layout.custom_g_input3, container, false);



        exercise = view.findViewById(R.id.exercise);
        study = view.findViewById(R.id.study);
        hobby = view.findViewById(R.id.hobby);
        routine = view.findViewById(R.id.routine);



        Button preBtn = view.findViewById(R.id.preBtn);
        Button OKBtn = view.findViewById(R.id.OKBtn);


        exercise.setOnClickListener(this);
        hobby.setOnClickListener(this);
        routine.setOnClickListener(this);
        study.setOnClickListener(this);

        //sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);


        preBtn.setOnClickListener(view -> {
                    assert getFragmentManager() != null;
                    getFragmentManager().popBackStack();
                }
        );
        OKBtn.setOnClickListener(view -> {
            if (!(exercise.isChecked() || hobby.isChecked() || routine.isChecked() || study.isChecked())) {
                Toast.makeText(getContext(), "카테고리를 선택해주세요", Toast.LENGTH_SHORT).show();
            } else {
                ToFragjoin();
            }
        });

        return view;
    }

    private void ToFragjoin() {

        assert getFragmentManager() != null;
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.input_framelayout, second);
        //프래그먼트 트랜잭션을 백스택에 push
        transaction.addToBackStack(null);
        //프래그먼트 상태전환 최적화
        transaction.setReorderingAllowed(true);
        transaction.commit();


    }



@SuppressLint("NonConstantResourceId")
@Override
public void onClick(View v) {
    //입력한값 형 변환
    String ex = exercise.getText().toString();
    String st = study.getText().toString();
    String hob = hobby.getText().toString();
    String rout = routine.getText().toString();

    int viewId = v.getId(); // v.getId()를 변수에 할당하여 가독성을 높입니다.

    if (viewId == R.id.exercise) {
        if (exercise.isChecked()) {
            exercise.setChecked(true);
            Toast.makeText(getContext(), "운동 카테고리 클릭", Toast.LENGTH_SHORT).show();
            bundle.putString("cate", ex);
            second.setArguments(bundle);

            study.setChecked(false);
            routine.setChecked(false);
            hobby.setChecked(false);
        } else {
            exercise.setChecked(false);
        }
    } else if (viewId == R.id.study) {
        if (study.isChecked()) {
            study.setChecked(true);
            Toast.makeText(getContext(), "공부 카테고리 클릭", Toast.LENGTH_SHORT).show();
            bundle.putString("cate", st);
            second.setArguments(bundle);
            routine.setChecked(false);
            hobby.setChecked(false);
            exercise.setChecked(false);
        } else {
            study.setChecked(false);
        }
    } else if (viewId == R.id.routine) {
        if (routine.isChecked()) {
            routine.setChecked(true);
            Toast.makeText(getContext(), "루틴 카테고리 클릭", Toast.LENGTH_SHORT).show();
            bundle.putString("cate", rout);
            second.setArguments(bundle);
            cate_ = rout;
            hobby.setChecked(false);
            exercise.setChecked(false);
            study.setChecked(false);
        } else {
            routine.setChecked(false);
        }
    } else if (viewId == R.id.hobby) {
        if (hobby.isChecked()) {
            hobby.setChecked(true);
            Toast.makeText(getContext(), "취미 카테고리 클릭", Toast.LENGTH_SHORT).show();
            bundle.putString("cate", hob);
            second.setArguments(bundle);
            cate_ = hob;
            exercise.setChecked(false);
            study.setChecked(false);
            routine.setChecked(false);
        } else {
            hobby.setChecked(false);
        }
    }
}

}
