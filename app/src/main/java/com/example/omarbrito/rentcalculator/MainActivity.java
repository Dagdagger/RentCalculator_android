package com.example.omarbrito.rentcalculator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText txtInput;
    int listSize = 0;
    int whatever = 0;
    ArrayList<String> items;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initializeListView();
        initializeButtons();
        initializeActivityTwo();

    }

    private void initializeActivityTwo() {
        Button button = findViewById(R.id.button7);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(i);

            }
        });




    }

    private void initializeListView(){

        loadList();
        final ListView mlistView = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> roommateAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, items);
        mlistView.setAdapter(roommateAdapter);
        txtInput=findViewById(R.id.editText2);
        txtInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_DOWN) {
                    String newRoommate = txtInput.getText().toString();
                    items.add(newRoommate);
                }
                return false;
            }
        });
        Button addRoommate = findViewById(R.id.button);
        addRoommate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newRoommate = txtInput.getText().toString();
                items.add(newRoommate);
                listSize = items.size();
                txtInput.setText("");
                mlistView.invalidateViews();
                saveData();
            }
        });
        Button deleteRoommate = findViewById(R.id.button2);
        deleteRoommate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(items.size() > 0) {
                    items.remove(items.size() - 1);
                    txtInput.setText("");
                    listSize = items.size();
                    mlistView.invalidateViews();
                    saveData();
                }
            }
        });

    }

    private void saveData(){

        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(items);
        editor.putString("task list", json);
        editor.apply();
    }


    private void loadList() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        items = gson.fromJson(json, type);

        if (items == null){
            items = new ArrayList<String>();
        }


    }

    private void initializeButtons(){
            Button setElectricity = findViewById(R.id.button3);
            Button setBaseRent = findViewById(R.id.button4);
            Button setInternet = findViewById(R.id.button5);
            Button setInsurance = findViewById(R.id.button6);
            TextView elecNum = findViewById(R.id.textView2);
            TextView rentNum = findViewById(R.id.textView9);
            TextView internetNum = findViewById(R.id.textView11);
            TextView insuranceNum = findViewById(R.id.textView13);

            txtInput=findViewById(R.id.editText2);

            setStuff(setElectricity, elecNum, txtInput);
            setStuff(setBaseRent, rentNum, txtInput);
            setStuff(setInternet, internetNum, txtInput);
            setStuff(setInsurance, insuranceNum, txtInput);

    }

    public void setStuff(Button b, final TextView v, final EditText edit){

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edit.getText().toString() != "") {
                    String newNum;
                    newNum = txtInput.getText().toString();
                    float number = Float.parseFloat(newNum);
                    float numOfMates = (float) listSize;
                    DecimalFormat df = new DecimalFormat("#.##");
                    float total = number / numOfMates;
                    df.format(total);
                    String realTotal = Float.toString(total);
                    v.setText(realTotal);
                    edit.setText("");
                }
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
