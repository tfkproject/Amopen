package ta.jurais.amopen.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import ta.jurais.amopen.KompenDetailActivity;
import ta.jurais.amopen.R;
import ta.jurais.amopen.model.ItemAdmin;

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
                Intent intent = new Intent(context, KompenDetailActivity.class);
                intent.putExtra("key_id_mhs", items.get(position).getId());
                context.startActivity(intent);
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
