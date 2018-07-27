package app.tfkproject.amopen.model;

/**
 * Created by taufik on 27/06/18.
 */

public class ItemListJob {
    String id, nama, jabatan, desk, ruang, quota;

    public ItemListJob(String id,
                       String nama,
                       String jabatan,
                       String desk,
                       String ruang,
                       String quota){
        this.id = id;
        this.nama = nama;
        this.jabatan = jabatan;
        this.desk = desk;
        this.ruang = ruang;
        this.quota = quota;
    }

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
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

    public String getQuota() {
        return quota;
    }
}
