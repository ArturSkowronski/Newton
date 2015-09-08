package com.hiddencity.games;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.hiddencity.games.gcm.Preferences;
import com.hiddencity.games.rest.JoinTeam;
import com.hiddencity.games.rest.Onboarding;
import com.hiddencity.games.rest.RegisterTeam;
import com.hiddencity.games.rest.TeamJoinRequest;
import com.hiddencity.games.rest.TeamJoinResponse;
import com.hiddencity.games.rest.TeamRegistrationRequest;
import com.hiddencity.games.rest.TeamRegistrationResponse;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cyd.awesome.material.AwesomeButton;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class PlayerScreen extends ActionBarActivity {
    JoinTeam joinTeamRestEndpoint;

    @OnClick(R.id.joinGame)
    public void joinGameSubmit(View view) {
        joinGameRequest();
    }

    @Bind(R.id.joinGameInput)
    EditText joinGameInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String backendEndpoint = getResources().getString(R.string.backend_endpoint);
        joinTeamRestEndpoint = new RestAdapter.Builder()
                .setEndpoint(backendEndpoint)
                .build()
                .create(JoinTeam.class);
        setContentView(R.layout.activity_player_screen);
        ButterKnife.bind(this);
    }

    private void joinGameRequest() {

        TeamJoinRequest teamJoinRequest = creteTeamJoinRequest();
        Callback<TeamJoinResponse> callback = TeamJoinResponse.callbackResponse(this);

        joinTeamRestEndpoint.joinTeam(teamJoinRequest, callback);
    }

    private TeamJoinRequest creteTeamJoinRequest() {
        final HiddenSharedPreferences hiddenSharedPreferences = new HiddenSharedPreferences(PlayerScreen.this);
        String gcm = hiddenSharedPreferences.getGCMToken();

        TeamJoinRequest teamJoinRequest = new TeamJoinRequest();
        teamJoinRequest.setGcm(gcm);

        String code = joinGameInput.getText().toString();
        teamJoinRequest.setCode(code);
        return teamJoinRequest;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_player_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
