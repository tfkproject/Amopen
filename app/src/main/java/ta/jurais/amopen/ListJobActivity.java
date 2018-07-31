package ta.jurais.amopen;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
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

import ta.jurais.amopen.adapter.ListJobAdapter;
import ta.jurais.amopen.model.ItemListJob;
import ta.jurais.amopen.util.Config;
import ta.jurais.amopen.util.Request;

public class ListJobActivity extends AppCompatActivity {

    List<ItemListJob> items;
    ListJobAdapter adapter;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    private ProgressDialog pDialog;
    private static String url = Config.HOST+"list_job_detail.php";
    private static String url_post = Config.HOST+"ambil_job.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_job);

        String id_staff_kampus = getIntent().getStringExtra("key_id_staff_kampus");
        final String id_mahasiswa = getIntent().getStringExtra("key_id_mahasiswa");

        //buat tombol back di ActionBar
        getSupportActionBar().setTitle("Deskripsi Job");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //panggil RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        //set LayoutManager
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        items = new ArrayList<>();

        new dapatkanData(id_staff_kampus).execute();

        //set adapter
        adapter = new ListJobAdapter(ListJobActivity.this, items, new ListJobAdapter.AdapterListener() {
            @Override
            public void onButtonClick(int position, String id_pekerjaan) {
                //post kompen
                new postData(id_mahasiswa, id_pekerjaan,"Sedang dikerjakan").execute();
                //Toast.makeText(ListJobActivity.this, "Silakan kerjakan kompen anda kepada staff terkait", Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(adapter);



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

        private String id_staff_kampus;

        public dapatkanData(String id_staff_kampus){
            this.id_staff_kampus = id_staff_kampus;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ListJobActivity.this);
            pDialog.setMessage("Memuat data...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(Void... params) {

            try{
                //susun parameter
                HashMap<String,String> detail = new HashMap<>();
                detail.put("id_staff_kampus", id_staff_kampus);

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
                            String id = c.getString("id_pekerjaan");
                            String nama = c.getString("nama");
                            String jabatan = c.getString("jabatan");
                            String pekerjaan = c.getString("pekerjaan");
                            String ruangan = c.getString("ruangan");
                            String quota = c.getString("quota");


                            items.add(new ItemListJob(id, nama, jabatan, pekerjaan, ruangan, quota));

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
            adapter.notifyDataSetChanged();
            pDialog.dismiss();

        }

    }

    private class postData extends AsyncTask<Void,Void,String> {

        //variabel untuk tangkap data
        private int scs = 0;
        private String psn;
        private String id_mahasiswa, id_pekerjaan, status;

        public postData(String id_mahasiswa,
                        String id_pekerjaan,
                        String status){
            this.id_mahasiswa = id_mahasiswa;
            this.id_pekerjaan = id_pekerjaan;
            this.status = status;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ListJobActivity.this);
            pDialog.setMessage("Posting data...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(Void... params) {

            try{
                //susun parameter
                HashMap<String,String> detail = new HashMap<>();
                detail.put("id_mahasiswa", id_mahasiswa);
                detail.put("id_pekerjaan", id_pekerjaan);
                detail.put("status", status);

                try {
                    //convert this HashMap to encodedUrl to send to php file
                    String dataToSend = hashMapToUrl(detail);
                    //make a Http request and send data to php file
                    String response = Request.post(url_post,dataToSend);

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
                Toast.makeText(ListJobActivity.this, psn, Toast.LENGTH_SHORT).show();
            } else {
                // no data found
                Toast.makeText(ListJobActivity.this, psn, Toast.LENGTH_SHORT).show();
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
