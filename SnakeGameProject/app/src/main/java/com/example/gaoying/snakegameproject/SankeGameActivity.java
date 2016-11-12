package com.example.gaoying.snakegameproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SankeGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sanke_game);
    }

    public void PlayGame(View view) {
        Intent intent = new Intent(this, PlaygameActivity.class);
        startActivity(intent);
    }

    public void ExitGame(View view) {
        finish();
    }
}
