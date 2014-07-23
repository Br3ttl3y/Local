package com.austindroids.commuter.widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by markrebhan on 7/15/14.
 */
public class ButtonMedium extends Button {

    public ButtonMedium(Context context) {
        super(context);
        setFont();
    }

    public ButtonMedium(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public ButtonMedium(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFont();
    }

    private void setFont() {
        Typeface typeface = TypefaceCache.getTypeface(getContext(), "fonts/Roboto-Medium.ttf");
        setTypeface(typeface);
    }
}
