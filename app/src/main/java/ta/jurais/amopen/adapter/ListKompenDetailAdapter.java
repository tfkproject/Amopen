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
import ta.jurais.amopen.model.ItemKompenDetail;
import ta.jurais.amopen.model.ItemListJob;

/**
 * Created by taufik on 27/06/18.
 */

public class ListKompenDetailAdapter extends RecyclerView.Adapter<ListKompenDetailAdapter.ViewHolder> {

    List<ItemKompenDetail> items;
    Context context;

    public ListKompenDetailAdapter(Context context, List<ItemKompenDetail> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public ListKompenDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_kompen_detail, parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.txtNamaMhs.setText(items.get(position).getNama());
        holder.txtSmtr.setText(items.get(position).getSemester());
        holder.txtKelas.setText(items.get(position).getKelas());
        holder.txtStatus.setText(items.get(position).getStatus());
        holder.txtNamaDsn.setText(items.get(position).getDosen());
        holder.txtJabatan.setText(items.get(position).getJabatan());
        holder.txtDesk.setText(items.get(position).getDesk());
        holder.txtRuang.setText(items.get(position).getRuang());
        holder.txtSisaJam.setText(items.get(position).getSisa_jam());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        Button btnAmbil;
        TextView txtNamaMhs, txtSmtr, txtKelas, txtStatus, txtNamaDsn, txtJabatan, txtDesk, txtRuang, txtSisaJam;

        public ViewHolder(View itemView){
            super(itemView);

            txtNamaMhs = (TextView) itemView.findViewById(R.id.txt_nm_mhs);
            txtSmtr = (TextView) itemView.findViewById(R.id.txt_smt);
            txtKelas = (TextView) itemView.findViewById(R.id.txt_kls);
            txtStatus = (TextView) itemView.findViewById(R.id.txt_status);
            txtNamaDsn = (TextView) itemView.findViewById(R.id.txt_nm_dsn);
            txtJabatan = (TextView) itemView.findViewById(R.id.txt_jab);
            txtDesk = (TextView) itemView.findViewById(R.id.txt_desk);
            txtRuang = (TextView) itemView.findViewById(R.id.txt_ruang);
            txtSisaJam = (TextView) itemView.findViewById(R.id.txt_sisa_jam);

        }
    }

}
