package com.example.tanxsus.controller;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.xmlrpc.android.XMLRPCClient;

import java.net.URI;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public class DisplayMessageActivity extends ActionBarActivity {

    public final static String LINEA = "";
    public static HashMap<String, HashMap> mappa = new HashMap<String, HashMap>();
    private XMLRPCClient client;
    private ListView lv;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Ricevi messaggio dall'intent
        Intent intent = getIntent();
        String message = intent.getStringExtra(MyActivity.EXTRA_MESSAGE);

        StringBuffer sb = new StringBuffer();

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();


        List<HashMap> arrivi = arrivi(message);

        if (arrivi == null || arrivi.size() == 0) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Errore...");
            alertDialog.setMessage("Codice fermata errato");
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {


                    Intent intent = new Intent(DisplayMessageActivity.this, MyActivity.class);
                    startActivity(intent);

                }
            });
            //alertDialog.setIcon(R.drawable.icon);
            alertDialog.show();
        }

        String[] lista = new String[arrivi.size()];

        int count = 0;


        for (HashMap s : arrivi) {
            actionBar.setSubtitle((String) s.get("nome_palina"));

            String chiave = s.get("linea") + " " + s.get("annuncio");

            lista[count] = chiave;

            mappa.put(chiave, s);

            count++;
        }

        lv = (ListView) findViewById(R.id.list);
        lv.setAdapter(new ArrayAdapter<String>(this, R.layout.list_item, R.id.item, lista));


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Object s = lv.getItemAtPosition(position);

                Intent intent = new Intent(DisplayMessageActivity.this, DettaglioLista.class);
                intent.putExtra(LINEA, s.toString());

                startActivity(intent);

            }

        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.display_message, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            finish();
            startActivity(getIntent());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private String idAutenticazione() {

        String id = null;

        try {
            Object[] object = new Object[2];

            object[0] = "v7xg49vZ7F_rm7phWimxhNlPefoT2ncw";//chiave sviluppatore
            object[1] = "";

            URI uri = URI.create("http://muovi.roma.it/ws/xml/autenticazione/1");

            client = new XMLRPCClient(uri);

            id = (String) client.callEx("autenticazione.Accedi", object);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return id;
    }

    private List<HashMap> arrivi(String fermata) {
        List<HashMap> arrivi = new LinkedList<HashMap>();

        URI uri = URI.create("http://muovi.roma.it/ws/xml/paline/7");

        Object[] paline = new Object[3];

        paline[0] = idAutenticazione();
        paline[1] = fermata; //codice palina
        paline[2] = "it";

        HashMap primi_per_palina = null;
        Object[] list = null;

        try {

            client = new XMLRPCClient(uri);
            client.callEx("paline.Previsioni", paline);

            list = (Object[]) ((HashMap) ((HashMap) client.callEx("paline.Previsioni", paline)).get("risposta")).get("primi_per_palina");

            for (Object s : list) {
                if (s instanceof HashMap) {
                    primi_per_palina = (HashMap) s;
                    break;
                }
            }

            list = (Object[]) primi_per_palina.get("arrivi");

            for (Object o : list) {
                if (o instanceof HashMap) {
                    arrivi.add((HashMap) o);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrivi;
    }
}





