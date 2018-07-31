package ta.jurais.amopen.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import ta.jurais.amopen.R;
import ta.jurais.amopen.model.ItemListJob;

/**
 * Created by taufik on 27/06/18.
 */

public class ListJobAdapter extends RecyclerView.Adapter<ListJobAdapter.ViewHolder> {

    List<ItemListJob> items;
    Context context;
    private AdapterListener listener;

    public ListJobAdapter(Context context, List<ItemListJob> items, AdapterListener listener) {
        this.context = context;
        this.items = items;
        this.listener = listener;
    }

    @Override
    public ListJobAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_job, parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.txtNama.setText(items.get(position).getNama());
        holder.txtJabatan.setText(items.get(position).getJabatan());
        holder.txtDesk.setText(items.get(position).getDesk());
        holder.txtRuang.setText(items.get(position).getRuang());
        holder.txtQuota.setText(items.get(position).getQuota() + " Orang");

        holder.btnAmbil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onButtonClick(position, items.get(position).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        Button btnAmbil;
        TextView txtNama, txtJabatan, txtDesk, txtRuang, txtQuota;
        Button cardView;

        public ViewHolder(View itemView){
            super(itemView);
            btnAmbil = (Button) itemView.findViewById(R.id.btn_ambil);
            txtNama = (TextView) itemView.findViewById(R.id.txt_nama);
            txtJabatan = (TextView) itemView.findViewById(R.id.txt_jab);
            txtDesk = (TextView) itemView.findViewById(R.id.txt_desk);
            txtRuang = (TextView) itemView.findViewById(R.id.txt_ruang);
            txtQuota = (TextView) itemView.findViewById(R.id.txt_quota);
        }
    }

    public interface AdapterListener {
        void onButtonClick(int position, String id_pekerjaan);
    }
}
