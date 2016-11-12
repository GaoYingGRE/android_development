package com.example.gaoying.snakegameproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;

import java.util.ArrayList;

import stanford.androidlib.graphics.GCanvas;
import stanford.androidlib.graphics.GColor;
import stanford.androidlib.graphics.GLabel;
import stanford.androidlib.graphics.GSprite;
import stanford.androidlib.util.RandomGenerator;

/**
 * Created by gaoying on 16/6/26.
 */
public class SnakeView extends GCanvas{

    private static final float SNAKE_VELOCITY = 2.0f;

    // private fields
    private GSprite snake_head;
    private GSprite food_cheese;
    private GSprite tail_rec;

    private int Direction = 0;

    private GLabel scoreLabel;

    private ArrayList<GSprite> snake_bodys;
    private int score = 0;
    /*
     * Required auto-generated constructor.
     */
    public SnakeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init() {

        scoreLabel = new GLabel();

        updateScoreLabel();
        scoreLabel.setColor(GColor.BLACK);
        scoreLabel.setFontSize(60);
        scoreLabel.setRightX(getWidth() - 50);
        add(scoreLabel);

        snake_head = new GSprite(resizeBitmap(R.drawable.snakehead, .25f));
        snake_head.setCollisionMargin(5f);
        snake_head.setX(getWidth()/2);
        snake_head.setY(getHeight()/2);
        add(snake_head);

        snake_bodys = new ArrayList<>();
        tail_rec = new GSprite(resizeBitmap(R.drawable.snakebody, .25f));

        addFood();

        animate(1);

    }

    @Override
    public void onAnimateTick() {
        super.onAnimateTick();

        gotoNext();
        checkDirection();
        checkForCollisions();

        if (checkForGetFood()) {
            // once per second, add an asteroid
            score++;
            remove(food_cheese);
            updateScoreLabel();
            addTail();
            addFood();
        }


    }

    private void updateScoreLabel() {
        String scoreText = getResources().getString(R.string.score, score);
        scoreLabel.setText(scoreText);
    }

    private void addTail() {
        GSprite tail = new GSprite(resizeBitmap(R.drawable.snakebody, .25f));
        tail.setCollisionMargin(5f);
        tail.setX(tail_rec.getX());
        tail.setY(tail_rec.getY());

        add(tail);
        snake_bodys.add(tail);
        Log.d("ADD TAIL", "Now the body size is "+snake_bodys.size());
    }

    private void checkDirection() {
        if(Direction%4 ==0){
            snake_head.setBottomY(snake_head.getTopY());
        }else if(Direction%4==1){
            snake_head.setX(snake_head.getRightX());
        }else if(Direction%4==2){
            snake_head.setY(snake_head.getBottomY());
        }else if(Direction%4==3){
            snake_head.setRightX(snake_head.getLeftX());
        }
    }

    private void addFood(){
        food_cheese = new GSprite(resizeBitmap(R.drawable.cheese, 0.05f));
        RandomGenerator rgen = RandomGenerator.getInstance();
        food_cheese.setX(rgen.nextFloat(getWidth()-snake_head.getWidth()));
        food_cheese.setY(rgen.nextFloat(getHeight()-snake_head.getHeight()));
        while (true){
            if(checkForPlacingFood())
                break;
            else{
                food_cheese.setX(rgen.nextFloat(getWidth()-snake_head.getWidth()));
                food_cheese.setY(rgen.nextFloat(getHeight()-snake_head.getHeight()));
            }
        }
        add(food_cheese);
    }

    private boolean checkForGetFood(){
        if(food_cheese.collidesWith(snake_head)){
            return true;
        }
        return false;
    }

    private boolean checkForPlacingFood(){
        if(food_cheese.collidesWith(snake_head))
            return false;
        if(snake_bodys == null)
            return true;
        for (GSprite snake_body : snake_bodys) {
            if (food_cheese.collidesWith(snake_body)) {
                return false;
            }
        }
        return true;
    }

    /*
     * Helper method to resize a bitmap by a given scale factor.
     */
    private Bitmap resizeBitmap(int id, float scaleFactor) {
        Bitmap bmp = getBitmap(id);
        bmp = Bitmap.createScaledBitmap(bmp,
                (int) (bmp.getWidth() * scaleFactor),
                (int) (bmp.getHeight() * scaleFactor), true);
        return bmp;
    }


    public void turnLeft() {
        Direction = Direction +3;
    }

    private void gotoNext(){

        //find the last piece of snake
        if(snake_bodys.size()==0){
            tail_rec.setX(snake_head.getX());
            tail_rec.setY(snake_head.getY());
        }else{
            tail_rec.setX(snake_bodys.get(snake_bodys.size()-1).getX());
            tail_rec.setY(snake_bodys.get(snake_bodys.size()-1).getY());
        }

        if(snake_bodys.size()>1){
            for(int i=snake_bodys.size()-1; i>0; i--){
                snake_bodys.get(i).setX(snake_bodys.get(i-1).getX());
                snake_bodys.get(i).setY(snake_bodys.get(i-1).getY());
            }
            snake_bodys.get(0).setX(snake_head.getX());
            snake_bodys.get(0).setY(snake_head.getY());

        }else if (snake_bodys.size()==1){
            snake_bodys.get(0).setX(snake_head.getX());
            snake_bodys.get(0).setY(snake_head.getY());
        }
    }

    public void turnRight() {
        Direction = Direction +1;
    }

    private void checkForCollisions() {
        if (snake_head.getRightX() >= getWidth()
                || snake_head.getX() <= 0
                || snake_head.getBottomY() >= getHeight()
                || snake_head.getY() <=0) {
                gameOver();
            Log.d("CANCLE YY", "collides  outside window");

        } else {
            // check for collisions with asteroids
            for (GSprite snake_body : snake_bodys) {
                if (snake_head.collidesWith(snake_body)) {
                    gameOver();
                    Log.d("CANCLE YY", "collides snake body and head");
                    break;
                }
            }
        }
    }
    /*
     * Called when the player loses the game.
     */
    private void gameOver() {

        winLoseHelper(R.string.youlose);
    }

    /*
     * Common code used when we win or lose the game.
     */
    private void winLoseHelper(int Id) {
        animationStop();

        GLabel winLabel = new GLabel();
        winLabel.setText(Id);
        winLabel.setColor(GColor.BLACK);
        winLabel.setFontSize(60);
        winLabel.setX(getWidth() / 2 - winLabel.getWidth() / 2);
        winLabel.setY(getHeight() / 2 - winLabel.getHeight() / 2);
        add(winLabel);
    }

}
