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
import app.tfkproject.amopen.model.ItemAdmin;
import app.tfkproject.amopen.model.ItemStaffKampus;

/**
 * Created by taufik on 27/06/18.
 */

public class MahasiswaAdapter extends RecyclerView.Adapter<MahasiswaAdapter.ViewHolder> {

    List<ItemStaffKampus> items;
    Context context;
    private AdapterListener listener;

    public MahasiswaAdapter(Context context, List<ItemStaffKampus> items, AdapterListener listener) {
        this.context = context;
        this.items = items;
        this.listener = listener;
    }

    @Override
    public MahasiswaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_staff_kampus, parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.txtNama.setText(items.get(position).getNama());
        holder.txtJabatan.setText(items.get(position).getJabatan());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSelected(position, items.get(position).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        Button btnRekap;
        TextView txtNama, txtJabatan;
        CardView cardView;

        public ViewHolder(View itemView){
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            txtNama = (TextView) itemView.findViewById(R.id.txt_nama);
            txtJabatan = (TextView) itemView.findViewById(R.id.txt_jab);
        }
    }

    public interface AdapterListener {
        void onSelected(int position, String id_staff_kampus);
    }
}
