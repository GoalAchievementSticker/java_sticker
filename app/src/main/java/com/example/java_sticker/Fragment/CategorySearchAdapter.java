package com.example.java_sticker.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.java_sticker.Group_main;
import com.example.java_sticker.R;
import com.example.java_sticker.group.GroupDialog;

import java.util.ArrayList;

public class CategorySearchAdapter extends RecyclerView.Adapter<CategorySearchAdapter.ViewHolder>{

    private ArrayList<GroupDialog> mDataset;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tittle, person_count, goal_count;
        CardView cardView;

        public ViewHolder(View view){
            super(view);
            tittle = (TextView) view.findViewById(R.id.fh_tittle);
            person_count = (TextView) view.findViewById(R.id.personal_count_view);
            goal_count = (TextView) view.findViewById(R.id.goal_count_view);
            cardView = (CardView) view.findViewById(R.id.custom_fraghome_cardView);
        }
    }

    public CategorySearchAdapter(ArrayList<GroupDialog> mDataset){
        this.mDataset = mDataset;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_fraghome, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tittle.setText(mDataset.get(position).getgTittle());//목표명
        holder.person_count.setText(mDataset.get(position).getLimit_count() +"/"+ mDataset.get(position).getLimit()); //참가인원 수
        holder.goal_count.setText(mDataset.get(position).getgCount() +"개"); //스티커 개수

        holder.cardView.setOnClickListener(view -> {
            //클릭시 프래그먼트로 데이터 보내기
            Bundle bundle = new Bundle();

            bundle.putString("goal",mDataset.get(position).getgTittle()); //목표제목
            bundle.putInt("limit",mDataset.get(position).getLimit());//제한인원
            bundle.putInt("limit_count", mDataset.get(position).getLimit_count()); //참가한 인원
            bundle.putInt("count",mDataset.get(position).getgCount());//총 도장수
            bundle.putString("auth",mDataset.get(position).getAuth());//인증방식
            bundle.putString("cate",mDataset.get(position).getCate()); //카테고리
            bundle.putString("key", mDataset.get(position).getKey()); //리사이클러뷰 고유키

            AppCompatActivity activity = (AppCompatActivity) view.getContext();
            Fragment DetailFragment = new DetailFragment();
            DetailFragment.setArguments(bundle);
            FragmentManager fragmentManager = ((Group_main)view.getContext()).getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.group_layout,DetailFragment).addToBackStack(null).commit();
            //activity.getFragmentManager().beginTransaction().replace(R.id.group_layout, DetailFragment).addToBackStack(null).commit();

            // ((Group_main) view.getContext()).getFragmentManager().beginTransaction().replace(R.id.group_layout, DetailFragment).commit();

        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


}