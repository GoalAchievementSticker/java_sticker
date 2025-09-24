package com.example.java_sticker.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.java_sticker.R;
import com.example.java_sticker.group.GroupDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;


public class Hobby extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private final ArrayList<GroupDialog> groupDialogs = new ArrayList<>();
    private CategoryAdapter mAdapter;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference categoryReference = firebaseDatabase.getReference("Category");

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Hobby() {
        // Required empty public constructor
    }

    public static Hobby newInstance(String param1, String param2) {
        Hobby fragment = new Hobby();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ReadCategory();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_hobby, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.fraghome_hobby_ry);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_hobby);
        recyclerView.setHasFixedSize(true);
        mAdapter = new CategoryAdapter(groupDialogs);

        toolbar.setNavigationOnClickListener(view1 -> requireActivity().onBackPressed());

        RecyclerView.LayoutManager manager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mAdapter);

        ReadCategory();
        return view;
    }


    private void ReadCategory(){
        categoryReference.child("취미").addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                groupDialogs.clear();
                //Log.d("TAG", String.valueOf(snapshot));
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String key = dataSnapshot.getKey();
                    GroupDialog read_g = dataSnapshot.getValue(GroupDialog.class);
                    assert read_g != null;
                    read_g.key = key;
                    //Log.d("TAG", read_g.getgTittle());
                    //Log.d("TAG", String.valueOf(read_g.getgCount()));

                    groupDialogs.add(read_g);

                }
                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getContext(), "불러오기 실패", Toast.LENGTH_SHORT).show();
            }
        });

    }
}