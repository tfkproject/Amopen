package ta.jurais.amopen.model;

/**
 * Created by taufik on 27/06/18.
 */

public class ItemAdmin {
    String id, nama, status, keterangan;

    public ItemAdmin(String id,
                     String nama,
                     String status){
        this.id = id;
        this.nama = nama;
        this.status = status;
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

    public String getKeterangan() {
        return keterangan;
    }
}
