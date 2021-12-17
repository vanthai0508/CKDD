package com.CK.QLTV.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.CK.QLTV.DTO.KhachHangDTO;
import com.CK.QLTV.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class KhachHangDAO {

    SQLiteDatabase database;
    public KhachHangDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public long ThemKhachHang(KhachHangDTO khachHangDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_KHACHHANG_HOTENKH, khachHangDTO.getHOTENKH());
        contentValues.put(CreateDatabase.TBL_KHACHHANG_TENDN, khachHangDTO.getTENDN());
        contentValues.put(CreateDatabase.TBL_KHACHHANG_MATKHAU, khachHangDTO.getMATKHAU());
        contentValues.put(CreateDatabase.TBL_KHACHHANG_EMAIL, khachHangDTO.getEMAIL());
        contentValues.put(CreateDatabase.TBL_KHACHHANG_SDT, khachHangDTO.getSDT());
        contentValues.put(CreateDatabase.TBL_KHACHHANG_GIOITINH, khachHangDTO.getGIOITINH());
        contentValues.put(CreateDatabase.TBL_KHACHHANG_NGAYSINH, khachHangDTO.getNGAYSINH());
        contentValues.put(CreateDatabase.TBL_KHACHHANG_MAQUYEN, khachHangDTO.getMAQUYEN());

        long ktra = database.insert(CreateDatabase.TBL_KHACHHANG,null,contentValues);
        return ktra;
    }

    public long SuaThongTinKH(KhachHangDTO khachHangDTO, int makh){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_KHACHHANG_HOTENKH, khachHangDTO.getHOTENKH());
        contentValues.put(CreateDatabase.TBL_KHACHHANG_TENDN, khachHangDTO.getTENDN());
        contentValues.put(CreateDatabase.TBL_KHACHHANG_MATKHAU, khachHangDTO.getMATKHAU());
        contentValues.put(CreateDatabase.TBL_KHACHHANG_EMAIL, khachHangDTO.getEMAIL());
        contentValues.put(CreateDatabase.TBL_KHACHHANG_SDT, khachHangDTO.getSDT());
        contentValues.put(CreateDatabase.TBL_KHACHHANG_GIOITINH, khachHangDTO.getGIOITINH());
        contentValues.put(CreateDatabase.TBL_KHACHHANG_NGAYSINH, khachHangDTO.getNGAYSINH());
        contentValues.put(CreateDatabase.TBL_KHACHHANG_MAQUYEN, khachHangDTO.getMAQUYEN());

        long ktra = database.update(CreateDatabase.TBL_KHACHHANG,contentValues,
                CreateDatabase.TBL_KHACHHANG_MAKH+" = "+makh,null);
        return ktra;
    }

    public int KiemTraDN(String tenDN, String matKhau){
        String query = "SELECT * FROM " +CreateDatabase.TBL_KHACHHANG+ " WHERE "
                +CreateDatabase.TBL_KHACHHANG_TENDN +" = '"+ tenDN+"' AND "+CreateDatabase.TBL_KHACHHANG_MATKHAU +" = '" +matKhau +"'";
        int makh = 0;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            makh = cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_KHACHHANG_MAKH)) ;
            cursor.moveToNext();
        }
        return makh;
    }

    public boolean KtraTonTaiKH(){
        String query = "SELECT * FROM "+CreateDatabase.TBL_KHACHHANG;
        Cursor cursor =database.rawQuery(query,null);
        if(cursor.getCount() != 0){
            return true;
        }else {
            return false;
        }
    }

    public List<KhachHangDTO> LayDSKH(){
        List<KhachHangDTO> khachHangDTOS = new ArrayList<KhachHangDTO>();
        String query = "SELECT * FROM "+CreateDatabase.TBL_KHACHHANG;

        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            KhachHangDTO khachHangDTO = new KhachHangDTO();
            khachHangDTO.setHOTENKH(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_KHACHHANG_HOTENKH)));
            khachHangDTO.setEMAIL(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_KHACHHANG_EMAIL)));
            khachHangDTO.setGIOITINH(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_KHACHHANG_GIOITINH)));
            khachHangDTO.setNGAYSINH(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_KHACHHANG_NGAYSINH)));
            khachHangDTO.setSDT(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_KHACHHANG_SDT)));
            khachHangDTO.setTENDN(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_KHACHHANG_TENDN)));
            khachHangDTO.setMATKHAU(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_KHACHHANG_MATKHAU)));
            khachHangDTO.setMaKH(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_KHACHHANG_MAKH)));
            khachHangDTO.setMAQUYEN(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_KHACHHANG_MAQUYEN)));

            khachHangDTOS.add(khachHangDTO);
            cursor.moveToNext();
        }
        return khachHangDTOS;
    }

    public boolean XoaNV(int makh){
        long ktra = database.delete(CreateDatabase.TBL_KHACHHANG,CreateDatabase.TBL_KHACHHANG_MAKH+ " = " +makh
                ,null);
        if(ktra !=0 ){
            return true;
        }else {
            return false;
        }
    }

    public KhachHangDTO LayKHTheoMa(int makh){
        KhachHangDTO khachHangDTO = new KhachHangDTO();
        String query = "SELECT * FROM "+CreateDatabase.TBL_KHACHHANG+" WHERE "+CreateDatabase.TBL_KHACHHANG_MAKH+" = "+makh;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            khachHangDTO.setHOTENKH(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_KHACHHANG_HOTENKH)));
            khachHangDTO.setEMAIL(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_KHACHHANG_EMAIL)));
            khachHangDTO.setGIOITINH(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_KHACHHANG_GIOITINH)));
            khachHangDTO.setNGAYSINH(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_KHACHHANG_NGAYSINH)));
            khachHangDTO.setSDT(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_KHACHHANG_SDT)));
            khachHangDTO.setTENDN(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_KHACHHANG_TENDN)));
            khachHangDTO.setMATKHAU(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_KHACHHANG_MATKHAU)));
            khachHangDTO.setMaKH(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_KHACHHANG_MAKH)));
            khachHangDTO.setMAQUYEN(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_KHACHHANG_MAQUYEN)));

            cursor.moveToNext();
        }
        return khachHangDTO;
    }

    public int LayQuyenKH(int makh){
        int maquyen = 0;
        String query = "SELECT * FROM "+CreateDatabase.TBL_KHACHHANG+" WHERE "+CreateDatabase.TBL_KHACHHANG_MAKH+" = "+makh;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            maquyen = cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_KHACHHANG_MAQUYEN));

            cursor.moveToNext();
        }
        return maquyen;
    }


}
