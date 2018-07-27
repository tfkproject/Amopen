package app.tfkproject.amopen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.tfkproject.amopen.util.Config;
import app.tfkproject.amopen.util.Request;
import app.tfkproject.amopen.util.SessionManager;

public class LoginActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText edtEmail, edtPass;
    Button btnLogin;
    int posisi = 0;
    private ProgressDialog pDialog;

    SessionManager session;
    private static String url_adm = Config.HOST+"login_adm.php";
    private static String url_mhs = Config.HOST+"login_mhs.php";
    private static String url_stf = Config.HOST+"login_stf.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session = new SessionManager(LoginActivity.this);

        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.jenis_login);

        // Spinner click listener
        spinner.setOnItemSelectedListener(LoginActivity.this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Pilih login");
        categories.add("Admin");
        categories.add("Mahasiswa");
        categories.add("Staff Kampus");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        edtEmail = (EditText) findViewById(R.id.edt_email);

        edtPass = (EditText) findViewById(R.id.edt_pass);

        btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtEmail.getText().toString();
                String pass = edtPass.getText().toString();
                switch(posisi) {
                    case 1 :
                        //startActivity(new Intent(LoginActivity.this, AdminActivity.class));
                        new loginAdmin(email, pass).execute();
                        break; // optional

                    case 2 :
                        //startActivity(new Intent(LoginActivity.this, MahasiswaActivity.class));
                        new loginMahasiswa(email, pass).execute();
                        break; // optional

                    case 3 :
                        //startActivity(new Intent(LoginActivity.this, StaffActivity.class));
                        new loginStaff(email, pass).execute();
                        break; // optional
                    default : // Optional
                        Toast.makeText(LoginActivity.this, "Pilih login anda", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
        posisi = position;

        // Showing selected spinner item
        //Toast.makeText(parent.getContext(), "Selected: " + position, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    private class loginAdmin extends AsyncTask<Void,Void,String> {

        //variabel untuk tangkap data
        private int scs = 0;
        private String psn;
        private String email, password;

        public loginAdmin(String email, String password){
            this.email = email;
            this.password = password;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Loading...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(Void... params) {

            try{
                //susun parameter
                HashMap<String,String> detail = new HashMap<>();
                detail.put("email", email);
                detail.put("pass", password);

                try {
                    //convert this HashMap to encodedUrl to send to php file
                    String dataToSend = hashMapToUrl(detail);
                    //make a Http request and send data to php file
                    String response = Request.post(url_adm, dataToSend);

                    //dapatkan respon
                    Log.e("Email", email);
                    Log.e("Pass", password);
                    Log.e("Url", url_adm);
                    Log.e("Respon Adm", response);

                    JSONObject ob = new JSONObject(response);
                    scs = ob.getInt("success");

                    if (scs == 1) {
                        JSONArray products = ob.getJSONArray("field");

                        for (int i = 0; i < products.length(); i++) {
                            JSONObject c = products.getJSONObject(i);

                            // Storing each json item in variable
                            String id_user = c.getString("id_admin");
                            String nama = c.getString("nama");
                            /*String email = c.getString("email");*/

                            //buat sesi login
                            session.createLoginSession("1", id_user, nama);
                        }
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
            if(scs == 1){
                finish();
                Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(LoginActivity.this, psn, Toast.LENGTH_SHORT).show();
            }
        }

    }

    private class loginMahasiswa extends AsyncTask<Void,Void,String> {

        //variabel untuk tangkap data
        private int scs = 0;
        private String psn;
        private String email, password;

        public loginMahasiswa(String email, String password){
            this.email = email;
            this.password = password;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Loading...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(Void... params) {

            try{
                //susun parameter
                HashMap<String,String> detail = new HashMap<>();
                detail.put("email", email);
                detail.put("pass", password);

                try {
                    //convert this HashMap to encodedUrl to send to php file
                    String dataToSend = hashMapToUrl(detail);
                    //make a Http request and send data to php file
                    String response = Request.post(url_mhs, dataToSend);

                    //dapatkan respon
                    Log.e("Email", email);
                    Log.e("Pass", password);
                    Log.e("Url", url_mhs);
                    Log.e("Respon Mhs", response);

                    JSONObject ob = new JSONObject(response);
                    scs = ob.getInt("success");

                    if (scs == 1) {
                        JSONArray products = ob.getJSONArray("field");

                        for (int i = 0; i < products.length(); i++) {
                            JSONObject c = products.getJSONObject(i);

                            // Storing each json item in variable
                            String id_user = c.getString("id_mahasiswa");
                            String nama = c.getString("nama");
                            /*String email = c.getString("email");*/

                            //buat sesi login
                            session.createLoginSession("2", id_user, nama);
                        }
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
            if(scs == 1){
                finish();
                Intent intent = new Intent(LoginActivity.this, MahasiswaActivity.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(LoginActivity.this, psn, Toast.LENGTH_SHORT).show();
            }
        }

    }

    private class loginStaff extends AsyncTask<Void,Void,String> {

        //variabel untuk tangkap data
        private int scs = 0;
        private String psn;
        private String email, password;

        public loginStaff(String email, String password){
            this.email = email;
            this.password = password;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Loading...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(Void... params) {

            try{
                //susun parameter
                HashMap<String,String> detail = new HashMap<>();
                detail.put("email", email);
                detail.put("pass", password);

                try {
                    //convert this HashMap to encodedUrl to send to php file
                    String dataToSend = hashMapToUrl(detail);
                    //make a Http request and send data to php file
                    String response = Request.post(url_stf, dataToSend);

                    //dapatkan respon
                    Log.e("Email", email);
                    Log.e("Pass", password);
                    Log.e("Url", url_stf);
                    Log.e("Respon Stf", response);

                    JSONObject ob = new JSONObject(response);
                    scs = ob.getInt("success");

                    if (scs == 1) {
                        JSONArray products = ob.getJSONArray("field");

                        for (int i = 0; i < products.length(); i++) {
                            JSONObject c = products.getJSONObject(i);

                            // Storing each json item in variable
                            String id_user = c.getString("id_staff_kampus");
                            String nama = c.getString("nama");
                            /*String email = c.getString("email");*/

                            //buat sesi login
                            session.createLoginSession("3", id_user, nama);
                        }
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
            if(scs == 1){
                finish();
                Intent intent = new Intent(LoginActivity.this, StaffActivity.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(LoginActivity.this, psn, Toast.LENGTH_SHORT).show();
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
