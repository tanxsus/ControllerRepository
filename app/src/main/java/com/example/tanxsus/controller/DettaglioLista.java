package com.example.tanxsus.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.HashMap;
import java.util.Set;


public class DettaglioLista extends ActionBarActivity {

    private ListView lv;

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
}
