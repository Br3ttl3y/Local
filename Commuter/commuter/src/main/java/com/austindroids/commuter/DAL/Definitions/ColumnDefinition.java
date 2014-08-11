package com.austindroids.commuter.DAL.Definitions;

import android.content.ContentValues;
import android.database.Cursor;

/**r
 * Created by brett on 8/2/14.
 */
public abstract class ColumnDefinition<T> {
    private int index;
    private String name;

    public ColumnDefinition(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public abstract String getDbType();

    public abstract Class<T> getObjectType();

    public abstract T read(Cursor cursor);

    public abstract void write(T value, ContentValues contentValues);

    @Override
    public String toString() {
        return getName();
    }
}
