package com.CK.QLTV.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.CK.QLTV.DAO.ChiTietDonDatDAO;
import com.CK.QLTV.DAO.DonDatDAO;
import com.CK.QLTV.DTO.ChiTietDonDatDTO;
import com.CK.QLTV.R;

public class AmountMenuActivity extends AppCompatActivity {

    TextInputLayout TXTL_amountmenu_SoLuong;
    Button BTN_amountmenu_DongY;
    int magiohang, maSach;
    DonDatDAO donDatDAO;
    ChiTietDonDatDAO chiTietDonDatDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.amount_menu_layout);

        //Lấy đối tượng view
        TXTL_amountmenu_SoLuong = (TextInputLayout)findViewById(R.id.txtl_amountmenu_SoLuong);
        BTN_amountmenu_DongY = (Button)findViewById(R.id.btn_amountmenu_DongY);

        //khởi tạo kết nối csdl
        donDatDAO = new DonDatDAO(this);
        chiTietDonDatDAO = new ChiTietDonDatDAO(this);

        //Lấy thông tin từ bàn được chọn
        Intent intent = getIntent();
        magiohang = intent.getIntExtra("magiohang",0);
        maSach = intent.getIntExtra("maSach",0);

        BTN_amountmenu_DongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateAmount()){
                    return;
                }

                int madondat = (int) donDatDAO.LayMaDonTheomagiohang(magiohang,"false");
                boolean ktra = chiTietDonDatDAO.KiemTraSachTonTai(madondat,maSach);
                if(ktra){
                    //update Số quyển đã chọn
                    int sluongcu = chiTietDonDatDAO.LaySLSachTheoMaDon(madondat,maSach);
                    int sluongmoi = Integer.parseInt(TXTL_amountmenu_SoLuong.getEditText().getText().toString());
                    int tongsl = sluongcu + sluongmoi;

                    ChiTietDonDatDTO chiTietDonDatDTO = new ChiTietDonDatDTO();
                    chiTietDonDatDTO.setMaSach(maSach);
                    chiTietDonDatDTO.setMaDonDat(madondat);
                    chiTietDonDatDTO.setSoLuong(tongsl);

                    boolean ktracapnhat = chiTietDonDatDAO.CapNhatSL(chiTietDonDatDTO);
                    if(ktracapnhat){
                        Toast.makeText(getApplicationContext(),getResources().getString(R.string.add_sucessful),Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getApplicationContext(),getResources().getString(R.string.add_failed),Toast.LENGTH_SHORT).show();
                    }
                }else {
                    //thêm Số quyển nếu chưa chọn sách này
                    int sluong = Integer.parseInt(TXTL_amountmenu_SoLuong.getEditText().getText().toString());
                    ChiTietDonDatDTO chiTietDonDatDTO = new ChiTietDonDatDTO();
                    chiTietDonDatDTO.setMaSach(maSach);
                    chiTietDonDatDTO.setMaDonDat(madondat);
                    chiTietDonDatDTO.setSoLuong(sluong);

                    boolean ktracapnhat = chiTietDonDatDAO.ThemChiTietDonDat(chiTietDonDatDTO);
                    if(ktracapnhat){
                        Toast.makeText(getApplicationContext(),getResources().getString(R.string.add_sucessful),Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getApplicationContext(),getResources().getString(R.string.add_failed),Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    //validate số lượng
    private boolean validateAmount(){
        String val = TXTL_amountmenu_SoLuong.getEditText().getText().toString().trim();
        if(val.isEmpty()){
            TXTL_amountmenu_SoLuong.setError(getResources().getString(R.string.not_empty));
            return false;
        }else if(!val.matches(("\\d+(?:\\.\\d+)?"))){
            TXTL_amountmenu_SoLuong.setError("Số lượng không hợp lệ");
            return false;
        }else {
            TXTL_amountmenu_SoLuong.setError(null);
            TXTL_amountmenu_SoLuong.setErrorEnabled(false);
            return true;
        }
    }
}