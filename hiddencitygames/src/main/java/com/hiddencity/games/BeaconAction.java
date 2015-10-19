package com.hiddencity.games;

import android.content.Context;

import com.hiddencity.games.adapters.BeaconEntityAdapter;
import com.hiddencity.games.adapters.PlaceEntityAdapter;
import com.hiddencity.games.db.table.BeaconEntity;
import com.hiddencity.games.db.table.PlacesEntity;
import com.hiddencity.games.rest.AnswerResponse;
import com.hiddencity.games.rest.calls.NotificationShowedCall;
import com.hiddencity.games.rest.calls.PlacesCall;
import com.hiddencity.newton.domain.BeaconEvent;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.android.AndroidLog;
import retrofit.client.Response;
import rx.functions.Action1;

/**
 * Created by arturskowronski on 15/10/15.
 */
public class BeaconAction {
    public BeaconAction(Context context) {
        this.context = context;
    }

    Context context;
    String TAG = "Newton";


    public Action1<BeaconEvent> get(){
        return new Action1<BeaconEvent>() {
            @Override
            public void call(final BeaconEvent beaconEvent) {
                final BeaconEntityAdapter beaconEntityAdapter = new BeaconEntityAdapter(context);
                final PlaceEntityAdapter placeEntityAdapter = new PlaceEntityAdapter(context);

                BeaconEntity beaconEntity = beaconEntityAdapter.findByName(beaconEvent.getContentID().getBeaconName());

                if(beaconEntity == null) {
                    Log.e(TAG, "Beacon " + beaconEvent.getContentID().getBeaconName() + " not in backend");
                    return;
                }

                PlacesEntity placesEntity = placeEntityAdapter.findByName(beaconEntity.getPlaceId());

                if(!placesEntity.isActive()) {
                    Log.e(TAG, "Beacon " + beaconEvent.getContentID().getBeaconName() + " not active");
                    return;
                }

                String contentId = beaconEntity.getContent();

                if(contentId == null) {
                    Log.e(TAG, "Beacon Content   " + beaconEvent.getContentID().getBeaconName() + " not in backend");
                    return;
                }
                Log.e(TAG, "Beacon Content GO!");
                String backendEndpoint = context.getResources().getString(R.string.backend_endpoint);

                NotificationShowedCall notif = new RestAdapter.Builder()
                        .setEndpoint(backendEndpoint)
                        .setLog(new AndroidLog(TAG))
                        .setLogLevel(RestAdapter.LogLevel.FULL)
                        .build()
                        .create(NotificationShowedCall.class);
                HiddenSharedPreferences hiddenSharedPreferences = new HiddenSharedPreferences(context);
                notif.showed(hiddenSharedPreferences.getPlayerId(), contentId, new Callback<AnswerResponse>() {
                    @Override
                    public void success(AnswerResponse answerResponse, Response response) {

                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
                HiddenNotification hiddenNotification = new HiddenNotification();
                hiddenNotification.sendBeaconNotification(context, contentId, new HiddenSharedPreferences(context).getPlayerId(), beaconEntity.getTitle());
            }
        };
    }
}
