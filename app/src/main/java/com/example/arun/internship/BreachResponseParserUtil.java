package com.example.arun.internship;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Arun on 5/28/2018.
 */

public class BreachResponseParserUtil {


    public static ArrayList<BreachedAccount> responseParser(String result) throws JSONException {
        ArrayList<BreachedAccount> breachedAccountsList= new ArrayList<BreachedAccount>();
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
                breachedAccountsList.add(baccount);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return breachedAccountsList;
    }
}
