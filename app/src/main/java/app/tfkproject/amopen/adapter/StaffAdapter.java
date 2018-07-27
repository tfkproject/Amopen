package app.tfkproject.amopen.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import app.tfkproject.amopen.ListJobActivity;
import app.tfkproject.amopen.R;
import app.tfkproject.amopen.StaffJobDetailActivity;
import app.tfkproject.amopen.model.ItemMahasiswa;
import app.tfkproject.amopen.model.ItemStaffKampus;

/**
 * Created by taufik on 27/06/18.
 */

public class StaffAdapter extends RecyclerView.Adapter<StaffAdapter.ViewHolder> {

    List<ItemMahasiswa> items;
    Context context;

    public StaffAdapter(Context context, List<ItemMahasiswa> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public StaffAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mahasiswa, parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.txtNama.setText(items.get(position).getNama());
        holder.txtKelas.setText(items.get(position).getKelas());
        holder.txtStatus.setText(items.get(position).getStatus());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, StaffJobDetailActivity.class);
                intent.putExtra("key_id_kompen", items.get(position).getId());
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
        TextView txtNama, txtKelas, txtStatus;
        CardView cardView;

        public ViewHolder(View itemView){
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            txtNama = (TextView) itemView.findViewById(R.id.txt_nama);
            txtKelas = (TextView) itemView.findViewById(R.id.txt_kelas);
            txtStatus = (TextView) itemView.findViewById(R.id.txt_status);
        }
    }
}
