package ta.jurais.amopen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
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

import ta.jurais.amopen.adapter.MahasiswaAdapter;
import ta.jurais.amopen.model.ItemStaffKampus;
import ta.jurais.amopen.util.Config;
import ta.jurais.amopen.util.Request;
import ta.jurais.amopen.util.SessionManager;

public class MahasiswaActivity extends AppCompatActivity {

    List<ItemStaffKampus> items;
    MahasiswaAdapter adapter;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    private ProgressDialog pDialog;
    String id_mahasiswa;
    SessionManager session;
    private static String url = Config.HOST+"list_job.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mahasiswa);

        getSupportActionBar().setTitle("Open Job Kompensasi");

        session = new SessionManager(MahasiswaActivity.this);
        //ambil data user
        HashMap<String, String> user = session.getUserDetails();
        id_mahasiswa = user.get(SessionManager.KEY_ID_USER);
        String nama = user.get(SessionManager.KEY_NM_USER);
        Toast.makeText(this, ""+nama, Toast.LENGTH_SHORT).show();

        //panggil RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        //set LayoutManager
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        items = new ArrayList<>();

        new dapatkanData().execute();

        //set adapter
        adapter = new MahasiswaAdapter(MahasiswaActivity.this, items, new MahasiswaAdapter.AdapterListener() {
            @Override
            public void onSelected(int position, String id_staff_kampus) {
                Intent intent = new Intent(MahasiswaActivity.this, ListJobActivity.class);
                intent.putExtra("key_id_staff_kampus", id_staff_kampus);
                intent.putExtra("key_id_mahasiswa", id_mahasiswa);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            session.logoutUser();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class dapatkanData extends AsyncTask<Void,Void,String> {

        //variabel untuk tangkap data
        private int scs = 0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MahasiswaActivity.this);
            pDialog.setMessage("Memuat data...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(Void... params) {

            try{
                //susun parameter
                HashMap<String,String> detail = new HashMap<>();

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
                            String id = c.getString("id_staff_kampus");
                            String nama = c.getString("nama");
                            String jabatan = c.getString("jabatan");


                            items.add(new ItemStaffKampus(id, nama, jabatan));

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
