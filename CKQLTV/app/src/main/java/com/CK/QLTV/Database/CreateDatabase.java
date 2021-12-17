package com.CK.QLTV.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CreateDatabase extends SQLiteOpenHelper {

    public static String TBL_KHACHHANG = "KHACHHANG";
    public static String TBL_SACH = "SACH";
    public static String TBL_LOAISACH = "LOAISACH";
    public static String TBL_GIOHANG = "GIOHANG";
    public static String TBL_DONDAT = "DONDAT";
    public static String TBL_CHITIETDONDAT = "CHITIETDONDAT";
    public static String TBL_QUYEN = "QUYEN";

    //Bảng khách hàng
    public static String TBL_KHACHHANG_MAKH = "MAKH";
    public static String TBL_KHACHHANG_HOTENKH = "HOTENKH";
    public static String TBL_KHACHHANG_TENDN = "TENDN";
    public static String TBL_KHACHHANG_MATKHAU = "MATKHAU";
    public static String TBL_KHACHHANG_EMAIL = "EMAIL";
    public static String TBL_KHACHHANG_SDT = "SDT";
    public static String TBL_KHACHHANG_GIOITINH = "GIOITINH";
    public static String TBL_KHACHHANG_NGAYSINH = "NGAYSINH";
    public static String TBL_KHACHHANG_MAQUYEN= "MAQUYEN";

    //Bảng quyền
    public static String TBL_QUYEN_MAQUYEN = "MAQUYEN";
    public static String TBL_QUYEN_TENQUYEN = "TENQUYEN";

    //Bảng sách
    public static String TBL_SACH_MASACH = "MASACH";
    public static String TBL_SACH_TENSACH = "TENSACH";
    public static String TBL_SACH_GIATIEN = "GIATIEN";
    public static String TBL_SACH_TINHTRANG = "TINHTRANG";
    public static String TBL_SACH_HINHANH = "HINHANH";
    public static String TBL_SACH_MALOAI = "MALOAI";

    //Bảng loại sách
    public static String TBL_LOAISACH_MALOAI = "MALOAI";
    public static String TBL_LOAISACH_TENLOAI = "TENLOAI";
    public static String TBL_LOAISACH_HINHANH = "HINHANH";

    //Bảng bàn
    public static String TBL_GIOHANG_MAGIOHANG = "MAGIOHANG";
    public static String TBL_GIOHANG_TENGIOHANG = "TENGIOHANG";
    public static String TBL_GIOHANG_TINHTRANG = "TINHTRANG";

    //Bảng đơn đặt
    public static String TBL_DONDAT_MADONDAT = "MADONDAT";
    public static String TBL_DONDAT_MAKH = "MAKH";
    public static String TBL_DONDAT_NGAYDAT = "NGAYDAT";
    public static String TBL_DONDAT_TINHTRANG = "TINHTRANG";
    public static String TBL_DONDAT_TONGTIEN = "TONGTIEN";
    public static String TBL_DONDAT_MAGIOHANG = "MAGIOHANG";

    //Bảng chi tiết đơn đặt
    public static String TBL_CHITIETDONDAT_MADONDAT = "MADONDAT";
    public static String TBL_CHITIETDONDAT_MASACH = "MASACH";
    public static String TBL_CHITIETDONDAT_SOLUONG = "SOLUONG";


    public CreateDatabase(Context context) {
        super(context, "QLTV", null, 1);
    }

    //thực hiện tạo bảng
    @Override
    public void onCreate(SQLiteDatabase db) {
        String tblKHACHHANG = "CREATE TABLE " +TBL_KHACHHANG+ " ( " +TBL_KHACHHANG_MAKH+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +TBL_KHACHHANG_HOTENKH+ " TEXT, " +TBL_KHACHHANG_TENDN+ " TEXT, " +TBL_KHACHHANG_MATKHAU+ " TEXT, " +TBL_KHACHHANG_EMAIL+ " TEXT, "
                +TBL_KHACHHANG_SDT+ " TEXT, " +TBL_KHACHHANG_GIOITINH+ " TEXT, " +TBL_KHACHHANG_NGAYSINH+ " TEXT , "+TBL_KHACHHANG_MAQUYEN+" INTEGER)";

        String tblQUYEN = "CREATE TABLE " +TBL_QUYEN+ " ( " +TBL_QUYEN_MAQUYEN+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +TBL_QUYEN_TENQUYEN+ " TEXT)" ;

        String tblGIOHANG = "CREATE TABLE " +TBL_GIOHANG+ " ( " +TBL_GIOHANG_MAGIOHANG+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +TBL_GIOHANG_TENGIOHANG+ " TEXT, " +TBL_GIOHANG_TINHTRANG+ " TEXT )";

        String tblSACH = "CREATE TABLE " +TBL_SACH+ " ( " +TBL_SACH_MASACH+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +TBL_SACH_TENSACH+ " TEXT, " +TBL_SACH_GIATIEN+ " TEXT, " +TBL_SACH_TINHTRANG+ " TEXT, "
                +TBL_SACH_HINHANH+ " BLOB, "+TBL_SACH_MALOAI+ " INTEGER )";

        String tblLOAISACH = "CREATE TABLE " +TBL_LOAISACH+ " ( " +TBL_LOAISACH_MALOAI+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +TBL_LOAISACH_HINHANH+ " BLOB, " +TBL_LOAISACH_TENLOAI+ " TEXT)" ;

        String tblDONDAT = "CREATE TABLE " +TBL_DONDAT+ " ( " +TBL_DONDAT_MADONDAT+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
                +TBL_DONDAT_MAGIOHANG+ " INTEGER, " +TBL_DONDAT_MAKH+ " INTEGER, " +TBL_DONDAT_NGAYDAT+ " TEXT, "+TBL_DONDAT_TONGTIEN+" TEXT,"
                +TBL_DONDAT_TINHTRANG+ " TEXT )" ;

        String tblCHITIETDONDAT = "CREATE TABLE " +TBL_CHITIETDONDAT+ " ( " +TBL_CHITIETDONDAT_MADONDAT+ " INTEGER, "
                +TBL_CHITIETDONDAT_MASACH+ " INTEGER, " +TBL_CHITIETDONDAT_SOLUONG+ " INTEGER, "
                + " PRIMARY KEY ( " +TBL_CHITIETDONDAT_MADONDAT+ "," +TBL_CHITIETDONDAT_MASACH+ "))";

        db.execSQL(tblKHACHHANG);
        db.execSQL(tblQUYEN);
        db.execSQL(tblGIOHANG);
        db.execSQL(tblSACH);
        db.execSQL(tblLOAISACH);
        db.execSQL(tblDONDAT);
        db.execSQL(tblCHITIETDONDAT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //mở kết nối csdl
    public SQLiteDatabase open(){
        return this.getWritableDatabase();
    }
}
