package com.example.gaoying.friendrapplication;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

/**
 * Created by gaoying on 16/6/23.
 */
public class ProfileFragment extends Fragment {
    /*
     * Required empty constructor.
     */
    public ProfileFragment() {
        // empty
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    /*
     * This method is called by Android when the activity containing this fragment is created.
     * This is the recommended place for putting initialization code to set up the fragment.
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // read the turtle number to show, if one was passed in from TmntPicActivity/Fragment
        Log.d("ONCLICK", "profile fregment lauched");
        RatingBar rb = (RatingBar) getActivity().findViewById(R.id.friendProfRate);
        Intent intent = getActivity().getIntent();
        String name = intent.getStringExtra("name");
        String imageURL = intent.getStringExtra("imageURL");
        setActiveProfile(imageURL, name);
    }

    public void setActiveProfile(String imageURL, String name) {
        ImageView iv = (ImageView) getActivity().findViewById(R.id.friendProfImage);
        Picasso.with(getActivity())
                .load(imageURL)
                // .rotate(90)
                .resize(500, 500)
                .placeholder(R.drawable.loading)
                .into(iv);
        TextView tv = (TextView) getActivity().findViewById(R.id.friendProfName);
        tv.setText(name);
        RatingBar rb = (RatingBar) getActivity().findViewById(R.id.friendProfRate);
        rb.setRating(0);
        rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                if(fromUser){
                    Toast.makeText(getActivity(), "rate is "+rating, Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}
