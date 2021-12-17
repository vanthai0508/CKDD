package com.CK.QLTV.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.CK.QLTV.DTO.GioHangDTO;
import com.CK.QLTV.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class GioHangDAO {
    SQLiteDatabase database;
    public GioHangDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    //Hàm thêm giỏ hàng mới
    public boolean ThemGioHang(String tengiohang){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_GIOHANG_TENGIOHANG,tengiohang);
        contentValues.put(CreateDatabase.TBL_GIOHANG_TINHTRANG,"false");

        long ktra = database.insert(CreateDatabase.TBL_GIOHANG,null,contentValues);
        if(ktra != 0){
            return true;
        }else {
            return false;
        }
    }

    //Hàm xóa bàn ăn theo mã
    public boolean XoaGioHangTheoMa(int magiohang){
        long ktra =database.delete(CreateDatabase.TBL_GIOHANG,CreateDatabase.TBL_GIOHANG_MAGIOHANG+" = "+magiohang,null);
        if(ktra != 0){
            return true;
        }else {
            return false;
        }
    }

    //Sửa tên bàn
    public boolean CapNhatTenGioHang(int magiohang, String tengiohang){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_GIOHANG_TENGIOHANG,tengiohang);

        long ktra = database.update(CreateDatabase.TBL_GIOHANG,contentValues,CreateDatabase.TBL_GIOHANG_MAGIOHANG+ " = '"+magiohang+"' ",null);
        if(ktra != 0){
            return true;
        }else {
            return false;
        }
    }

    //Hàm lấy ds các bàn ăn đổ vào gridview
    public List<GioHangDTO> LayTatCaGioHang(){
        List<GioHangDTO> gioHangDTOList = new ArrayList<GioHangDTO>();
        String query = "SELECT * FROM " +CreateDatabase.TBL_GIOHANG;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            GioHangDTO gioHangDTO = new GioHangDTO();
            gioHangDTO.setMagiohang(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_GIOHANG_MAGIOHANG)));
            gioHangDTO.setTengiohang(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_GIOHANG_TENGIOHANG)));

            gioHangDTOList.add(gioHangDTO);
            cursor.moveToNext();
        }
        return gioHangDTOList;
    }

    public String LayTinhTrangGioHangTheoMa(int magiohang){
        String tinhtrang="";
        String query = "SELECT * FROM "+CreateDatabase.TBL_GIOHANG + " WHERE " +CreateDatabase.TBL_GIOHANG_MAGIOHANG+ " = '" +magiohang+ "' ";
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            tinhtrang = cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_SACH_TINHTRANG));
            cursor.moveToNext();
        }

        return tinhtrang;
    }

    public boolean CapNhatTinhTrangGioHang(int magiohang, String tinhtrang){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_GIOHANG_TINHTRANG,tinhtrang);

        long ktra = database.update(CreateDatabase.TBL_GIOHANG,contentValues,CreateDatabase.TBL_GIOHANG_MAGIOHANG+ " = '"+magiohang+"' ",null);
        if(ktra != 0){
            return true;
        }else {
            return false;
        }
    }

    public String LayTenGioHangTheoMa(int magiohang){
        String tengiohang="";
        String query = "SELECT * FROM "+CreateDatabase.TBL_GIOHANG + " WHERE " +CreateDatabase.TBL_GIOHANG_MAGIOHANG+ " = '" +magiohang+ "' ";
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            tengiohang = cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_GIOHANG_TENGIOHANG));
            cursor.moveToNext();
        }

        return tengiohang;
    }
}
