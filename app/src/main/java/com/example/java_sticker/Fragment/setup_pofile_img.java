package com.example.java_sticker.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.java_sticker.Group_main;
import com.example.java_sticker.R;
import com.example.java_sticker.UserRegister;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;


public class setup_pofile_img extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Toolbar setup_profile_img_toolbar;
    CircleImageView circleImageView_setup;
    ImageButton profile_green_or;
    ImageButton profile_green_slow;
    ImageButton profile_green_an;
    ImageButton profile_blue_or;
    ImageButton profile_grap;
    Button button_profile_img_ok;

    //FB
    String uid;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference profile_databaseReference = firebaseDatabase.getReference();
    DatabaseReference databaseReference = firebaseDatabase.getReference("GroupDialog");
    DatabaseReference img_path_url_ch;

    FirebaseStorage mstorage;
    private StorageReference storageReference;

    String img_path;

    String profile_green_or_url;
    String profile_green_slow_url;
    String profile_green_an_url;
    String profile_blue_or_url;
    String profile_grap_url;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public setup_pofile_img() {
        // Required empty public constructor
    }


    public static setup_pofile_img newInstance(String param1, String param2) {
        setup_pofile_img fragment = new setup_pofile_img();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        View view = inflater.inflate(R.layout.fragment_setup_pofile_img, container, false);

        setup_profile_img_toolbar = (Toolbar) view.findViewById(R.id.setup_profile_img_toolbar);
        circleImageView_setup = (CircleImageView) view.findViewById(R.id.circleImageView_setup);
        profile_blue_or = (ImageButton) view.findViewById(R.id.profile_blue_or);
        profile_grap = (ImageButton) view.findViewById(R.id.profile_grap);
        profile_green_or = (ImageButton) view.findViewById(R.id.profile_green_or);
        profile_green_an = (ImageButton) view.findViewById(R.id.profile_green_an);
        profile_green_slow = (ImageButton) view.findViewById(R.id.profile_green_slow);

        button_profile_img_ok = (Button) view.findViewById(R.id.button_profile_img_ok);

        setup_profile_img_toolbar.setNavigationOnClickListener(view1 -> getActivity().onBackPressed());
        //파이어베이스 로그인 유저 가져오기기
        user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        uid = user.getUid();

        img_path = null;
        mstorage = FirebaseStorage.getInstance();
        storageReference = mstorage.getReference();

        //기존 프로필 이미지 설정
        img_setup();

        setProfile_img_url();

        //각 버튼 누르면 이미지 변경(프로필 이미지 확인)
        new Handler().postDelayed(this::profile_img_click,300);

        //확인 버튼 클릭시 프로필 이미지 바꾸기
        button_profile_img_ok.setOnClickListener(view12 -> {
            img_path_url_ch = profile_databaseReference.child("user").child(uid).child("profileImageUrl");
            img_path_url_ch.setValue(img_path);
            startActivity(new Intent(getContext(), Group_main.class));
            Toast.makeText(getContext(), "프로필 사진을 변경했습니다", Toast.LENGTH_SHORT).show();

        });

        return view;
    }

    private void img_setup(){
        //현재 프로필 이미지 가져오기
        profile_databaseReference.child("user").child(uid).child("profileImageUrl").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String image = snapshot.getValue(String.class);
                Glide.with(requireActivity())
                        .load(image)
                        .into(circleImageView_setup);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setProfile_img_url(){
        storageReference.child("profile_img").child("profile_green_or.png").getDownloadUrl()
                .addOnSuccessListener(uri -> {
                    String path = uri.toString();
                    profile_green_or_url = path;
                }).addOnFailureListener(Throwable::printStackTrace);
        storageReference.child("profile_img").child("profile_green_an.png").getDownloadUrl()
                .addOnSuccessListener(uri -> {
                    String path2 = uri.toString();
                    profile_green_an_url = path2;

                }).addOnFailureListener(Throwable::printStackTrace);
        storageReference.child("profile_img").child("profile_green_slow.png").getDownloadUrl()
                .addOnSuccessListener(uri -> {
                    String path3 = uri.toString();
                    profile_green_slow_url = path3;

                }).addOnFailureListener(Throwable::printStackTrace);
        storageReference.child("profile_img").child("profile_grap.png").getDownloadUrl()
                .addOnSuccessListener(uri -> {
                    String path4 = uri.toString();
                    profile_grap_url = path4;

                }).addOnFailureListener(Throwable::printStackTrace);
        storageReference.child("profile_img").child("profile_blue_or.png").getDownloadUrl()
                .addOnSuccessListener(uri -> {
                    String path5 = uri.toString();
                    profile_blue_or_url = path5;

                }).addOnFailureListener(Throwable::printStackTrace);

    }



    private void profile_img_click(){

        profile_green_or.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img_path = profile_green_or_url;
                Glide.with(view).load(profile_green_or_url).into(circleImageView_setup);
                Log.d("TAG", img_path);
            }
        });

        profile_green_an.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img_path = profile_green_an_url;
                Glide.with(view).load(profile_green_an_url).into(circleImageView_setup);
                Log.d("TAG", img_path);
            }
        });

        profile_green_slow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img_path = profile_green_slow_url;
                Glide.with(view).load(profile_green_slow_url).into(circleImageView_setup);
                Log.d("TAG",
                        img_path);
            }
        });

        profile_blue_or.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img_path = profile_blue_or_url;
                Glide.with(view).load(profile_blue_or_url).into(circleImageView_setup);
                Log.d("TAG", img_path);
            }
        });

        profile_grap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img_path = profile_grap_url;
                Glide.with(view).load(profile_grap_url).into(circleImageView_setup);
                Log.d("TAG", img_path);
            }
        });

    }
}