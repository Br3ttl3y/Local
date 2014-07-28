package com.austindroids.commuter.DAL;

/**
 * Created by Brett on 7/27/2014.
 */
public class DbQueries {
    //Routes
    public static String CreateRouteTable(){
      return "CREATE TABLE routes" +
                "(" +
                "id INTEGER PRIMARY KEY," +
                "name TEXT" +
                ")";
    }

    public static String GetRouteById(int id)
    {
        return "SELECT * FROM routes" +
                "WHERE id =" + String.valueOf(id);
    }

    public static String GetAllRoutes(){
        return "SELECT * FROM routes";
    }

    //Database Schema
    public static String DropAllTables(){
        return "PRAGMA writable_schema = 1" +
                "DELETE FROM sqlite_master where type = 'table'" +
                "PRAGMA writable_schema = 0";

    }

    public static String DropTableIfExists(String table){
        return "DROP TABLE IF EXISTS" + table;
    }
}
