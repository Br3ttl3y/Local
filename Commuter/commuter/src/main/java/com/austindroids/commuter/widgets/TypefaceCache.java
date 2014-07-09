package com.austindroids.commuter.widgets;

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;
import java.util.Hashtable;

/**
 * Typefaces are not cached by default, we must cache the .ttf files in order to have better
 * performance
 */
public class TypefaceCache {

    private static final Hashtable<String, Typeface> cache = new Hashtable<String, Typeface>();

    public static Typeface getTypeface(Context context, String assetPath) {
        synchronized (cache) {
            if (!cache.containsKey(assetPath)) {
                Typeface typeface = Typeface.createFromAsset(context.getAssets(), assetPath);
                cache.put(assetPath, typeface);
            }

            return cache.get(assetPath);
        }
    }
}
