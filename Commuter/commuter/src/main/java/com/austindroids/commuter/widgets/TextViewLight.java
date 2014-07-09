package com.austindroids.commuter.widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class TextViewLight extends TextView {

    public TextViewLight(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFont();
    }

    public TextViewLight(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public TextViewLight(Context context) {
        super(context);
        setFont();
    }

    private void setFont() {
            Typeface typeface = TypefaceCache.getTypeface(getContext(), "fonts/Roboto-Light.ttf");
            setTypeface(typeface);
    }

}
