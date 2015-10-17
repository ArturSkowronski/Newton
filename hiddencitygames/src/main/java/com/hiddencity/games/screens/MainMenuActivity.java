package com.hiddencity.games.screens;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringListener;
import com.facebook.rebound.SpringSystem;
import com.hiddencity.games.HiddenSharedPreferences;
import com.hiddencity.games.R;
import com.hiddencity.games.adapters.PlaceEntityAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainMenuActivity extends AppCompatActivity implements SpringListener {

    private SpringSystem mSpringSystem;
    private Spring mSpring;
    private static double TENSION = 100;
    private static double DAMPER = 20; //friction


    @OnClick(R.id.joinTeam)
    public void joinTeamSubmit(View view) {
        HiddenSharedPreferences hiddenSharedPreferences = new HiddenSharedPreferences(MainMenuActivity.this);
        if(hiddenSharedPreferences.getGCMToken().equals("")) Toast.makeText(MainMenuActivity.this, "Trwa pobieranie zawartości, spróbuj ponownie za parę sekund", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainMenuActivity.this, PlayerScreen.class);
        MainMenuActivity.this.startActivity(intent);
    }

    @Bind(R.id.imageView)
    ImageView conradLogo;

    @Bind(R.id.buttons)
    LinearLayout buttonPanel;

    @OnClick(R.id.registerTeam)
    public void registerTeamSubmit(View view) {
        HiddenSharedPreferences hiddenSharedPreferences = new HiddenSharedPreferences(MainMenuActivity.this);
        if(hiddenSharedPreferences.getGCMToken().equals("")) Toast.makeText(MainMenuActivity.this, "Trwa pobieranie zawartości, spróbuj ponownie za parę sekund", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainMenuActivity.this, GameMasterScreen.class);
        MainMenuActivity.this.startActivity(intent);
    }
    private boolean mMovedUp = false;
    private float mOrigY;

    Runnable action = new Runnable() {

        @Override
        public void run() {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mMovedUp) {
                        mSpring.setEndValue(mOrigY);
                    } else {
                        mOrigY = conradLogo.getY();

                        mSpring.setEndValue(mOrigY - 50f);
                    }

                    mMovedUp = !mMovedUp;
                    final Handler handler2 = new Handler();

                    handler2.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            buttonPanel.setVisibility(View.VISIBLE);
                        }
                    }, 1000);

                }
            }, 2500);
        }
    };

    public void logout(){
        new HiddenSharedPreferences(this).clearAllProperties();
        final PlaceEntityAdapter placeEntityAdapter = new PlaceEntityAdapter(MainMenuActivity.this);
        placeEntityAdapter.clearDB();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_board);
        ButterKnife.bind(this);
        logout();


        mSpringSystem = SpringSystem.create();
        mSpring = mSpringSystem.createSpring();
        mSpring.addListener(this);
        SpringConfig config = new SpringConfig(TENSION, DAMPER);
        mSpring.setEndValue(700f);
        mSpring.setSpringConfig(config);
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setStartOffset(500);
        fadeIn.setDuration(2000);

        AnimationSet animation = new AnimationSet(false); //change to false
        animation.addAnimation(fadeIn);
        conradLogo.setAnimation(animation);
        runOnUiThread(action);

    }


    @Override
    public void onBackPressed() {
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_on_board, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSpringUpdate(Spring spring) {
        float value = (float) spring.getCurrentValue();

       conradLogo.setY(value-200f);
    }

    @Override
    public void onSpringAtRest(Spring spring) {

    }

    @Override
    public void onSpringActivate(Spring spring) {

    }

    @Override
    public void onSpringEndStateChange(Spring spring) {

    }
}
