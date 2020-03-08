package com.test.roompractice.DAO;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.test.roompractice.model.TestModel;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface TestDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TestModel testModel);

    @Query("SELECT * FROM testdata")
    public LiveData<List<TestModel>> nameList();

    @Query("DELETE from testdata where id =:id")
    void  delete(int id);

    @Query("UPDATE testdata SET name=:newname, phone= :newphone WHERE id= :id")
    void update(String newname,String newphone,int id);
}
