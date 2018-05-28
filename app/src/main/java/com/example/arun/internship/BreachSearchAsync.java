package com.example.arun.internship;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Arun on 5/28/2018.
 */

public class BreachSearchAsync extends AsyncTask<String,Void,ArrayList<BreachedAccount>> {


    IBreachAccount breachData;
    ArrayList<BreachedAccount> breachDataList= new ArrayList<BreachedAccount>();
    OkHttpClient okHttpClient = new OkHttpClient();

    public BreachSearchAsync(IBreachAccount breachData)
    {
        this.breachData = breachData;
    }

    @Override
    protected void onPostExecute(ArrayList<BreachedAccount> breachedAccounts) {

        breachData.setBreachedAccounts(breachedAccounts);
    }

    @Override
    protected void onPreExecute() {
       breachData.startProgressDialog();;
    }



    @Override
    protected ArrayList<BreachedAccount> doInBackground(String... strings) {

        String url="https://haveibeenpwned.com/api/v2/breachedaccount/";
        String account=strings[0].trim();
        String finalURL=url+account;
        Request request = new Request.Builder()
                .url(finalURL)
                .build();
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
           String result=response.body().string();
           breachDataList=BreachResponseParserUtil.responseParser(result);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return breachDataList;

    }
    public  interface IBreachAccount
    {
        void setBreachedAccounts(ArrayList<BreachedAccount> breachedAccounts);
        void startProgressDialog();

    }

}
