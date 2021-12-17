package com.CK.QLTV.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.CK.QLTV.CustomAdapter.AdapterDisplayPayment;
import com.CK.QLTV.DAO.GioHangDAO;
import com.CK.QLTV.DAO.DonDatDAO;
import com.CK.QLTV.DAO.ThanhToanDAO;
import com.CK.QLTV.DTO.ThanhToanDTO;
import com.CK.QLTV.R;

import java.util.List;

public class PaymentActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView IMG_payment_backbtn;
    TextView txt_payment_TenGioHang, TXT_payment_NgayDat, TXT_payment_TongTien;
    Button BTN_payment_ThanhToan;
    GridView gvDisplayPayment;
    DonDatDAO donDatDAO;
    GioHangDAO gioHangDAO;
    ThanhToanDAO thanhToanDAO;
    List<ThanhToanDTO> thanhToanDTOS;
    AdapterDisplayPayment adapterDisplayPayment;
    long tongtien = 0;
    int magiohang, madondat;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_layout);

        //region thuộc tính view
        gvDisplayPayment= (GridView)findViewById(R.id.gvDisplayPayment);
        IMG_payment_backbtn = (ImageView)findViewById(R.id.img_payment_backbtn);
        txt_payment_TenGioHang = (TextView)findViewById(R.id.txt_payment_TenGioHang);
        TXT_payment_NgayDat = (TextView)findViewById(R.id.txt_payment_NgayDat);
        TXT_payment_TongTien = (TextView)findViewById(R.id.txt_payment_TongTien);
        BTN_payment_ThanhToan = (Button)findViewById(R.id.btn_payment_ThanhToan);
        //endregion

        //khởi tạo kết nối csdl
        donDatDAO = new DonDatDAO(this);
        thanhToanDAO = new ThanhToanDAO(this);
        gioHangDAO = new GioHangDAO(this);

        fragmentManager = getSupportFragmentManager();

        //lấy data từ mã giỏ hàng đc chọn
        Intent intent = getIntent();
        magiohang = intent.getIntExtra("magiohang",0);
        String tengiohang = intent.getStringExtra("tengiohang");
        String ngaydat = intent.getStringExtra("ngaydat");

        txt_payment_TenGioHang.setText(tengiohang);
        TXT_payment_NgayDat.setText(ngaydat);

        //ktra mã giỏ hàng tồn tại thì hiển thị
        if(magiohang !=0 ){
            HienThiThanhToan();

            for (int i=0;i<thanhToanDTOS.size();i++){
                int soluong = thanhToanDTOS.get(i).getSoLuong();
                int giatien = thanhToanDTOS.get(i).getGiaTien();

                tongtien += (soluong * giatien);
            }
            TXT_payment_TongTien.setText(String.valueOf(tongtien) +" VNĐ");
        }

        BTN_payment_ThanhToan.setOnClickListener(this);
        IMG_payment_backbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btn_payment_ThanhToan:
                boolean ktragiohang = gioHangDAO.CapNhatTinhTrangGioHang(magiohang,"false");
                boolean ktradondat = donDatDAO.UpdateTThaiDonTheomagiohang(magiohang,"true");
                boolean ktratongtien = donDatDAO.UpdateTongTienDonDat(madondat,String.valueOf(tongtien));
                if(ktragiohang && ktradondat && ktratongtien){
                    HienThiThanhToan();
                    TXT_payment_TongTien.setText("0 VNĐ");
                    Toast.makeText(getApplicationContext(),"Thanh toán thành công!",Toast.LENGTH_SHORT);
                }else{
                    Toast.makeText(getApplicationContext(),"Lỗi thanh toán!",Toast.LENGTH_SHORT);
                }
                break;

            case R.id.img_payment_backbtn:
                finish();
                break;
        }
    }

    //hiển thị data lên gridview
    private void HienThiThanhToan(){
        madondat = (int) donDatDAO.LayMaDonTheomagiohang(magiohang,"false");
        thanhToanDTOS = thanhToanDAO.LayDSSachTheoMaDon(madondat);
        adapterDisplayPayment = new AdapterDisplayPayment(this,R.layout.custom_layout_paymentmenu,thanhToanDTOS);
        gvDisplayPayment.setAdapter(adapterDisplayPayment);
        adapterDisplayPayment.notifyDataSetChanged();
    }
}