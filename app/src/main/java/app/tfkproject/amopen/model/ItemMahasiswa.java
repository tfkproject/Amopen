package app.tfkproject.amopen.model;

/**
 * Created by taufik on 27/06/18.
 */

public class ItemMahasiswa {
    String id, nama, kelas, status;

    public ItemMahasiswa(String id,
                         String nama,
                         String kelas,
                         String status){
        this.id = id;
        this.nama = nama;
        this.kelas = kelas;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getKelas() {
        return kelas;
    }

    public String getStatus() {
        return status;
    }
}
