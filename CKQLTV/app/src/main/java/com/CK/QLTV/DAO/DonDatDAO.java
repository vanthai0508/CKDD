package com.CK.QLTV.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.CK.QLTV.DTO.DonDatDTO;
import com.CK.QLTV.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

public class DonDatDAO {

    SQLiteDatabase database;
    public DonDatDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        database = createDatabase.open();
    }

    public long ThemDonDat(DonDatDTO donDatDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_DONDAT_MAGIOHANG,donDatDTO.getMagiohang());
        contentValues.put(CreateDatabase.TBL_DONDAT_MAKH,donDatDTO.getMaKH());
        contentValues.put(CreateDatabase.TBL_DONDAT_NGAYDAT,donDatDTO.getNgayDat());
        contentValues.put(CreateDatabase.TBL_DONDAT_TINHTRANG,donDatDTO.getTinhTrang());
        contentValues.put(CreateDatabase.TBL_DONDAT_TONGTIEN,donDatDTO.getTongTien());

        long madondat = database.insert(CreateDatabase.TBL_DONDAT,null,contentValues);

        return madondat;
    }

    public List<DonDatDTO> LayDSDonDat(){
        List<DonDatDTO> donDatDTOS = new ArrayList<DonDatDTO>();
        String query = "SELECT * FROM "+CreateDatabase.TBL_DONDAT;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            DonDatDTO donDatDTO = new DonDatDTO();
            donDatDTO.setMaDonDat(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_MADONDAT)));
            donDatDTO.setMagiohang(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_MAGIOHANG)));
            donDatDTO.setTongTien(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_TONGTIEN)));
            donDatDTO.setTinhTrang(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_TINHTRANG)));
            donDatDTO.setNgayDat(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_NGAYDAT)));
            donDatDTO.setMaKH(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_MAKH)));
            donDatDTOS.add(donDatDTO);

            cursor.moveToNext();
        }
        return donDatDTOS;
    }

    public List<DonDatDTO> LayDSDonDatNgay(String ngaythang){
        List<DonDatDTO> donDatDTOS = new ArrayList<DonDatDTO>();
        String query = "SELECT * FROM "+CreateDatabase.TBL_DONDAT+" WHERE "+CreateDatabase.TBL_DONDAT_NGAYDAT+" like '"+ngaythang+"'";
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            DonDatDTO donDatDTO = new DonDatDTO();
            donDatDTO.setMaDonDat(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_MADONDAT)));
            donDatDTO.setMagiohang(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_MAGIOHANG)));
            donDatDTO.setTongTien(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_TONGTIEN)));
            donDatDTO.setTinhTrang(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_TINHTRANG)));
            donDatDTO.setNgayDat(cursor.getString(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_NGAYDAT)));
            donDatDTO.setMaKH(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_MAKH)));
            donDatDTOS.add(donDatDTO);

            cursor.moveToNext();
        }
        return donDatDTOS;
    }

    public long LayMaDonTheomagiohang(int magiohang, String tinhtrang){
        String query = "SELECT * FROM " +CreateDatabase.TBL_DONDAT+ " WHERE " +CreateDatabase.TBL_DONDAT_MAGIOHANG+ " = '" +magiohang+ "' AND "
                +CreateDatabase.TBL_DONDAT_TINHTRANG+ " = '" +tinhtrang+ "' ";
        long mamuasach = 0;
        Cursor cursor = database.rawQuery(query,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            mamuasach = cursor.getInt(cursor.getColumnIndex(CreateDatabase.TBL_DONDAT_MADONDAT));

            cursor.moveToNext();
        }
        return mamuasach;
    }

    public boolean UpdateTongTienDonDat(int madondat,String tongtien){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_DONDAT_TONGTIEN,tongtien);
        long ktra  = database.update(CreateDatabase.TBL_DONDAT,contentValues,
                CreateDatabase.TBL_DONDAT_MADONDAT+" = "+madondat,null);
        if(ktra != 0){
            return true;
        }else{
            return false;
        }
    }

    public boolean UpdateTThaiDonTheomagiohang(int magiohang,String tinhtrang){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TBL_DONDAT_TINHTRANG,tinhtrang);
        long ktra = database.update(CreateDatabase.TBL_DONDAT,contentValues,CreateDatabase.TBL_DONDAT_MAGIOHANG+
                " = '"+magiohang+"'",null);
        if(ktra !=0){
            return true;
        }else {
            return false;
        }
    }

}
