package com.example.java_sticker.gGoalInput;

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


public class First extends Fragment {

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
    Fragment Second = new Second();
    TabLayout.Tab tab;

    //viewpager
    //private ViewPager viewPager;
   //private View vp;


    //viewpager fragment끼리 데이터 패스
   //SharedPreferences sharedpreferences;
    //public static final String MyPREFERENCES = "MyPrefs" ;

    public First() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    //class의 객체 생성
    //=인수 없는 생성자 호출
    public static First newInstance() {
        return new First();
    }


    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        assert inflater != null;
        view = inflater.inflate(R.layout.custom_g_input1, container, false);
        // LayoutInflater layoutInflater = (LayoutInflater) requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //vp = getLayoutInflater().inflate(R.layout.activity_custom_g_input, null);

        gGoalInputActivity frag = (gGoalInputActivity) this.getActivity();
        assert frag != null;
       // viewPager = frag.findViewById(R.id.input_viewPager);


        EditText goal = view.findViewById(R.id.sticker_goal);
        EditText count = view.findViewById(R.id.sticker_count);
        EditText limit = view.findViewById(R.id.limitNOP);
        Button noBtn = view.findViewById(R.id.noBtn);
        Button nxtBtn = view.findViewById(R.id.nxtBtn);



        //sharedpreferences =  getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        noBtn.setOnClickListener(view -> {
            assert getFragmentManager() != null;
            getFragmentManager().popBackStack();
        });

        nxtBtn.setOnClickListener(view -> {

            String _goal = goal.getText().toString();
            String _count = count.getText().toString();
            String _limit = limit.getText().toString();

            if (_goal.matches("") || _count.matches("") || _limit.matches(""))
                Toast.makeText(getContext(), "빈 입력칸이 있습니다.", Toast.LENGTH_SHORT).show();

            else {
                //입력한값 형 변환
                int vi = Integer.parseInt(_count);
                int l = Integer.parseInt(_limit);
                //int g = Integer.parseInt(_goal);

//                SharedPreferences.Editor editor = sharedpreferences.edit();
//                editor.putString("count", String.valueOf(vi));
//                editor.putString("limit", String.valueOf(l));
//                editor.putString("goal", _goal);
//                editor.commit();

                bundle.putInt("count", vi);
                bundle.putInt("limit", l);
                bundle.putString("goal", _goal);
                Second.setArguments(bundle);


//                Bundle first = new Bundle();
//                first.putInt("count", vi);
//                first.putInt("count", vi);
//                first.putInt("limit", l);
//                first.putString("goal", _goal);
//
//                getParentFragmentManager().setFragmentResult("firstKey", first);
                //viewPager.setCurrentItem(getItem(), true);


                assert getFragmentManager() != null;
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.input_framelayout, Second);
                //프래그먼트 트랜잭션을 백스택에 push
                transaction.addToBackStack(null);
                //프래그먼트 상태전환 최적화
                transaction.setReorderingAllowed(true);
                transaction.commit();

            }


        });
        return view;
    }

//    //private int getItem() {
//        return viewPager.getCurrentItem() + 1;
//    }
}
