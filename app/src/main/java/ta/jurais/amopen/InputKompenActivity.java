package ta.jurais.amopen;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import ta.jurais.amopen.util.Config;
import ta.jurais.amopen.util.Request;

public class InputKompenActivity extends AppCompatActivity {

    EditText edtDesk, edtRuang, edtQuota;
    Button btnSubmit;

    private ProgressDialog pDialog;
    private static String url = Config.HOST+"input_kompen.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_kompen);

        getSupportActionBar().setTitle("Input Data Kompen");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final String id_staff_kampus = getIntent().getStringExtra("key_id_staff_kampus");

        edtDesk = (EditText) findViewById(R.id.edt_desk);
        edtRuang = (EditText) findViewById(R.id.edt_ruang);
        edtQuota = (EditText) findViewById(R.id.edt_quota);

        btnSubmit = (Button) findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String desk = edtDesk.getText().toString();
                String ruangan = edtRuang.getText().toString();
                String quota = edtQuota.getText().toString();

                new postData(id_staff_kampus, desk, ruangan, quota).execute();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id_menu = item.getItemId();
        if(id_menu == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private class postData extends AsyncTask<Void,Void,String> {

        //variabel untuk tangkap data
        private int scs = 0;
        private String psn;
        private String id_staff_kampus, desk_job, ruangan, quota;

        public postData(String id_staff_kampus,
                        String desk_job,
                        String ruangan,
                        String quota){
            this.id_staff_kampus = id_staff_kampus;
            this.desk_job = desk_job;
            this.ruangan = ruangan;
            this.quota = quota;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(InputKompenActivity.this);
            pDialog.setMessage("Posting data...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(Void... params) {

            try{
                //susun parameter
                HashMap<String,String> detail = new HashMap<>();
                detail.put("id_staff_kampus", id_staff_kampus);
                detail.put("desk_job", desk_job);
                detail.put("ruangan", ruangan);
                detail.put("quota", quota);

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
                Toast.makeText(InputKompenActivity.this, psn, Toast.LENGTH_SHORT).show();
            } else {
                // no data found
                Toast.makeText(InputKompenActivity.this, psn, Toast.LENGTH_SHORT).show();
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
