package com.hiddencity.games;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class OnBoardActivity extends ActionBarActivity {

    @OnClick(R.id.joinTeam)
    public void joinTeamSubmit(View view) {
        Intent intent = new Intent(OnBoardActivity.this, PlayerScreen.class);
        OnBoardActivity.this.startActivity(intent);
    }

    @OnClick(R.id.registerTeam)
    public void registerTeamSubmit(View view) {
        Intent intent = new Intent(OnBoardActivity.this, GameMasterScreen.class);
        OnBoardActivity.this.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_board);
        ButterKnife.bind(this);
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
}
