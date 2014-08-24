package com.example.tanxsus.controller;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MyActivity extends Activity {


    public final static String EXTRA_MESSAGE = "Ricerca in corso";
    private ProgressDialog progressDialog;


    public void sendMessage(View view) {

        Intent intent = new Intent(MyActivity.this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.fermata);
        String message = editText.getText().toString();

        Progress mytask = new Progress(intent);
        mytask.execute();

        intent.putExtra(EXTRA_MESSAGE, message);
        //progressDialog.dismiss();
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        final TextView textView = (TextView) findViewById(R.id.text_view);

        final EditText fermata;
        final Button ok;
        try {


            fermata = (EditText) findViewById(R.id.fermata);
            ok = (Button) findViewById(R.id.ok);

            /*
            ok.setOnClickListener(new View.OnClickListener() {

                @Override

                public void onClick(View v) {

                    //Progress mytask = new Progress();
                    //mytask.execute();
                }
            });

        */

        } catch (Exception e) {

            e.printStackTrace();

            textView.setText(e.toString());
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private class Progress extends AsyncTask<String, Void, String> {

        android.app.ProgressDialog progressDialog = new android.app.ProgressDialog(MyActivity.this);

        private Intent intent;// = new Intent(MyActivity.this, DisplayMessageActivity.class);

        Progress(Intent intent) {
            this.intent = intent;
        }

        protected void onPreExecute() {

            progressDialog.setTitle("Processing...");
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(true);
            progressDialog.setIndeterminate(true);
            progressDialog.show();
        }

        protected String doInBackground(String... params) {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {

            progressDialog.dismiss();
            //Intent n = new Intent(MyActivity.this, DisplayMessageActivity.class);
            //startActivity(intent);
        }


    }


}
