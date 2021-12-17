package com.CK.QLTV.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.CK.QLTV.CustomAdapter.AdapterDisplayPayment;
import com.CK.QLTV.DAO.GioHangDAO;
import com.CK.QLTV.DAO.KhachHangDAO;
import com.CK.QLTV.DAO.ThanhToanDAO;
import com.CK.QLTV.DTO.KhachHangDTO;
import com.CK.QLTV.DTO.ThanhToanDTO;
import com.CK.QLTV.R;

import java.util.List;

public class DetailStatisticActivity extends AppCompatActivity {

    ImageView img_detailstatistic_backbtn;
    TextView txt_detailstatistic_MaDon,txt_detailstatistic_NgayDat,txt_detailstatistic_TenGioHang
            ,txt_detailstatistic_TenKH,txt_detailstatistic_TongTien;
    GridView gvDetailStatistic;
    int madon, makh, magiohang;
    String ngaydat, tongtien;
    KhachHangDAO khachHangDAO;
    GioHangDAO gioHangDAO;
    List<ThanhToanDTO> thanhToanDTOList;
    ThanhToanDAO thanhToanDAO;
    AdapterDisplayPayment adapterDisplayPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailstatistic_layout);

        //Lấy thông tin từ display statistic
        Intent intent = getIntent();
        madon = intent.getIntExtra("madon",0);
        makh = intent.getIntExtra("makh",0);
        magiohang = intent.getIntExtra("magiohang",0);
        ngaydat = intent.getStringExtra("ngaydat");
        tongtien = intent.getStringExtra("tongtien");

        //region Thuộc tính bên view
        img_detailstatistic_backbtn = (ImageView)findViewById(R.id.img_detailstatistic_backbtn);
        txt_detailstatistic_MaDon = (TextView)findViewById(R.id.txt_detailstatistic_MaDon);
        txt_detailstatistic_NgayDat = (TextView)findViewById(R.id.txt_detailstatistic_NgayDat);
        txt_detailstatistic_TenGioHang = (TextView)findViewById(R.id.txt_detailstatistic_TenGioHang);
        txt_detailstatistic_TenKH = (TextView)findViewById(R.id.txt_detailstatistic_TenKH);
        txt_detailstatistic_TongTien = (TextView)findViewById(R.id.txt_detailstatistic_TongTien);
        gvDetailStatistic = (GridView)findViewById(R.id.gvDetailStatistic);
        //endregion

        //khởi tạo lớp dao mở kết nối csdl
        khachHangDAO = new KhachHangDAO(this);
        gioHangDAO = new GioHangDAO(this);
        thanhToanDAO = new ThanhToanDAO(this);

        //chỉ hiển thị nếu lấy đc mã đơn đc chọn
        if (madon !=0){
            txt_detailstatistic_MaDon.setText("Mã đơn: "+madon);
            txt_detailstatistic_NgayDat.setText(ngaydat);
            txt_detailstatistic_TongTien.setText(tongtien+" VNĐ");

            KhachHangDTO khachHangDTO = khachHangDAO.LayKHTheoMa(makh);
            txt_detailstatistic_TenKH.setText(khachHangDTO.getHOTENKH());
            txt_detailstatistic_TenGioHang.setText(gioHangDAO.LayTenGioHangTheoMa(magiohang));

            HienThiDSCTDD();
        }

        img_detailstatistic_backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });
    }

    private void HienThiDSCTDD(){
        thanhToanDTOList = thanhToanDAO.LayDSSachTheoMaDon(madon);
        adapterDisplayPayment = new AdapterDisplayPayment(this,R.layout.custom_layout_paymentmenu,thanhToanDTOList);
        gvDetailStatistic.setAdapter(adapterDisplayPayment);
        adapterDisplayPayment.notifyDataSetChanged();
    }
}