package id.inixindosurabaya.androidwebservice;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

public class LihatDetail extends AppCompatActivity implements View.OnClickListener {
    // inisialisasi komponen
    EditText edit_id, edit_nama, edit_jabatan, edit_gaji;
    Button btn_ubah, btn_hapus;
    String id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_detail);

        // mengenali komponen
        edit_id = findViewById(R.id.edit_id);
        edit_nama = findViewById(R.id.edit_nama);
        edit_jabatan = findViewById(R.id.edit_jabatan);
        edit_gaji = findViewById(R.id.edit_gaji);
        btn_ubah = findViewById(R.id.btn_ubah);
        btn_hapus = findViewById(R.id.btn_hapus);

        // event handling
        btn_ubah.setOnClickListener(this);
        btn_hapus.setOnClickListener(this);

        // mendeteksi id mana yg dipilih
        Intent myIntent = getIntent();
        id = myIntent.getStringExtra(Konfigurasi.PGW_ID);
        edit_id.setText(id);

        // memanggil isi json sesuai id yg dipilih
        getJSON();
    }

    private void getJSON() {
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(LihatDetail.this,
                        "Mengambil Data",
                        "Harap Tunggu.....",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String hasil = handler.sendGetResponse(Konfigurasi.URL_GET_DETAIL, id);
                return hasil;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                tampilkanDetail(s);
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void tampilkanDetail(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);
            JSONObject x = result.getJSONObject(0);
            String name = x.getString(Konfigurasi.TAG_JSON_NAMA);
            String desg = x.getString(Konfigurasi.TAG_JSON_JABATAN);
            String gaji = x.getString(Konfigurasi.TAG_JSON_GAJI);

            edit_nama.setText(name);
            edit_jabatan.setText(desg);
            edit_gaji.setText(gaji);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {

    }
}
