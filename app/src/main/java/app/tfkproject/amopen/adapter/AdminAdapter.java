package app.tfkproject.amopen.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import app.tfkproject.amopen.R;
import app.tfkproject.amopen.model.ItemAdmin;

/**
 * Created by taufik on 27/06/18.
 */

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.ViewHolder> {

    List<ItemAdmin> items;
    Context context;

    public AdminAdapter(Context context, List<ItemAdmin> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public AdminAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin, parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.txtNama.setText(items.get(position).getNama());
        holder.txtStatus.setText(items.get(position).getStatus());
        //holder.txtKeterangan.setText(items.get(position).getKeterangan());
        holder.btnRekap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Sedang dikerjakan", Toast.LENGTH_SHORT).show();
                /*Intent intent = new Intent(context, DataDetailActivity.class);
                intent.putExtra("key_nama", items.get(position).getNama());
                intent.putExtra("key_tgl_lahir", items.get(position).getTgl_lahir());
                intent.putExtra("key_jk", items.get(position).getJk());
                intent.putExtra("key_pddkn", items.get(position).getPddkn());
                intent.putExtra("key_n_ortu", items.get(position).getN_ortu());
                intent.putExtra("key_alamat", items.get(position).getAlamat());
                intent.putExtra("key_tgl_periksa", items.get(position).getTgl_periksa());
                intent.putExtra("key_tujuan", items.get(position).getTujuan());
                intent.putExtra("key_kpsp_ke", items.get(position).getKpsp_ke());
                intent.putExtra("key_hasil", items.get(position).getHasil());
                context.startActivity(intent);*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        Button btnRekap;
        TextView txtNama, txtStatus, txtKeterangan;

        public ViewHolder(View itemView){
            super(itemView);
            txtNama = (TextView) itemView.findViewById(R.id.txt_nama);
            txtStatus = (TextView) itemView.findViewById(R.id.txt_status);
            txtKeterangan = (TextView) itemView.findViewById(R.id.txt_keterangan);
            btnRekap = (Button) itemView.findViewById(R.id.btn_rekap);
        }
    }
}
