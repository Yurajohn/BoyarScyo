package com.example.yurawork.boyarscyool;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
//import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;



class api extends AsyncTask<Void, Void, String> {
    private String number;
    private String password;
    private String method;
    private String status;
    private String desc;

    Context ct;

    TextView textView;



    public String getStatuscode() {
        return status;
    }

    public void setStatuscode(String status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }



    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }



    public api(TextView textView,Context ct){
        this.textView = textView;

        this.ct = ct;



    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        //progressBar.setVisibility(View.VISIBLE);
        //mInfoTextView.setText("запит відправлено");
        //startButton.setVisibility(View.INVISIBLE); // прячем кнопку
    }

    @Override
    protected String doInBackground(Void... params) {
        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        byte[] data = null;
        InputStream is = null;

        String parammetrs = "";
        try {

        JSONObject dataJsonObjResponce = new JSONObject();
        dataJsonObjResponce.put("apiid",1);
            dataJsonObjResponce.put("timestamp",1);

        if(this.getMethod().equals("auth")){




                dataJsonObjResponce.put("method", "auth");
                dataJsonObjResponce.put("number", number);
                dataJsonObjResponce.put("password", password);







        }


        if(this.getMethod().equals("getlessons")){

                dataJsonObjResponce.put("method", "getlessons");
                dataJsonObjResponce.put("number", number);
                dataJsonObjResponce.put("password", password);


        }


        parammetrs = dataJsonObjResponce.toString();

        }catch (Exception e){

        }



        // Will contain the raw JSON response as a string.
        String forecastJsonStr = null;

        try {
            // Construct the URL for the OpenWeatherMap query
            // Possible parameters are avaiable at OWM's forecast API page, at
            // http://openweathermap.org/API#forecast
            URL url = new URL("http://blviv.com/api");

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            OutputStream os = urlConnection.getOutputStream();
            data = parammetrs.getBytes("UTF-8");
            os.write(data);
            data = null;

            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            forecastJsonStr = buffer.toString();
            return forecastJsonStr;
        } catch (IOException e) {
            Log.e("PlaceholderFragment", "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
            return null;
        } finally{
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("PlaceholderFragment", "Error closing stream", e);
                }
            }
        }
    }

    @Override
    protected void onPostExecute(String jsonrespons) {
        super.onPostExecute(jsonrespons);
        textView.setText(jsonrespons);
        //progressBar.setVisibility(View.INVISIBLE);
        //startButton.setVisibility(View.INVISIBLE);

        JSONObject dataJsonObj =null;
        String secondName = "";
        try {
            dataJsonObj = new JSONObject(jsonrespons);
            JSONArray friends = dataJsonObj.getJSONArray("friends");
            Integer statuscode = Integer.parseInt(
                    dataJsonObj.get("status").toString()
            );
            String desc = dataJsonObj.get("desc").toString();

            this.setStatuscode(status);
            this.setDesc(desc);
            //this.setDesc(desc);


            if (this.getMethod().equals("auth")) {


                if (statuscode == 1) {

                    Intent intent = new Intent(this.ct, MenuActivity.class);
                    intent.putExtra("number", number);
                    intent.putExtra("password", password);
                    ct.startActivity(intent);
                } else {
                    Toast toast = Toast.makeText(this.ct,
                            "Помилка авторизації: " + desc, Toast.LENGTH_LONG);
                    toast.show();
                }

            }


            if (this.getMethod().equals("getUsers")) {


                Toast toast = Toast.makeText(this.ct,
                        "Помилка авторизації: " + desc, Toast.LENGTH_LONG);
                toast.show();


            }

            //Log.i("respons", dataJsonObj.get("desc").toString());

            textView.setText("respons"+dataJsonObj.get("desc").toString());


        } catch (JSONException e){
            Log.e("json eror","json errror");

        }
    }
}