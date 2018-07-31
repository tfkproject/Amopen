package ta.jurais.amopen;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ta.jurais.amopen.model.ItemMahasiswa;
import ta.jurais.amopen.util.Config;
import ta.jurais.amopen.util.Request;

public class StaffJobDetailActivity extends AppCompatActivity {

    List<ItemMahasiswa> items;
    private ProgressDialog pDialog;
    private static String url = Config.HOST+"list_kompen_stf_detail.php";
    private static String url_update = Config.HOST+"update_status_job.php";

    Button btnUpdate;
    TextView txtNama, txtDesk, txtLabor, txtStatus;

    String id, nama, ruangan, status, pekerjaan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_job_detail);
        //buat tombol back di ActionBar
        getSupportActionBar().setTitle("Detail Job Kompen");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String id_kompen = getIntent().getStringExtra("key_id_kompen");

        new dapatkanData(id_kompen).execute();

        txtNama = (TextView) findViewById(R.id.txt_nama);
        txtDesk = (TextView) findViewById(R.id.txt_desk);
        txtLabor = (TextView) findViewById(R.id.txt_ruang);
        txtStatus = (TextView) findViewById(R.id.txt_status);
        btnUpdate = (Button) findViewById(R.id.btn_update);

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
            pDialog = new ProgressDialog(StaffJobDetailActivity.this);
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
                            nama = c.getString("nama");
                            ruangan = c.getString("ruangan");
                            status = c.getString("status");
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

            txtNama.setText(nama);
            txtDesk.setText(pekerjaan);
            txtLabor.setText(ruangan);
            txtStatus.setText(status);
            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new updateData(id).execute();
                }
            });
        }

    }

    private class updateData extends AsyncTask<Void,Void,String> {

        //variabel untuk tangkap data
        private int scs = 0;
        private String psn;
        private String id_kompen;

        public updateData(String id_kompen){
            this.id_kompen = id_kompen;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(StaffJobDetailActivity.this);
            pDialog.setMessage("Update data...");
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
                    String response = Request.post(url_update,dataToSend);

                    //dapatkan respon
                    Log.e("Respon", response);

                    JSONObject ob = new JSONObject(response);
                    scs = ob.getInt("success");
                    if (scs == 1) {
                        psn = ob.getString("message");
                    } else {
                        // no data found
                        psn = ob.getString("message");
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
            if (scs == 1) {
                finish();
                Toast.makeText(StaffJobDetailActivity.this, psn, Toast.LENGTH_SHORT).show();
            } else {
                // no data found
                Toast.makeText(StaffJobDetailActivity.this, psn, Toast.LENGTH_SHORT).show();
            }

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
