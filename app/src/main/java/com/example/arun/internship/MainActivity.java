package com.example.arun.internship;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BreachSearchAsync.IBreachAccount {

    ProgressDialog pd;
    ArrayList<BreachedAccount> breachedAccounts= new ArrayList<BreachedAccount>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Breach Finder");
        final EditText searchkeyword= (EditText)findViewById(R.id.editTextEmail);
        Button searchButton =(Button)findViewById(R.id.buttonSearch);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(searchkeyword.getText().toString().length()>0)

                {
                    String search=searchkeyword.getText().toString().trim();
                    new BreachSearchAsync(MainActivity.this).execute(search);
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Please enter username or email",Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    @Override
    public void setBreachedAccounts(ArrayList<BreachedAccount> breachedAccountsResult) {

        breachedAccounts=breachedAccountsResult;
        dismissProgressDialog();
        ListView listView = (ListView) findViewById(R.id.listView);
        if(breachedAccounts.size()>0) {

            BreachAccountListViewAdapter adapter = new BreachAccountListViewAdapter(this, R.layout.listview_raw_layout, breachedAccounts);
            listView.setAdapter(adapter);
            adapter.setNotifyOnChange(true);
        }
        else
        {
            Toast.makeText(MainActivity.this,"No breach found",Toast.LENGTH_SHORT).show();
            listView.setAdapter(null);

        }
    }

    @Override
    public void startProgressDialog() {

        pd = new ProgressDialog(MainActivity.this);
        pd.setMessage("Loading...");
        pd.show();
        pd.setCancelable(false);
    }
    private void dismissProgressDialog(){
        pd.dismiss();
    }
}
