package com.CK.QLTV.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.CK.QLTV.DAO.GioHangDAO;
import com.CK.QLTV.R;

public class EditGioHangActivity extends AppCompatActivity {

    TextInputLayout txtl_editgiohang_tengiohang;
    Button btn_editgiohang_SuaGioHang;
    GioHangDAO gioHangDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editgiohang_layout);

        //thuộc tính view
        txtl_editgiohang_tengiohang = (TextInputLayout)findViewById(R.id.txtl_editgiohang_tengiohang);
        btn_editgiohang_SuaGioHang = (Button)findViewById(R.id.btn_editgiohang_SuaGioHang);

        //khởi tạo dao mở kết nối csdl
        gioHangDAO = new GioHangDAO(this);
        int magiohang = getIntent().getIntExtra("magiohang",0); //lấy mã giỏ hàng từ giỏ hàng đc chọn

        btn_editgiohang_SuaGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tengiohang = txtl_editgiohang_tengiohang.getEditText().getText().toString();

                if(tengiohang != null || tengiohang.equals("")){
                    boolean ktra = gioHangDAO.CapNhatTenGioHang(magiohang,tengiohang);
                    Intent intent = new Intent();
                    intent.putExtra("ketquasua",ktra);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });
    }
}