package com.test.roompractice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Application;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.test.roompractice.adapter.NamesAdapter;
import com.test.roompractice.database.TestRoomDatabase;
import com.test.roompractice.model.TestModel;
import com.test.roompractice.repository.DataRepository;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LifecycleOwner {

    TestViewModel testViewModel;
    Button add;
    RecyclerView names;
    List<TestModel> nameEntries;
    NamesAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testViewModel=new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(TestViewModel.class);
        add=findViewById(R.id.addRecord);
        names=findViewById(R.id.namesList);
        names.setHasFixedSize(true);
        names.setLayoutManager(new LinearLayoutManager(MainActivity.this));
       // nameEntries=testViewModel.getNameListVM();
        adapter=new NamesAdapter(this);
        testViewModel.getNameListVM().observe(this, new Observer<List<TestModel>>() {
            @Override
            public void onChanged(final List<TestModel> testModels) {
                adapter.setWords(testModels);
                names.setAdapter(adapter);
                adapter.onCardActionsListener(new NamesAdapter.onCardActionsClick() {
                    @Override
                    public void deleteRow(int position) {
                        int id=testModels.get(position).getId();
                        testViewModel.deleteEntryTestModelVM(id);
                    }

                    @Override
                    public void editRow(final int position) {
                        final TestModel tm=testModels.get(position);
                        final int id=tm.getId();
                        final Dialog dialog = new Dialog(MainActivity.this);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        dialog.setContentView(R.layout.add_new_record);
                        dialog.show();
                        final EditText myName=dialog.findViewById(R.id.name);
                        myName.setText(tm.getName());
                        final EditText myPhone=dialog.findViewById(R.id.phone);
                        myPhone.setText(tm.getPhone());
                        final   EditText myCity=dialog.findViewById(R.id.city);
                        myCity.setText(tm.getCity());
                        final EditText myCompany=dialog.findViewById(R.id.company);
                        myCompany.setText(tm.getCompany());
                        Button addEntry=dialog.findViewById(R.id.add);
                        addEntry.setText("Update");
                        addEntry.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                TestModel updated=new TestModel(myName.getText().toString(),myPhone.getText().toString(),myCity.getText().toString(),myCompany.getText().toString());
                                dialog.dismiss();
                                testViewModel.updateEntryTestModelVM(id,updated);
                                Toast.makeText(MainActivity.this,"Record Updated",Toast.LENGTH_SHORT).show();

                            }
                        });


                    }
                });
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setContentView(R.layout.add_new_record);
                dialog.show();
               final EditText myName=dialog.findViewById(R.id.name);
                final EditText myPhone=dialog.findViewById(R.id.phone);
              final   EditText myCity=dialog.findViewById(R.id.city);
                final EditText myCompany=dialog.findViewById(R.id.company);
                Button addEntry=dialog.findViewById(R.id.add);
                addEntry.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        Toast.makeText(MainActivity.this,"Record Added",Toast.LENGTH_SHORT).show();
                        TestModel model=new TestModel(myName.getText().toString(),myPhone.getText().toString(),myCity.getText().toString(),myCompany.getText().toString());
                        testViewModel.insertTestModelVM(model);
                    }
                });

            }
        });
    }

}
