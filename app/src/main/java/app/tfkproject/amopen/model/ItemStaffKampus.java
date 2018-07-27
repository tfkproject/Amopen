package app.tfkproject.amopen.model;

/**
 * Created by taufik on 27/06/18.
 */

public class ItemStaffKampus {
    String id, nama, jabatan;

    public ItemStaffKampus(String id,
                           String nama,
                           String jabatan){
        this.id = id;
        this.nama = nama;
        this.jabatan = jabatan;
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
}
