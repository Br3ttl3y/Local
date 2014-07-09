package com.austindroids.commuter.widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.HashMap;

public class TextViewMedium extends TextView{



    public TextViewMedium(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFont();
    }

    public TextViewMedium(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public TextViewMedium(Context context) {
        super(context);
        setFont();
    }

    private void setFont() {
        Typeface typeface = TypefaceCache.getTypeface(getContext(), "fonts/Roboto-Medium.ttf");
        setTypeface(typeface);
    }
}
