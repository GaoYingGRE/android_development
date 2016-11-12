package com.example.gaoying.todolistapplication;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> mylist;
    private ArrayAdapter<String> adapter;
    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mylist = new ArrayList<>();
        listview = (ListView) findViewById(R.id.Mytodolist);

        if (adapter == null) {
            adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1,
                    mylist);
            listview.setAdapter(adapter);
        } else {
            // update list adapter if list was already created previously
            adapter.notifyDataSetChanged();
        }

        try{
            Scanner scan = new Scanner(openFileInput("filename.txt"));
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                mylist.add(line);
                adapter.notifyDataSetChanged();
            }
        }catch(FileNotFoundException e){
            return;
        }
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // when a list item is clicked, delete the clicked item, and update the todolist
                mylist.remove(position);
                adapter.notifyDataSetChanged();
                return true;
            }
        });
    }


    public void AddEvent(View view) {
        EditText newEvent = (EditText) findViewById(R.id.newEvent);
        String event = newEvent.getText().toString();
        mylist.add(event);
        Toast.makeText(MainActivity.this,event+" event added", Toast.LENGTH_SHORT).show();
        adapter.notifyDataSetChanged();
        newEvent.setText("");
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Toast.makeText(MainActivity.this,"Event of index0 is"+ mylist.get(0), Toast.LENGTH_SHORT).show();
        try{
            PrintStream out = new PrintStream(openFileOutput("filename.txt", MODE_PRIVATE));
            for(int i=0;i<mylist.size();i++){
                out.println(mylist.get(i));
            }
            out.close();
        } catch(FileNotFoundException e){
            return;
        }

    }
}
