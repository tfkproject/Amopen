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

public class InputMhsActivity extends AppCompatActivity {

    EditText edtNim, edtNama, edtEmail, edtPass, edtJumKompen;
    Button btnSubmit;

    private ProgressDialog pDialog;
    private static String url = Config.HOST+"input_mahasiswa.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_mhs);

        getSupportActionBar().setTitle("Input Data Mahasiswa");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        edtNim = (EditText) findViewById(R.id.edt_nim);
        edtNama = (EditText) findViewById(R.id.edt_nama);
        edtEmail = (EditText) findViewById(R.id.edt_email);
        edtPass = (EditText) findViewById(R.id.edt_pass);
        edtJumKompen = (EditText) findViewById(R.id.edt_jum_kompen);

        btnSubmit = (Button) findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nim = edtNim.getText().toString();
                String nama = edtNama.getText().toString();
                String email = edtEmail.getText().toString();
                String pass = edtPass.getText().toString();
                String sisa_jam = edtJumKompen.getText().toString();

                new postData(nim, nama, email, pass, sisa_jam).execute();
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
        private String nim, nama, email, pass, sisa_jam;

        public postData(String nim,
                        String nama,
                        String email,
                        String pass,
                        String sisa_jam){
            this.nim = nim;
            this.nama = nama;
            this.email = email;
            this.pass = pass;
            this.sisa_jam = sisa_jam;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(InputMhsActivity.this);
            pDialog.setMessage("Posting data...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(Void... params) {

            try{
                //susun parameter
                HashMap<String,String> detail = new HashMap<>();
                detail.put("nim", nim);
                detail.put("nama", nama);
                detail.put("email", email);
                detail.put("pass", pass);
                detail.put("jum_kompen", sisa_jam);
                detail.put("sisa_jam", sisa_jam);

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
                Toast.makeText(InputMhsActivity.this, psn, Toast.LENGTH_SHORT).show();
            } else {
                // no data found
                Toast.makeText(InputMhsActivity.this, psn, Toast.LENGTH_SHORT).show();
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
