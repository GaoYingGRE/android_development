package com.example.gaoying.friendrapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class FriendrMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendr);
    }


    /*public void swipToRate(View view) {
        Intent intent = new Intent(this, SwipeActivity.class);
        startActivity(intent);
    }*/

    public void viewUsers(View view) {
        Intent intent = new Intent(this, ViewUserActivity.class);
        startActivity(intent);
    }
}
