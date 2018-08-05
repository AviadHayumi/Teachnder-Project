package com.example.aviad.teachnder.Server;


import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetDataFromServer extends AsyncTask<String,Void,String> {

    iGetServerResult _listener;

    public GetDataFromServer(iGetServerResult listener){
        _listener = listener;
    }

    public String resultData = "";

    @Override
    protected void onPreExecute() {
        super.onPreExecute();


    }

    @Override
    protected String doInBackground(String... params) {
        StringBuilder result = new StringBuilder();

        try {
            URL url = new URL(params[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while( (line = bufferedReader.readLine()) != null)  {
                result.append(line).append('\n');

            }

            if(bufferedReader!= null) {
                bufferedReader.close();
            }

            resultData = result.toString();


        } catch (IOException e) {
            return "network error";
        }


        return result.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        Log.d("trytrytry", "onPostExecute: " + s);

        if(_listener != null){
            _listener.onResult(s);
        }
    }

    public interface iGetServerResult{
        void onResult(String res);
    }

}
