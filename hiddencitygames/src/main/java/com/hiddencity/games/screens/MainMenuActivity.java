package com.hiddencity.games.screens;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringListener;
import com.facebook.rebound.SpringSystem;
import com.hiddencity.games.R;

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
        Intent intent = new Intent(MainMenuActivity.this, PlayerScreen.class);
        MainMenuActivity.this.startActivity(intent);
    }

    @Bind(R.id.imageView)
    ImageView conradLogo;

    @Bind(R.id.buttons)
    LinearLayout buttonPanel;

    @OnClick(R.id.registerTeam)
    public void registerTeamSubmit(View view) {
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
                    buttonPanel.setVisibility(View.VISIBLE);
                    if (mMovedUp) {
                        mSpring.setEndValue(mOrigY);
                    } else {
                        mOrigY = conradLogo.getY();

                        mSpring.setEndValue(mOrigY - 50f);
                    }

                    mMovedUp = !mMovedUp;

                }
            }, 2000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_board);
        ButterKnife.bind(this);
        mSpringSystem = SpringSystem.create();
        mSpring = mSpringSystem.createSpring();
        mSpring.addListener(this);
        SpringConfig config = new SpringConfig(TENSION, DAMPER);
        mSpring.setEndValue(700f);
        mSpring.setSpringConfig(config);

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
