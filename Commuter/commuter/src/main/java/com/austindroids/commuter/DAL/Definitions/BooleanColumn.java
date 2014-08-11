package com.austindroids.commuter.DAL.Definitions;

import android.content.ContentValues;
import android.database.Cursor;

import com.austindroids.commuter.DAL.ColumnDefinition;
import com.austindroids.commuter.DAL.DbConnection;

/**
 * Created by brett on 8/2/14.
 */
public class BooleanColumn extends ColumnDefinition<Boolean> {

    public BooleanColumn(int index, String name)
    {
        super(index, name);
    }

    @Override
    public String getDbType() {
        return "INTEGER";
    }

    @Override
    public Class<Boolean> getObjectType() {
        return Boolean.class;
    }

    @Override
    public Boolean read(Cursor cursor) {
        return DbConnection.readBoolean(super.getIndex(), cursor);
    }

    @Override
    public void write(Boolean value, ContentValues contentValues) {
        DbConnection.writeBoolean(contentValues, super.getName(), value);
    }
}
