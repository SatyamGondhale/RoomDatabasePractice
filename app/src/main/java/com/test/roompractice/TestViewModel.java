package com.test.roompractice;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.test.roompractice.model.TestModel;
import com.test.roompractice.repository.DataRepository;

import java.util.ArrayList;
import java.util.List;

public class TestViewModel extends AndroidViewModel {

    DataRepository repository;
    LiveData<List<TestModel>> entries;
    MutableLiveData<List<TestModel>> updatedEntries;
    public TestViewModel(@NonNull Application application) {
        super(application);
        repository=new DataRepository(application);
        entries=repository.getNameListRepo();
    }

    public void insertTestModelVM(TestModel model){
        repository.insertData(model);
    }

    public LiveData<List<TestModel>> getNameListVM(){
        return entries;
    }

    public void deleteEntryTestModelVM(int id){
        repository.deleteData(id);
    }

    public void updateEntryTestModelVM(int id,TestModel tm){
        repository.updateData(id, tm);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
