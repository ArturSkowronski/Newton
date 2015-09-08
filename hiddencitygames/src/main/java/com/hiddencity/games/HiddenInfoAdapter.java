package com.hiddencity.games;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by arturskowronski on 06/09/15.
 */
public class HiddenInfoAdapter implements GoogleMap.InfoWindowAdapter {

    public HiddenInfoAdapter(Activity context) {
        this.context = context;
    }

    Activity context;

    @Override
    public View getInfoWindow(Marker marker) {
        LinearLayout view = new LinearLayout(context);
        view.setBackgroundColor(Color.WHITE);
        LinearLayout.LayoutParams LLParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(LLParams);
        View child = context.getLayoutInflater().inflate(R.layout.marker, null);
        TextView title = (TextView) child.findViewById(R.id.label);
        TextView content = (TextView) child.findViewById(R.id.content);
        ImageView icon = (ImageView) child.findViewById(R.id.icon);
        title.setText(marker.getTitle());
        content.setText(marker.getSnippet());
        return view;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
