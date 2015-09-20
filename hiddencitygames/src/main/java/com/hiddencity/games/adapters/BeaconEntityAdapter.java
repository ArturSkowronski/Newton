package com.hiddencity.games.adapters;

import android.content.Context;

import com.hiddencity.games.db.table.BeaconEntity;

import io.realm.Realm;
import io.realm.RealmQuery;

/**
 * Created by arturskowronski on 20/09/15.
 */
public class BeaconEntityAdapter {
    public BeaconEntityAdapter(Context context) {
        this.context = context;
    }

    Context context;

    public BeaconEntity findByName(String id){
        Realm realm = Realm.getInstance(context);

        RealmQuery<BeaconEntity> query = realm.where(BeaconEntity.class).equalTo("id", id);

        BeaconEntity beaconEntity = query.findFirst();

        return beaconEntity;
    }


}
