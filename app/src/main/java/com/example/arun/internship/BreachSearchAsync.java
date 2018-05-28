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
        String account=strings[0];
        String finalURL=url+account;
        Request request = new Request.Builder()
                .url(finalURL)
                .build();
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            String result=response.body().string();
            try {
                JSONArray jsonArray= new JSONArray(result);
                for(int i=0;i<jsonArray.length();i++)
                {
                    JSONObject accountObj= jsonArray.getJSONObject(i);
                    BreachedAccount baccount= new BreachedAccount();
                    baccount.setTitle(accountObj.getString("Title"));
                    baccount.setBreachdate(accountObj.getString("BreachDate"));
                    baccount.setDescription(accountObj.getString("Description"));
                    JSONArray dataclassesArray= accountObj.getJSONArray("DataClasses");
                    String dataClasses="";
                    for(int j=0;j<dataclassesArray.length();j++)
                    {
                        if(j==0)
                            dataClasses=dataClasses+dataclassesArray.getString(j);
                        else
                            dataClasses=dataClasses+","+dataclassesArray.getString(j);

                    }
                    baccount.setDataclasses(dataClasses);
                    breachDataList.add(baccount);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
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
