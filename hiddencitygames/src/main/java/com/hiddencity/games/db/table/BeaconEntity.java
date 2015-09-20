package com.hiddencity.games.db.table;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by arturskowronski on 20/09/15.
 */
public class BeaconEntity extends RealmObject {
    @PrimaryKey
    private String          name;
    private int             age;

    @Ignore
    private int             sessionId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
