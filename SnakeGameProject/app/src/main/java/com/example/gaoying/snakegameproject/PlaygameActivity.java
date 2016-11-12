package com.example.gaoying.snakegameproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by gaoying on 16/6/26.
 */

public class PlaygameActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);
    }

    public void turnLeft(View view) {
        SnakeView sv = (SnakeView) findViewById(R.id.mysnakeview);
        sv.turnLeft();
    }

    public void turnRight(View view) {
        SnakeView sv = (SnakeView) findViewById(R.id.mysnakeview);
        sv.turnRight();
    }
}