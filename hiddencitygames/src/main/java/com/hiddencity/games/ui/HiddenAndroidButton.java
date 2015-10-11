package com.hiddencity.games.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

import com.hiddencity.games.R;

/**
 * Created by arturskowronski on 11/10/15.
 */
public class HiddenAndroidButton extends Button {
    public HiddenAndroidButton(Context context) {
        super(context);
        init(context);
    }

    public HiddenAndroidButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HiddenAndroidButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

        private void init(Context context){
            Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/sourcesans_pro.ttf");
            this.setTypeface(font);
            setTextColor(Color.WHITE);
            setBackground(context.getResources().getDrawable(R.drawable.hidden_button_background));
        }

}
