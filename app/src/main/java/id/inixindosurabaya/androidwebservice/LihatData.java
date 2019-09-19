package id.inixindosurabaya.androidwebservice;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class LihatData extends AppCompatActivity implements AdapterView.OnItemClickListener {
    // inisialisasi
    private ListView listView;
    private String JSON_STRING;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_data);
        // memunculkan icon back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // mengenali komponen
        listView = findViewById(R.id.list_view);
        listView.setOnItemClickListener(this);

        // membuat perintah untuk mengambil data JSON
        getJSON();
    }

    public void onBackPressed() {
        startActivity(new Intent(LihatData.this, MainActivity.class));
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return true;
    }

    private void getJSON() {
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(LihatData.this,
                        "Mengambil Data",
                        "Harap Tunggu.....",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String hasil = handler.sendGetResponse(Konfigurasi.URL_GET_ALL);
                return hasil;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;

                // menampilkan semua data JSON yg berhasil diambil dari server
                tampilkanSemuaData();
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void tampilkanSemuaData() {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

        // error handling jika internet putus
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);

            // menampilkan data json
            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String id = jo.getString(Konfigurasi.TAG_JSON_ID);
                String name = jo.getString(Konfigurasi.TAG_JSON_NAMA);
                HashMap<String, String> pegawai = new HashMap<>();
                pegawai.put(Konfigurasi.TAG_JSON_ID, id);
                pegawai.put(Konfigurasi.TAG_JSON_NAMA, name);

                // mengubah format json menjadi format arraylist
                list.add(pegawai);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // membuat adapter untuk meletakkan arraylist kedalam listview
        ListAdapter adapter = new SimpleAdapter(
                getApplicationContext(), list,
                R.layout.activity_list_item,
                new String[] {Konfigurasi.TAG_JSON_ID, Konfigurasi.TAG_JSON_NAMA},
                new int[] {R.id.id, R.id.name}
        );
        listView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent myIntent = new Intent(LihatData.this, LihatDetail.class);
        HashMap<String, String> map = (HashMap)parent.getItemAtPosition(position);
        String pgwId = map.get(Konfigurasi.TAG_JSON_ID).toString();
        myIntent.putExtra(Konfigurasi.PGW_ID, pgwId);
        startActivity(myIntent);
    }
}
