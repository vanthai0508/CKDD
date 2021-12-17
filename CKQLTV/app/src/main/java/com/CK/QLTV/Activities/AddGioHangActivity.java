package com.CK.QLTV.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.CK.QLTV.DAO.GioHangDAO;
import com.CK.QLTV.R;

public class AddGioHangActivity extends AppCompatActivity {

    TextInputLayout txtl_addgiohang_tengiohang;
    Button btn_addtable_TaoGioHang;
    GioHangDAO gioHangDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addgiohang_layout);

        //region Lấy đối tượng trong view
        txtl_addgiohang_tengiohang = (TextInputLayout)findViewById(R.id.txtl_addgiohang_tengiohang);
        btn_addtable_TaoGioHang = (Button)findViewById(R.id.btn_addtable_TaoGioHang);

        gioHangDAO = new GioHangDAO(this);
        btn_addtable_TaoGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sTenGioHang = txtl_addgiohang_tengiohang.getEditText().getText().toString();
                if(sTenGioHang != null || sTenGioHang.equals("")){
                    boolean ktra = gioHangDAO.ThemGioHang(sTenGioHang);
                    //trả về result cho displaytable
                    Intent intent = new Intent();
                    intent.putExtra("ketquathem",ktra);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });
    }

    //validate dữ liệu
    private boolean validateName(){
        String val = txtl_addgiohang_tengiohang.getEditText().getText().toString().trim();
        if(val.isEmpty()){
            txtl_addgiohang_tengiohang.setError(getResources().getString(R.string.not_empty));
            return false;
        }else {
            txtl_addgiohang_tengiohang.setError(null);
            txtl_addgiohang_tengiohang.setErrorEnabled(false);
            return true;
        }
    }
}