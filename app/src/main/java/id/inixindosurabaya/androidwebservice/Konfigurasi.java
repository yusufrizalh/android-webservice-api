package id.inixindosurabaya.androidwebservice;

public class Konfigurasi {
    // lokasi link dimana web services berada
    public static final String URL_ADD = "http://ionicschool.000webhostapp.com/json_pegawai/tambahPgw.php";
    public static final String URL_GET_ALL = "http://ionicschool.000webhostapp.com/json_pegawai/index.php";
    public static final String URL_GET_DETAIL = "http://ionicschool.000webhostapp.com/json_pegawai/tampilPgw.php?id=";
    public static final String URL_UPDATE = "http://ionicschool.000webhostapp.com/json_pegawai/updatePgw.php";
    public static final String URL_DELETE = "http://ionicschool.000webhostapp.com/json_pegawai/hapusPgw.php?id=";

    // key json yg muncul di browser
    public static final String KEY_PGW_ID = "id";
    public static final String KEY_PGW_NAMA = "name";
    public static final String KEY_PGW_JABATAN = "desg";
    public static final String KEY_PGW_GAJI = "salary";

    // tag JSON
    public static final String TAG_JSON_ARRAY = "result";
    public static final String TAG_JSON_ID = "id";
    public static final String TAG_JSON_NAMA = "name";
    public static final String TAG_JSON_JABATAN = "desg";
    public static final String TAG_JSON_GAJI = "salary";

    // variabel ID pegawai
    public static final String PGW_ID = "emp_id";
}
