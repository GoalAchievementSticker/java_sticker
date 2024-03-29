package com.example.java_sticker.personal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.java_sticker.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import in.srain.cube.views.GridViewWithHeaderAndFooter;

public class Custom_pAdapter extends BaseAdapter {


    Context context;
    ArrayList<GridItem> items = null;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    GridItem gi;
    CircleImageView sticker_img;
    LinearLayout ll;
    View select_v;
    GridViewWithHeaderAndFooter gridView;

    public Custom_pAdapter(Context context, ArrayList<GridItem> items) {
        this.context = context;
        this.items = items;
    }


    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        Context context = viewGroup.getContext();
        GridItem gridItem = items.get(position);


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.custom_grid_p, viewGroup, false);

        sticker_img = convertView.findViewById(R.id.sticker_img_2);
        sticker_img.setImageResource(R.drawable.plus);


        if (gridItem != null) {
            // Got the download URL for 'plus.png'
            Glide.with(context)
                    .load(gridItem.getTest())
                    .apply(new RequestOptions()
                            .format(DecodeFormat.PREFER_ARGB_8888)
                            .override(Target.SIZE_ORIGINAL))
                    .into(sticker_img);
        } else {
            Toast.makeText(context, "해당 셀이 null 입니다", Toast.LENGTH_LONG).show();
        }


        View view = new View(context);
        view = (View) convertView;


        return convertView;
    }


}