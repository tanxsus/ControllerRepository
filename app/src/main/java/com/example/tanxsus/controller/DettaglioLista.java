package com.example.tanxsus.controller;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.HashMap;
import java.util.Set;


public class DettaglioLista extends ActionBarActivity {

    private ListView lv;

    private String id_palina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dettaglio_lista);

        Intent intent = getIntent();
        String message = intent.getStringExtra(DisplayMessageActivity.LINEA);

        HashMap<String, HashMap> mappa = DisplayMessageActivity.mappa;

        int count = 0;

        HashMap m = (HashMap) mappa.get(message);

        String[] lista = new String[m.size()];

        Set set = m.keySet();

        for (Object obj : set) {

            lista[count] = obj.toString() + " " + m.get(obj).toString();
            count++;

            if (obj.toString().equals("id_palina")) {
                id_palina = m.get(obj).toString();
            }

        }


        lv = (ListView) findViewById(R.id.listDettaglio);
        lv.setAdapter(new ArrayAdapter<String>(this, R.layout.list_item, R.id.item, lista));


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dettaglio_lista, menu);
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

            Intent intent = new Intent(this, DisplayMessageActivity.class);

            Progress mytask = new Progress(intent);
            mytask.execute();


            intent.putExtra(MyActivity.EXTRA_MESSAGE, id_palina);
            startActivity(intent);

        }
        return true;
    }

    private class Progress extends AsyncTask<String, Void, String> {

        android.app.ProgressDialog progressDialog = new android.app.ProgressDialog(DettaglioLista.this);

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
            finish();
        }


    }


}
