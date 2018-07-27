package app.tfkproject.amopen;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.tfkproject.amopen.adapter.AdminAdapter;
import app.tfkproject.amopen.model.ItemAdmin;
import app.tfkproject.amopen.util.SessionManager;

public class AdminActivity extends AppCompatActivity {

    List<ItemAdmin> items;
    AdminAdapter adapter;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    private ProgressDialog pDialog;
    String id_admin;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mahasiswa);

        getSupportActionBar().setTitle("Admin");

        session = new SessionManager(AdminActivity.this);
        //ambil data user
        HashMap<String, String> user = session.getUserDetails();
        id_admin = user.get(SessionManager.KEY_ID_USER);
        String nama = user.get(SessionManager.KEY_NM_USER);
        Toast.makeText(this, ""+nama, Toast.LENGTH_SHORT).show();

        //panggil RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        //set LayoutManager
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        items = new ArrayList<>();

        //new ambilData().execute();
        items.add(new ItemAdmin("1", "Jurais", "Selesai"));
        items.add(new ItemAdmin("2", "Juniarto", "Sedang kompen"));
        items.add(new ItemAdmin("3", "Sipaul Muammar", "Selesai"));
        items.add(new ItemAdmin("4", "Nanda Febriandi", "Sedang kompen"));

        //set adapter
        adapter = new AdminAdapter(AdminActivity.this, items);
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
}
