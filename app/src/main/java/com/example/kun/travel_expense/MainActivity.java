package com.example.kun.travel_expense;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


public class MainActivity extends AppCompatActivity {
    EditText location;
    EditText date;
    EditText dollar_spent;
    ListView list;
    String listItems;
    String location_item = "";
    String date_item = "";
    String amt_item = "";
    String object="";
    private final String file = "list.txt";
    private FileOutputStream fos;
    private OutputStreamWriter out;
    private InputStream in;
    InputStream inputStream= null;
    StringBuilder stringBuilder = new StringBuilder();


    ArrayList<String> city = new ArrayList<String>();
    ArrayList<String> time = new ArrayList<String>();
    ArrayList<String> amt = new ArrayList<String>();


    public class Object {
        public   String city="";
        public   String time="";
        public   String amt="";

        public String getCity() {
            return city;
        }
        public String getTime() {
            return this.time;
        }
        public String getAmt() {
            return this.amt;
        }
        public void setCity(String newcity) {
            this.city = newcity;
        }
        public void setTime(String newtime) {
            this.time = newtime;
        }
        public void setAmt(String amt) {
            this.amt = amt;
        }

    }
    final int PICK1 = Menu.FIRST + 1;
    final int PICK2 = Menu.FIRST + 2;
    final int PICK3 = Menu.FIRST + 3;
    final int PICK4 = Menu.FIRST + 4;
    final int PICK5 = Menu.FIRST + 5;
    final int PICK6 = Menu.FIRST + 6;
    final int PICK7 = Menu.FIRST + 7;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = (ListView) findViewById(R.id.list_items);
        location = (EditText) findViewById(R.id.location);
        date = (EditText) findViewById(R.id.date);
        dollar_spent = (EditText) findViewById(R.id.dollar_spent);




            try {
                File file=new File(getFilesDir()+"/list.txt");
                if (file.exists()){
                    inputStream = openFileInput("list.txt");
                    InputStreamReader in=new InputStreamReader(inputStream);
                    BufferedReader reader= new BufferedReader(in);
                    String str="";
                    while((str=reader.readLine())!=null){
                        city.add(str.substring(0,str.indexOf(";")));
                        time.add(str.substring(str.indexOf(";")+1,str.lastIndexOf(";")));
                        amt.add(str.substring(str.lastIndexOf(";")+1));
                    }
                    reader.close();
                }else{
                    city.add("Toro");
                    city.add("Gourmet");
                    city.add("Lobster");
                    city.add("BonChon");
                    time.add("Mar-10");
                    time.add("Mar-11");
                    time.add("Mar-13");
                    time.add("Mar-13");
                    amt.add("47.89");
                    amt.add("40.35");
                    amt.add("34.56");
                    amt.add("30.99");
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        //adapter

        Adapter adapter = new Adapter(this, city, time, amt);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                location_item = String.valueOf(city.get(position));
                date_item = String.valueOf(time.get(position));
                amt_item = String.valueOf(amt.get(position));
                location.setText(location_item);
                date.setText(date_item);
                dollar_spent.setText(amt_item);

            }
        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {  //add menu items
        super.onCreateOptionsMenu(menu);

        MenuItem item1 = menu.add(0, PICK1, Menu.NONE, "Save");
        MenuItem item2 = menu.add(0, PICK2, Menu.NONE, "Finish");
        MenuItem item3 = menu.add(0, PICK3, Menu.NONE, "Add");
        MenuItem item4 = menu.add(0, PICK4, Menu.NONE, "Delete");
        MenuItem item5 = menu.add(0, PICK5, Menu.NONE, "Update");
        MenuItem item6 = menu.add(0, PICK6, Menu.NONE, "Expense Map");
        MenuItem item7 = menu.add(0, PICK7, Menu.NONE, "Expense Calculator");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        int itemID = item.getItemId();
        switch (itemID) {
            case PICK1: //SAVE
                ((BaseAdapter) list.getAdapter()).notifyDataSetChanged();
                doThatIO();
                location.setText("");
                date.setText("");
                dollar_spent.setText("");
                return true;
            case PICK2://Close
                doThatIO();
                this.finish();

                location.setText("");
                date.setText("");
                dollar_spent.setText("");
                return true;
            case PICK3://add

                city.add(location.getText().toString().trim());
                time.add(date.getText().toString().trim());
                amt.add(dollar_spent.getText().toString().trim());

                ((BaseAdapter) list.getAdapter()).notifyDataSetChanged();

                location.setText("");
                date.setText("");
                dollar_spent.setText("");
                return true;

            case PICK4://delete
                for (int i = 0; i < city.size(); i++) {
                    if (city.get(i).equals(location_item)) {
                        city.remove(i);
                        time.remove(i);
                        amt.remove(i);
                    }
                }
                ((BaseAdapter) list.getAdapter()).notifyDataSetChanged();
                location.setText("");
                date.setText("");
                dollar_spent.setText("");
                return true;
            case PICK5://update
                int index = 0;

                for (int i = 0; i < city.size(); i++) {
                    if ((city.get(i).equals(location_item))) {
                        index = i;
                    }
                }

                city.set(index, location.getText().toString().trim());
                time.set(index, date.getText().toString().trim());
                amt.set(index, dollar_spent.getText().toString().trim());

                ((BaseAdapter) list.getAdapter()).notifyDataSetChanged();
                location.setText("");
                date.setText("");
                dollar_spent.setText("");
                //if any change in amt occur, update the change

                return true;

            case PICK6: //open calculator activity
                this.startActivity(new Intent(this, ExpenseMapActivity.class));
                return true;
            case PICK7: //open calculator activity
                this.startActivity(new Intent(this, calculate_activity.class));
                return true;

        }
        return false;
    }

    public void doThatIO(){
        ArrayList<String> append=new ArrayList<>();
        try{
            fos=openFileOutput("list.txt",MODE_PRIVATE);
            out=new OutputStreamWriter(fos);
        }catch (IOException e){

        }
        try{

            for(int i=0;i<city.size();i++){
                object="";
                object=city.get(i)+";"+time.get(i)+";"+amt.get(i);
                append.add(object);
                out.write(append.get(i).trim()+"\n");
            }
            out.close();
        }catch (IOException e){
            Log.e("list",e.getMessage());
        }
    }
    @Override
    public boolean onKeyUp(int keycode, KeyEvent event){   //
        super.onKeyUp(keycode, event);
        if (keycode == KeyEvent.KEYCODE_ENTER) {
            doThatIO();
            return true;
        }
        return true;
    }
}