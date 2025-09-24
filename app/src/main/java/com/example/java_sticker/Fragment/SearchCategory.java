package com.example.java_sticker.Fragment;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
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
import java.util.Locale;



public class SearchCategory extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ArrayList<GroupDialog> groupDialogs;
    private ArrayList<GroupDialog> filterList;
    private RecyclerView recyclerView;
    private CategorySearchAdapter mAdapter;
    private SearchView searchView;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference categoryReference = firebaseDatabase.getReference("Category");


    private String mParam1;
    private String mParam2;

    public SearchCategory() {
        // Required empty public constructor
    }


    public static SearchCategory newInstance(String param1, String param2) {
        SearchCategory fragment = new SearchCategory();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ReadCategory();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search_category, container, false);

        //기존, 필터리스트 선언
        groupDialogs = new ArrayList<>();
        filterList = new ArrayList<>();

        //변수 연결
        recyclerView = (RecyclerView) view.findViewById(R.id.category_search_view_Recyclerview);
        SearchManager searchManager = (SearchManager)  getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) view.findViewById(R.id.search_view);
        recyclerView.setHasFixedSize(true);
        mAdapter = new CategorySearchAdapter(groupDialogs);



        RecyclerView.LayoutManager manager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mAdapter);


        //검색화면에 바로 커서 적용
        searchView.onActionViewExpanded();

        if(searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(this.getActivity().getComponentName()));
            searchView.setQueryHint(getString(R.string.search_hint));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    ReadCategorySearch(query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    ReadCategorySearch(newText);
                    return true;
                }
            });

        }

       // ReadCategory();
        // Inflate the layout for this fragment
        return view;
    }

    private void ReadCategorySearch(String keyword){
        categoryReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                groupDialogs.clear();
                for (DataSnapshot dataSnapshot : snapshot.child("공부").getChildren()) {
                    String key = dataSnapshot.getKey();
                    GroupDialog read_g = dataSnapshot.getValue(GroupDialog.class);
                    assert read_g != null;
                    read_g.key = key;
                    if(read_g.getgTittle().contains(keyword)){
                        groupDialogs.add(read_g);
                    }
                }
                for (DataSnapshot dataSnapshot2 : snapshot.child("운동").getChildren()) {
                    String key = dataSnapshot2.getKey();
                    GroupDialog read_g2 = dataSnapshot2.getValue(GroupDialog.class);
                    assert read_g2 != null;
                    read_g2.key = key;
                    if(read_g2.getgTittle().contains(keyword)){
                        groupDialogs.add(read_g2);
                    }
                }
                for (DataSnapshot dataSnapshot3 : snapshot.child("루틴").getChildren()) {
                    String key = dataSnapshot3.getKey();
                    GroupDialog read_g3 = dataSnapshot3.getValue(GroupDialog.class);
                    assert read_g3 != null;
                    read_g3.key = key;
                    if(read_g3.getgTittle().contains(keyword)){
                        groupDialogs.add(read_g3);
                    }
                }
                for (DataSnapshot dataSnapshot4 : snapshot.child("취미").getChildren()) {
                    String key = dataSnapshot4.getKey();
                    GroupDialog read_g4 = dataSnapshot4.getValue(GroupDialog.class);
                    assert read_g4 != null;
                    read_g4.key = key;
                    if(read_g4.getgTittle().contains(keyword)){
                        groupDialogs.add(read_g4);
                    }
                }
                Log.d("TAG", String.valueOf(groupDialogs));
                mAdapter.notifyDataSetChanged();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getContext(), "불러오기 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }




}