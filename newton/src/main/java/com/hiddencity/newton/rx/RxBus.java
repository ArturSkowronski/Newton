package com.hiddencity.newton.rx;

import com.hiddencity.newton.domain.BeaconEvent;

import rx.Observable;
import rx.functions.Func1;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by arturskowronski on 04/09/15.
 */
public class RxBus {

    private final Subject<Object, Object> _bus = new SerializedSubject<>(PublishSubject.create());

    public void send(Object o) {
        _bus.onNext(o);
    }

    public Observable<BeaconEvent> toObserverable() {
        return _bus.map(new Func1<Object, BeaconEvent>() {

            @Override
            public BeaconEvent call(Object o) {
                return (BeaconEvent)o;
            }
        });
    }
}