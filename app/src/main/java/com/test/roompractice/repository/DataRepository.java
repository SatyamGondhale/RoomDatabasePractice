package com.test.roompractice.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.test.roompractice.DAO.TestDAO;
import com.test.roompractice.database.TestRoomDatabase;
import com.test.roompractice.model.TestModel;

import java.util.ArrayList;
import java.util.List;

public class DataRepository {

    private TestDAO testDAO;
   private LiveData<List<TestModel>> entries;

    public DataRepository(Application application){
        TestRoomDatabase testRoomDatabase=TestRoomDatabase.getDatabase(application);
        testDAO=testRoomDatabase.testDAO();
        entries=testDAO.nameList();
    }

    public void insertData(final TestModel model){
        TestRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                testDAO.insert(model);
            }
        });
    }

    public LiveData<List<TestModel>> getNameListRepo(){
        return entries;
    }

    public void deleteData(final int id){
        TestRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                testDAO.delete(id);
            }
        });

    }

    public void updateData(final  int id,final  TestModel vm){
        TestRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                    testDAO.update(vm.getName(),vm.getPhone(),id);
            }
        });
    }
}
