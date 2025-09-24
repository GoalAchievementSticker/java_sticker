package com.example.java_sticker.Fragment;

import android.content.Intent;
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
import com.example.java_sticker.group.close_add_goal;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class CategorySearchAdapter extends RecyclerView.Adapter<CategorySearchAdapter.ViewHolder>{

    private ArrayList<GroupDialog> mDataset;
    Intent intent_close;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String uid = user.getUid();

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

        final GroupDialog item = mDataset.get(position);

        holder.tittle.setText(mDataset.get(position).getgTittle());//목표명
        holder.person_count.setText(mDataset.get(position).getLimit_count() +"/"+ mDataset.get(position).getLimit()); //참가인원 수
        holder.goal_count.setText(mDataset.get(position).getgCount() +"개"); //스티커 개수

        holder.cardView.setOnClickListener(view -> {

            if(mDataset.get(position).getW_uid().equals(uid)){
                intent_close = new Intent(view.getContext(), close_add_goal.class);
                intent_close.putExtra("tittle",item.getgTittle()); //도장판 제목
                intent_close.putExtra("key", item.getKey()); //리사이클러뷰 고유키
                intent_close.putExtra("count", item.getgCount()); //도장판 총 도장갯수
                intent_close.putExtra("limit", item.getLimit()); //도장판 인원 제한수
                intent_close.putExtra("limit_count", item.getLimit_count()); //도장판 참가한 수
                intent_close.putExtra("auth",item.getAuth()); //도장판 인증방식
                intent_close.putExtra("cate",item.getCate()); //도장판 카테고리
                intent_close.putExtra("w_uid", item.getW_uid()); //도장판 작성자
                view.getContext().startActivity(intent_close);

            }else{
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
            }


        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


}
