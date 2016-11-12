package com.example.gaoying.friendrapplication;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

/**
 * Created by gaoying on 16/6/20.
 */

public class ViewUserFragment extends Fragment {

    private static final String WEBSITE_PIC_DIRECTORY =  "http://www.martystepp.com/friendr/friends/";
    private static final String WEBSITE_LIST_DIRECTORY =  "http://www.martystepp.com/friendr/friends/list";
    private String[] friend_list;

    public ViewUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_viewuser, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadList();
        //Toast.makeText(getActivity(), "loaded in fragment", Toast.LENGTH_LONG).show();
    }

    private void loadList() {
        Ion.with(this)
                .load(WEBSITE_LIST_DIRECTORY)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        //Log.d("message",result);
                        friend_list = result.split("\n");
                        loadImage();
                    }
                });
        //Log.d("message",friend_list[0]);
        //Toast.makeText(getActivity(),friend_list[0], Toast.LENGTH_LONG);
    }


    public void loadImage() {
        LinearLayout ly = (LinearLayout) getActivity().findViewById(R.id.imageContainer);
        ly.removeAllViews();
        TextView tv = new TextView(getActivity());
        tv.setText("Click on a user to see more details");
        tv.setGravity(Gravity.CENTER_HORIZONTAL);
        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.WRAP_CONTENT));
        ly.addView(tv);
        for(int i=0; i<friend_list.length;i=i+2){
            LinearLayout ly2 = new LinearLayout(getActivity());
            ly2.setOrientation(LinearLayout.HORIZONTAL);
            LoadImageHelper(ly2, i);
            if(i<friend_list.length){
                LoadImageHelper(ly2, i+1);
                ly.addView(ly2);
            }
        }
    }

    private void LoadImageHelper(LinearLayout ly, final int i) {
        ImageButton ib = new ImageButton(getActivity());
        TextView tv = new TextView(getActivity());
        LinearLayout ly3 = new LinearLayout(getActivity());
        ly3.setOrientation(LinearLayout.VERTICAL);
        ly3.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT));
        String imgtext = friend_list[i];
        tv.setText(imgtext);
        tv.setGravity(Gravity.CENTER_HORIZONTAL);
        Picasso.with(getActivity())
                .load(WEBSITE_PIC_DIRECTORY + friend_list[i].toLowerCase()+".jpg")
                // .rotate(90)
                .resize(500, 500)
                .placeholder(R.drawable.loading)
                .into(ib);
        Log.d("GYY","frient list is loaded and the "+i+"th is "+friend_list[i].toLowerCase());
        ly3.addView(ib);
        ly3.addView(tv);
        ly.addView(ly3);
        ib.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                FriendImageClick(i);
                //Log.d("Bug for ONCLICK","Onclick works and I clicked "+friend_list[i]);
            }
        });
    }

    private void FriendImageClick(int position) {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            // start another activity
            Intent intent = new Intent(getActivity(), ProfileActivity.class);
            intent.putExtra("imageURL", WEBSITE_PIC_DIRECTORY + friend_list[position].toLowerCase()+".jpg");
            intent.putExtra("name", friend_list[position]);
            startActivity(intent);
            //Toast.makeText(getActivity(),"Profile intent created", Toast.LENGTH_SHORT);
           // Log.d("Bug for ONCLICK","FriendImageClick works and I clicked "+friend_list[position]);
        } else {
            // update second fragment within same activity
            updateOtherFragment(position);
        }
    }

    public void updateOtherFragment(int position) {
        ProfileFragment frag = (ProfileFragment)
                getActivity().getFragmentManager().findFragmentById(R.id.rightCont);
        frag.setActiveProfile(WEBSITE_PIC_DIRECTORY + friend_list[position].toLowerCase()+".jpg", friend_list[position]);
    }
}



