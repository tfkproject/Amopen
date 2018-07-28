package app.tfkproject.amopen;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.tfkproject.amopen.model.ItemMahasiswa;
import app.tfkproject.amopen.util.Config;
import app.tfkproject.amopen.util.Request;

public class KompenDetailActivity extends AppCompatActivity {

    List<ItemMahasiswa> items;
    private ProgressDialog pDialog;
    private static String url = Config.HOST+"kompen_detail.php";

    Button btnUpdate;
    TextView txtNamaMhs, txtStatus, txtNamaDsn, txtJabatan, txtDesk, txtRuang;

    String id, nama_mhs, status, nama_dsn, jabatan, ruangan, pekerjaan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kompen_detail);
        //buat tombol back di ActionBar
        getSupportActionBar().setTitle("Detail Kompen");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String id_kompen = getIntent().getStringExtra("key_id_kompen");

        new dapatkanData(id_kompen).execute();

        txtNamaMhs = (TextView) findViewById(R.id.txt_nm_mhs);
        txtStatus = (TextView) findViewById(R.id.txt_status);
        txtNamaDsn = (TextView) findViewById(R.id.txt_nm_dsn);
        txtJabatan = (TextView) findViewById(R.id.txt_jab);
        txtDesk = (TextView) findViewById(R.id.txt_desk);
        txtRuang = (TextView) findViewById(R.id.txt_ruang);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id_menu = item.getItemId();
        if(id_menu == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private class dapatkanData extends AsyncTask<Void,Void,String> {

        //variabel untuk tangkap data
        private int scs = 0;
        private String id_kompen;

        public dapatkanData(String id_kompen){
            this.id_kompen = id_kompen;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(KompenDetailActivity.this);
            pDialog.setMessage("Memuat data...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(Void... params) {

            try{
                //susun parameter
                HashMap<String,String> detail = new HashMap<>();
                detail.put("id_kompen", id_kompen);

                try {
                    //convert this HashMap to encodedUrl to send to php file
                    String dataToSend = hashMapToUrl(detail);
                    //make a Http request and send data to php file
                    String response = Request.post(url,dataToSend);

                    //dapatkan respon
                    Log.e("Respon", response);

                    JSONObject ob = new JSONObject(response);
                    scs = ob.getInt("success");

                    if (scs == 1) {
                        JSONArray products = ob.getJSONArray("field");

                        for (int i = 0; i < products.length(); i++) {
                            JSONObject c = products.getJSONObject(i);

                            // Storing each json item in variable
                            id = c.getString("id_kompen");
                            nama_mhs = c.getString("nm_mhs");
                            status = c.getString("status");
                            nama_dsn = c.getString("nm_dsn");
                            jabatan = c.getString("jabatan");
                            ruangan = c.getString("ruangan");
                            pekerjaan = c.getString("deks_job");


                        }
                    } else {
                        // no data found

                    }

                } catch (JSONException e){
                    e.printStackTrace();
                }

            } catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            pDialog.dismiss();

            txtNamaMhs.setText(nama_mhs);
            txtStatus.setText(status);
            txtNamaDsn.setText(nama_dsn);
            txtJabatan.setText(jabatan);
            txtDesk.setText(pekerjaan);
            txtRuang.setText(ruangan);
        }

    }

    private String hashMapToUrl(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }

}
