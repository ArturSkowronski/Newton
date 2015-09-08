package com.hiddencity.games;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hiddencity.games.gcm.Preferences;
import com.hiddencity.games.rest.Onboarding;
import com.hiddencity.games.rest.RegisterTeam;
import com.hiddencity.games.rest.TeamRegistrationRequest;
import com.hiddencity.games.rest.TeamRegistrationResponse;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class GameMasterScreen extends ActionBarActivity {
    RegisterTeam registerTeamRestService;

    @Bind(R.id.gameCode)
    TextView gameCode;

    @OnClick(R.id.joinGame)
    public void joinGameSubmit(View view) {
        Onboarding onboarding = new Onboarding(GameMasterScreen.this);
        onboarding.startOnboarding();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String backendEndpoint = getResources().getString(R.string.backend_endpoint);
        registerTeamRestService = new RestAdapter.Builder()
                .setEndpoint(backendEndpoint)
                .build().create(RegisterTeam.class);

        setContentView(R.layout.activity_game_master_screen);
        ButterKnife.bind(this);
        final HiddenSharedPreferences hiddenSharedPreferences = new HiddenSharedPreferences(this);

        String gcmToken = hiddenSharedPreferences.getGCMToken();

        final TeamRegistrationRequest teamRegistrationRequest = new TeamRegistrationRequest();
        teamRegistrationRequest.setGcm(gcmToken);

        registerTeamRestService.registerTeam(teamRegistrationRequest, new Callback<TeamRegistrationResponse>() {

            @Override
            public void success(TeamRegistrationResponse teamRegistrationResponse, Response response) {
                gameCode.setText(teamRegistrationResponse.getRegistration_code());
                hiddenSharedPreferences.setPlayerId(teamRegistrationResponse.getPlayer_id());
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(GameMasterScreen.this, "Nie udało się pobrać kodu gracza", Toast.LENGTH_SHORT);
                GameMasterScreen.this.finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game_master_screen, menu);
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
