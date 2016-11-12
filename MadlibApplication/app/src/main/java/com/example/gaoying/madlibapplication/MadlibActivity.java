package com.example.gaoying.madlibapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MadlibActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_madlib);
    }

    public void getStart(View view) {
        //Intent intent = new Intent(this, FillinWordsActivity.class);
        //startActivity(intent);
        Intent intent = new Intent();
        intent.setClass(this, FillinWordsActivity.class);
        //intent.putExtra("name","Chris");
        startActivity(intent);
        //Toast.makeText(this, " get string is "+intent.getStringExtra("name"), Toast.LENGTH_SHORT).show();
    }
}
