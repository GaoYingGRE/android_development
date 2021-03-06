package com.example.gaoying.friendrapplication;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentContainer;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by gaoying on 16/6/22.
 */
public class ViewUserActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewuser);

    setLayoutHelper();
        Log.d("NEW CREATE", "view user activity on create");

    }

    @Override
    public void onConfigurationChanged(Configuration config) {
        Log.d("NEW CREATE", "view user activity config changed");

        setContentView(R.layout.activity_viewuser);

        setLayoutHelper();

        super.onConfigurationChanged(config);

    }
    private void setLayoutHelper(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ViewUserFragment fg = new ViewUserFragment();
        ProfileFragment pg = new ProfileFragment();

        fragmentTransaction.add(R.id.leftCont, fg, "fg");
        fragmentTransaction.add(R.id.rightCont, pg, "pg");
        if(getResources().getConfiguration().orientation==Configuration.ORIENTATION_PORTRAIT){
            fragmentTransaction.show(fg);
            fragmentTransaction.hide(pg);
            Log.d("NEW CREATE", "port mode loaded");
        }else{
            fragmentTransaction.show(fg);
            fragmentTransaction.show(pg);
            Log.d("NEW CREATE", "land mode loaded");
        }
        fragmentTransaction.commit();
    }

}
