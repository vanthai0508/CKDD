package com.CK.QLTV.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.CK.QLTV.DTO.SachDTO;
import com.CK.QLTV.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class SachDAO {
    SQLiteDatabase database;
    public SachDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public boolean ThemSach(SachDTO sachDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_SACH_MALOAI, sachDTO.getMaLoai());
        contentValues.put(CreateDatabase.TBL_SACH_TENSACH, sachDTO.getTenSach());
        contentValues.put(CreateDatabase.TBL_SACH_GIATIEN, sachDTO.getGiaTien());
        contentValues.put(CreateDatabase.TBL_SACH_HINHANH, sachDTO.getHinhAnh());
        contentValues.put(CreateDatabase.TBL_SACH_TINHTRANG,"true");

        long ktra = database.insert(CreateDatabase.TBL_SACH,null,contentValues);

        if(ktra !=0){
            return true;
        }else {
            return false;
        }
    }

    public boolean XoaSach(int maSach){
        long ktra = database.delete(CreateDatabase.TBL_SACH,CreateDatabase.TBL_SACH_MASACH+ " = " +maSach
                ,null);
        if(ktra !=0 ){
            return true;
        }else {
            return false;
        }
    }

    public boolean SuaSach(SachDTO sachDTO, int maSach){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_SACH_MALOAI, sachDTO.getMaLoai());
        contentValues.put(CreateDatabase.TBL_SACH_TENSACH, sachDTO.getTenSach());
        contentValues.put(CreateDatabase.TBL_SACH_GIATIEN, sachDTO.getGiaTien());
        contentValues.put(CreateDatabase.TBL_SACH_HINHANH, sachDTO.getHinhAnh());
        contentValues.put(CreateDatabase.TBL_SACH_TINHTRANG, sachDTO.getTinhTrang());

        long ktra = database.update(CreateDatabase.TBL_SACH,contentValues,
                CreateDatabase.TBL_SACH_MASACH+" = "+maSach,null);
        if(ktra !=0){
            return true;
        }else {
            return false;
        }
    }

    public List<SachDTO> LayDSSachTheoLoai(int maloai){
        List<SachDTO> sachDTOList = new ArrayList<SachDTO>();
        String query = "SELECT * FROM " +CreateDatabase.TBL_SACH+ " WHERE " +CreateDatabase.TBL_SACH_MALOAI+ " = '" +maloai+ "' ";
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            SachDTO sachDTO = new SachDTO();
            sachDTO.setHinhAnh(cursor.getBlob(cursor.getColumnIndex(CreateDatabase.TBL_SACH_HINHANH)));
            sachDTO.setTenSach(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_SACH_TENSACH)));
            sachDTO.setMaLoai(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_SACH_MALOAI)));
            sachDTO.setMaSach(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_SACH_MASACH)));
            sachDTO.setGiaTien(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_SACH_GIATIEN)));
            sachDTO.setTinhTrang(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_SACH_TINHTRANG)));
            sachDTOList.add(sachDTO);

            cursor.moveToNext();
        }
        return sachDTOList;
    }

    public SachDTO LaySachTheoMa(int maSach){
        SachDTO sachDTO = new SachDTO();
        String query = "SELECT * FROM "+CreateDatabase.TBL_SACH+" WHERE "+CreateDatabase.TBL_SACH_MASACH+" = "+maSach;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            sachDTO.setHinhAnh(cursor.getBlob(cursor.getColumnIndex(CreateDatabase.TBL_SACH_HINHANH)));
            sachDTO.setTenSach(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_SACH_TENSACH)));
            sachDTO.setMaLoai(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_SACH_MALOAI)));
            sachDTO.setMaSach(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_SACH_MASACH)));
            sachDTO.setGiaTien(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_SACH_GIATIEN)));
            sachDTO.setTinhTrang(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_SACH_TINHTRANG)));

            cursor.moveToNext();
        }
        return sachDTO;
    }

}
