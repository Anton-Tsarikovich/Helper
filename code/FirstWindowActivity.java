package com.example.antontsarikovich.helper;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class FirstWindowActivity extends Activity {

    public Button downloadButton;
    public Button setPasswordButton;
    private EditText getPassword;
    public TextView someText;
    private EditText getNumber;
    public String password;
    public String numberOfGroup;
    public EditText getAndSavePassword;
    public Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_layout);
        initialization();

        View.OnClickListener clickListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.savePasswordButton:
                        FileSystemManager.savePassword(getAndSavePassword.getText().toString(),activity);
                        break;
                }
            }
        };
        setPasswordButton.setOnClickListener(clickListener);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_first_window, menu);
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
    private void initialization(){
        downloadButton = (Button) findViewById(R.id.downloadButton);
        getPassword = (EditText) findViewById(R.id.passwordEdit);
        getNumber = (EditText) findViewById(R.id.textNumberOfGroup);
        someText = (TextView) findViewById(R.id.numberOfGroup);
        setPasswordButton = (Button) findViewById(R.id.savePasswordButton);
        getAndSavePassword = (EditText) findViewById(R.id.getPassword);

    }


}
