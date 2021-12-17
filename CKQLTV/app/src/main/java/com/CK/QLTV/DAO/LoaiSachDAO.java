package com.CK.QLTV.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.CK.QLTV.DTO.LoaiSachDTO;
import com.CK.QLTV.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class LoaiSachDAO {

    SQLiteDatabase database;
    public LoaiSachDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public boolean ThemLoaiSach(LoaiSachDTO loaiSachDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_LOAISACH_TENLOAI, loaiSachDTO.getTenLoai());
        contentValues.put(CreateDatabase.TBL_LOAISACH_HINHANH, loaiSachDTO.getHinhAnh());
        long ktra = database.insert(CreateDatabase.TBL_LOAISACH,null,contentValues);

        if(ktra != 0){
            return true;
        }else {
            return false;
        }
    }

    public boolean XoaLoaiSach(int maloai){
        long ktra = database.delete(CreateDatabase.TBL_LOAISACH,CreateDatabase.TBL_LOAISACH_MALOAI+ " = " +maloai
                ,null);
        if(ktra !=0 ){
            return true;
        }else {
            return false;
        }
    }

    public boolean SuaLoaiSach(LoaiSachDTO loaiSachDTO, int maloai){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_LOAISACH_TENLOAI, loaiSachDTO.getTenLoai());
        contentValues.put(CreateDatabase.TBL_LOAISACH_HINHANH, loaiSachDTO.getHinhAnh());
        long ktra = database.update(CreateDatabase.TBL_LOAISACH,contentValues
                ,CreateDatabase.TBL_LOAISACH_MALOAI+" = "+maloai,null);
        if(ktra != 0){
            return true;
        }else {
            return false;
        }
    }

    public List<LoaiSachDTO> LayDSLoaiSach(){
        List<LoaiSachDTO> loaiSachDTOList = new ArrayList<LoaiSachDTO>();
        String query = "SELECT * FROM " +CreateDatabase.TBL_LOAISACH;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            LoaiSachDTO loaiSachDTO = new LoaiSachDTO();
            loaiSachDTO.setMaLoai(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_LOAISACH_MALOAI)));
            loaiSachDTO.setTenLoai(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_LOAISACH_TENLOAI)));
            loaiSachDTO.setHinhAnh(cursor.getBlob(cursor.getColumnIndex(CreateDatabase.TBL_LOAISACH_HINHANH)));
            loaiSachDTOList.add(loaiSachDTO);

            cursor.moveToNext();
        }
        return loaiSachDTOList;
    }

    public LoaiSachDTO LayLoaiSachTheoMa(int maloai){
        LoaiSachDTO loaiSachDTO = new LoaiSachDTO();
        String query = "SELECT * FROM " +CreateDatabase.TBL_LOAISACH+" WHERE "+CreateDatabase.TBL_LOAISACH_MALOAI+" = "+maloai;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            loaiSachDTO.setMaLoai(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_LOAISACH_MALOAI)));
            loaiSachDTO.setTenLoai(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_LOAISACH_TENLOAI)));
            loaiSachDTO.setHinhAnh(cursor.getBlob(cursor.getColumnIndex(CreateDatabase.TBL_LOAISACH_HINHANH)));

            cursor.moveToNext();
        }
        return loaiSachDTO;
    }

}
