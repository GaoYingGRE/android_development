package com.example.gaoying.madlibapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by gaoying on 16/6/19.
 */
public class FillinWordsActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fillwords);
        wordstoAdd = new ArrayList<>();
        wordAdded = new ArrayList<>();
        story = new ArrayList<>();
        readWordsFromFiles();
    }

    private ArrayList<String> wordstoAdd;
    private ArrayList<String> wordAdded;
    private ArrayList<String> story;
    private int numLeft;

    public void addWord(View view) {
        TextView tv = (TextView) findViewById(R.id.hint);
        String newWord = tv.getText().toString();
        //Toast.makeText(this, "new word "+ newWord+" added!", Toast.LENGTH_SHORT).show();
        wordAdded.add(newWord);
        if(numLeft>1){
            numLeft--;
            tv.setText("");
            Show();
        }else{
            Intent intent = new Intent(this, DisplaystoryActivity.class);
            intent.putExtra("wordadded", wordAdded);
            intent.putExtra("story",story);
            startActivity(intent);
        }

    }

    private void readWordsFromFiles() {
        //  read the initial (immutable) list of words from resource file "madlib1_tarzan.txt"
        Scanner scan = new Scanner(getResources().openRawResource(R.raw.madlib1_tarzan));
        readWordsHelper(scan);
        numLeft=wordstoAdd.size();
        Toast.makeText(FillinWordsActivity.this,"numletf is "+numLeft,Toast.LENGTH_SHORT);
        Show();
    }

    private void readWordsHelper(Scanner scan) {
        while (scan.hasNext()) {
            scan.useDelimiter("<");
            String halfsplit = scan.next();
            String[] parts = halfsplit.split(">");
            Log.d("TAG", "halfsplit is " +halfsplit);
            if (parts.length >= 2) {
                String descp = parts[0];
                String storypiece = parts[1];
                Log.d("TAG", "length>=2 & " + "defn is " +descp);
                Log.d("TAG", "storypiece is " +storypiece);
                wordstoAdd.add(descp);
                story.add(storypiece);

            }
            else if(parts.length==1){
                String storypiece = parts[0];
                Log.d("TAG", "storypiece is " +storypiece);
                story.add(storypiece);
            }
        }
        scan.close();
    }

    private void Show(){
        TextView tv = (TextView) findViewById(R.id.numLeft);
        tv.setText(numLeft+" word(s) left");
        EditText et = (EditText) findViewById(R.id.hint);
        et.setHint(wordstoAdd.get(wordstoAdd.size()-numLeft));
        TextView tv2 = (TextView) findViewById(R.id.typehint);
        tv2.setText("please type a " + wordstoAdd.get(wordstoAdd.size()-numLeft));

    }

}
