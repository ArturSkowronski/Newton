package com.hiddencity.games.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by arturskowronski on 11/10/15.
 */
public class HiddenTextView extends TextView{
    public HiddenTextView(Context context) {
        super(context);
        init(context);
    }

    public HiddenTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HiddenTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/sourcesans_light.ttf");
        this.setTypeface(font);
        setTextColor(Color.WHITE);
    }
}
