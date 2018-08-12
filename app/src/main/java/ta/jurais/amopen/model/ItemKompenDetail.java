package ta.jurais.amopen.model;

/**
 * Created by user on 8/12/18.
 */

public class ItemKompenDetail {
    String id, nama, status, dosen, jabatan, desk, ruang, sisa_jam;

    public ItemKompenDetail(String id,
                       String nama,
                       String status,
                       String dosen,
                       String jabatan,
                       String desk,
                       String ruang,
                       String sisa_jam){
        this.id = id;
        this.nama = nama;
        this.status = status;
        this.dosen = dosen;
        this.jabatan = jabatan;
        this.desk = desk;
        this.ruang = ruang;
        this.sisa_jam = sisa_jam;
    }

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getStatus() {
        return status;
    }

    public String getDosen() {
        return dosen;
    }

    public String getJabatan() {
        return jabatan;
    }

    public String getDesk() {
        return desk;
    }

    public String getRuang() {
        return ruang;
    }

    public String getSisa_jam() {
        return sisa_jam;
    }
}
