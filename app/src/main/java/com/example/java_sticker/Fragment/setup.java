package com.example.java_sticker.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.example.java_sticker.Group_main;
import com.example.java_sticker.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;


public class setup extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Button setup_img_ch_button;
    Button setup_name_ch_button;
    Toolbar setup_toolbar;

    //FB
    String uid;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference profile_databaseReference = firebaseDatabase.getReference();

    DatabaseReference ch_img;
    DatabaseReference ch_name;

    Group_main group_main;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public setup() {
        // Required empty public constructor
    }


    public static setup newInstance(String param1, String param2) {
        setup fragment = new setup();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        group_main = (Group_main) getActivity();
    }

    @Override
    public void onDetach(){
        super.onDetach();
        group_main = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setup, container, false);

        setup_toolbar = (Toolbar) view.findViewById(R.id.setup_toolbar);
        setup_img_ch_button = (Button) view.findViewById(R.id.setup_img_ch_button);
        setup_name_ch_button = (Button) view.findViewById(R.id.setup_name_ch_button);

        setup_toolbar.setNavigationOnClickListener(view1 -> requireActivity().onBackPressed());

        //파이어베이스 로그인 유저 가져오기기
        user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        uid = user.getUid();

        //프로필 이미지 변경
        setup_img_ch_button.setOnClickListener(view12 -> group_main.onFragmentChange(6));
        //이름 변경
        setup_name_ch_button.setOnClickListener(view12 -> group_main.onFragmentChange(8));


        return view;
    }
}