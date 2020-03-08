package com.test.roompractice.database;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.test.roompractice.DAO.TestDAO;
import com.test.roompractice.R;
import com.test.roompractice.model.TestModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {TestModel.class},version = 1,exportSchema = false)
public abstract class TestRoomDatabase extends RoomDatabase {

    public abstract TestDAO testDAO();
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static volatile TestRoomDatabase INSTANCE;

    public static  TestRoomDatabase getDatabase(final Context context){
        if(INSTANCE==null){
            synchronized (TestRoomDatabase.class){
                if(INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(context,TestRoomDatabase.class, "test_data")
                            .build();

                }
            }
        }
        return INSTANCE;
    }

    private static void fillAppWithDemoData(Context context){
        TestDAO testDAO=getDatabase(context).testDAO();
        JSONArray getJsonData=loadJsonData(context);
        try {
            for (int i = 0; i < getJsonData.length(); i++) {
                JSONObject item = getJsonData.getJSONObject(i);
                testDAO.insert(new TestModel(item.getString("name"),
                        item.getString("phone"),
                        item.getString("city"),item.getString("company")));
                Log.i("RoomDatabaseCreated","TestUserEntered");
            }
        } catch (JSONException exception) {
            exception.printStackTrace();
        }
    }

    private static  JSONArray loadJsonData(Context context){
        StringBuilder builder = new StringBuilder();
        InputStream in = context.getResources().openRawResource(R.raw.testuserdata);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            JSONObject json = new JSONObject(builder.toString());
            return json.getJSONArray("testusers");

        } catch (IOException | JSONException exception) {
            exception.printStackTrace();
        }

        return null;

    }
}
