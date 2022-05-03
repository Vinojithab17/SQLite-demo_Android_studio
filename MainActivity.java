package com.example.mydemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    Button btn_add, btn_viewall;
    EditText et_name, et_age;
    Switch sw_activeCustomer;
    ListView lv_customerlist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btn_add = findViewById(R.id.btn_add);
        btn_viewall = findViewById(R.id.btn_viewall);
        et_age = findViewById(R.id.et_age);
        et_name = findViewById(R.id.et_name);
        sw_activeCustomer = findViewById(R.id.sw_active);
        lv_customerlist = findViewById(R.id.lv_customerList);


        //BUTTON LIStner

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomerModel model;
                try {
                    model = new CustomerModel(-1,et_name.getText().toString(),Integer.parseInt(et_age.getText().toString()),sw_activeCustomer.isChecked());
                    Toast.makeText(MainActivity.this, model.toString(), Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(MainActivity.this, "Error While creating", Toast.LENGTH_SHORT).show();

                    model = new CustomerModel(-1,"error",0,false);
                }

                MYDatabaseDAO database = new MYDatabaseDAO(MainActivity.this);

                boolean success = database.Addthis(model);

                Toast.makeText(MainActivity.this, "success = " + success, Toast.LENGTH_SHORT).show();

            }
        });

        btn_viewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                MYDatabaseDAO db = new MYDatabaseDAO(MainActivity.this);
                List<CustomerModel> total = db.getALLs();
                //Toast.makeText(MainActivity.this, total.toString(), Toast.LENGTH_SHORT).show();

                ArrayAdapter customers = new ArrayAdapter<CustomerModel>(MainActivity.this, android.R.layout.simple_list_item_1,total);

                lv_customerlist.setAdapter(customers);

            }
        });


        lv_customerlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CustomerModel c = (CustomerModel) adapterView.getItemAtPosition(i);

                MYDatabaseDAO db = new MYDatabaseDAO(MainActivity.this);
                db.deleteme(c);

                List<CustomerModel> total = db.getALLs();
                //Toast.makeText(MainActivity.this, total.toString(), Toast.LENGTH_SHORT).show();

                ArrayAdapter customers = new ArrayAdapter<CustomerModel>(MainActivity.this, android.R.layout.simple_list_item_1,total);

                lv_customerlist.setAdapter(customers);
            }
        });



    }
}