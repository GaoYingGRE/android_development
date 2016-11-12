package com.example.gaoying.madlibapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by gaoying on 16/6/20.
 */
public class DisplaystoryActivity extends Activity {

    private ArrayList<String> wordAdded;
    private ArrayList<String> story;
    private ArrayList<String> compstory;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displaystory);
        Intent intent = getIntent();
        wordAdded = intent.getStringArrayListExtra("wordadded");
        story = intent.getStringArrayListExtra("story");
        compstory = new ArrayList<>();
        if (getIntent().getStringArrayListExtra("wordadded") != null & getIntent().getStringArrayListExtra("story")!=null) {
            Toast.makeText(this,"Lauched",Toast.LENGTH_SHORT);
            DisplayStory();
        }

    }

    private void DisplayStory() {
        for(int i=0; i<wordAdded.size(); i++){
            compstory.add(story.get(i));
            compstory.add(wordAdded.get(i));
        }
        compstory.add(story.get(wordAdded.size()));

        StringBuilder sb = new StringBuilder();
        for(String str : compstory){
            sb.append(str).append(""); //separating contents using space
        }

        String DispStory = sb.toString();
        TextView tv = (TextView) findViewById(R.id.madstorydisp);
        tv.setText(DispStory);
        Log.d("GAOYING", DispStory);
    }

    public void MakeNewStory(View view) {
        Intent intent = new Intent(this, FillinWordsActivity.class);
        startActivity(intent);
    }
}
