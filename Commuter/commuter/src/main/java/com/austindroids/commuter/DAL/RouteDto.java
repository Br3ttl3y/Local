package com.austindroids.commuter.DAL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Brett on 7/27/2014.
 */
public class RouteDto extends DbConnection{

    private static final int DATABASE_VERSION = 1;

    //TODO Add more real table attributes
    private static final String TABLE_NAME = "routes",
                                PK_ID = "id",
                                ATTR_NAME = "name";

    public RouteDto(Context context){
        super(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DbQueries.CreateRouteTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DbQueries.DropTableIfExists(TABLE_NAME));
        onCreate(db);
    }

    public void createRoute(Map<String, String> route){
        ContentValues values = new ContentValues();
        for(Map.Entry <String, String> kvp : route.entrySet())
        {
            values.put(kvp.getKey(), kvp.getValue());
        }

        DbConnection
                .GetDb()
                .insert(TABLE_NAME, null, values);
        DbConnection
                .GetDb()
                .close();
    }

    public Route getRoute(int id){
        Cursor results = DbConnection
                         .GetDb()
                         .rawQuery(DbQueries.GetRouteById(id), null);
        if(results == null ) return null;

        results.moveToFirst();

        Route route = new Route();
        route.id = results.getInt(0);
        route.name = results.getString(1);
        return route;
    }

    public List<String> getAllRoutes(){
        Cursor results = DbConnection
                         .GetDb()
                         .rawQuery(DbQueries.GetAllRoutes(), null);
        if(results == null) return null;

        List<String> routes = new ArrayList<String>();
        while(results.isAfterLast() == false)
        {
            routes.add(results.getString(1));
        }
        return routes;
    }
}

 class Route {
    public int id;
    public String name;

    public Route()
    {
    }
}
