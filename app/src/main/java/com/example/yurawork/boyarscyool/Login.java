package com.example.yurawork.boyarscyool;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.BreakIterator;

public class Login extends AppCompatActivity implements View.OnClickListener {


    Button button2;
    Button button;
    EditText password;
    EditText number;
    TextView tw;
    DBHelper dbh;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        button2 = (Button)findViewById(R.id.button2);
        button = (Button)findViewById(R.id.button);
        number = (EditText)findViewById(R.id.number);
        password = (EditText)findViewById(R.id.password);
        dbh = new DBHelper(this);

    tw = (TextView)findViewById(R.id.infomessage);

        password.setVisibility(View.INVISIBLE);
        button2.setVisibility(View.INVISIBLE);

        button2.setOnClickListener(this);
        SQLiteDatabase database = dbh.getWritableDatabase();
        ContentValues contentValues = new ContentValues();


        contentValues.put(DBHelper.KEY_GROUP, "Test group");
        contentValues.put(DBHelper.KEY_TIME, "test");

        database.insert(DBHelper.TABLE_CONTACTS, null, contentValues);

        Cursor cursor = database.query(DBHelper.TABLE_CONTACTS, null, null, null, null, null, null);




        while (cursor.moveToNext()){
            Log.i("info",cursor.getString(1));
        }



        cursor.close();
    }





    public void onClick(View v) {
        api api = new api(tw, this);


        switch (v.getId()) {

            case R.id.button:
                //asycntas


                //status!=1 ->
                //Toast toast = new Toast();

                //status==1 ->
                password.setVisibility(View.VISIBLE);
                button.setVisibility(View.INVISIBLE);
                button2.setVisibility(View.VISIBLE);

                //// Intent intent = new Intent(this,Activitytwo.class );    //правка кода
                // startActivity(intent);
                break;


            case R.id.button2:

                api.setMethod("auth");
                api.setNumber(number.getText().toString());
                api.setPassword(password.getText().toString());
                api.execute();

                Log.d("error api", "response: " + api.getDesc());//.i("log");


                //status!=1 ->
                //Toast toast = new Toast();

                //status==1 ->
                //asyc


                break;

            default:
                break;
        }
    }

    /*public void onClick2(View v) {
        String Name = GetName.getText().toString();
        SQLiteDatabase database = dbh.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("grouptitle","NEW UPDATED TEST "+Name);


        database.update(DBHelper.TABLE_CONTACTS,cv,null,null);
        Cursor cursor = database.query(DBHelper.TABLE_CONTACTS, null, null, null, null, null, null);

        while (cursor.moveToNext()){
            Log.i("info",cursor.getString(1));
            mInfoTextView.append("\n"+cursor.getString(1));

        }



        cursor.close();
*/





    private void execute() {
    }



}


