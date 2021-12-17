package com.CK.QLTV.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.CK.QLTV.DAO.KhachHangDAO;
import com.CK.QLTV.DTO.KhachHangDTO;
import com.CK.QLTV.R;

import java.util.Calendar;
import java.util.regex.Pattern;

public class AddKhActivity extends AppCompatActivity implements View.OnClickListener{

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=\\S+$)" +
                    ".{6,}" +
                    "$");

    ImageView IMG_addkh_back;
    TextView TXT_addkh_title;
    TextInputLayout TXTL_addkh_HoVaTen, TXTL_addkh_TenDN, TXTL_addkh_Email, TXTL_addkh_SDT, TXTL_addkh_MatKhau;
    RadioGroup RG_addkh_GioiTinh,rg_addkh_Quyen;
    RadioButton RD_addkh_Nam,RD_addkh_Nu,RD_addkh_Khac,rd_addkh_QuanLy,rd_addkh_KhachHang;
    DatePicker DT_addkh_NgaySinh;
    Button btn_addkh_ThemKH;
    KhachHangDAO khachHangDAO;
    String hoTen,tenDN,eMail,sDT,matKhau,gioiTinh,ngaySinh;
    int makh = 0,quyen = 0;
    long ktra = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addkh_layout);

        //region Lấy đối tượng trong view
        TXT_addkh_title = (TextView)findViewById(R.id.txt_addkh_title);
        IMG_addkh_back = (ImageView)findViewById(R.id.img_addkh_back);
        TXTL_addkh_HoVaTen = (TextInputLayout)findViewById(R.id.txtl_addkh_HoVaTen);
        TXTL_addkh_TenDN = (TextInputLayout)findViewById(R.id.txtl_addkh_TenDN);
        TXTL_addkh_Email = (TextInputLayout)findViewById(R.id.txtl_addkh_Email);
        TXTL_addkh_SDT = (TextInputLayout)findViewById(R.id.txtl_addkh_SDT);
        TXTL_addkh_MatKhau = (TextInputLayout)findViewById(R.id.txtl_addkh_MatKhau);
        RG_addkh_GioiTinh = (RadioGroup)findViewById(R.id.rg_addkh_GioiTinh);
        rg_addkh_Quyen = (RadioGroup)findViewById(R.id.rg_addkh_Quyen);
        RD_addkh_Nam = (RadioButton)findViewById(R.id.rd_addkh_Nam);
        RD_addkh_Nu = (RadioButton)findViewById(R.id.rd_addkh_Nu);
        RD_addkh_Khac = (RadioButton)findViewById(R.id.rd_addkh_Khac);
        rd_addkh_QuanLy = (RadioButton)findViewById(R.id.rd_addkh_QuanLy);
        rd_addkh_KhachHang = (RadioButton)findViewById(R.id.rd_addkh_KhachHang);
        DT_addkh_NgaySinh = (DatePicker)findViewById(R.id.dt_addkh_NgaySinh);
        btn_addkh_ThemKH = (Button)findViewById(R.id.btn_addkh_ThemKH);

        //endregion

        khachHangDAO = new KhachHangDAO(this);

        //region Hiển thị trang sửa nếu được chọn từ context menu sửa
        makh = getIntent().getIntExtra("makh",0);   //lấy makh từ display kh
        if(makh != 0){
            TXT_addkh_title.setText("Sửa khách hàng");
            KhachHangDTO khachHangDTO = khachHangDAO.LayKHTheoMa(makh);

            //Hiển thị thông tin từ csdl
            TXTL_addkh_HoVaTen.getEditText().setText(khachHangDTO.getHOTENKH());
            TXTL_addkh_TenDN.getEditText().setText(khachHangDTO.getTENDN());
            TXTL_addkh_Email.getEditText().setText(khachHangDTO.getEMAIL());
            TXTL_addkh_SDT.getEditText().setText(khachHangDTO.getSDT());
            TXTL_addkh_MatKhau.getEditText().setText(khachHangDTO.getMATKHAU());

            //Hiển thị giới tính từ csdl
            String gioitinh = khachHangDTO.getGIOITINH();
            if(gioitinh.equals("Nam")){
                RD_addkh_Nam.setChecked(true);
            }else if (gioitinh.equals("Nữ")){
                RD_addkh_Nu.setChecked(true);
            }else {
                RD_addkh_Khac.setChecked(true);
            }

            if(khachHangDTO.getMAQUYEN() == 1){
                rd_addkh_QuanLy.setChecked(true);
            }else {
                rd_addkh_KhachHang.setChecked(true);
            }

            //Hiển thị ngày sinh từ csdl
            String date = khachHangDTO.getNGAYSINH();
            String[] items = date.split("/");
            int day = Integer.parseInt(items[0]);
            int month = Integer.parseInt(items[1]) - 1;
            int year = Integer.parseInt(items[2]);
            DT_addkh_NgaySinh.updateDate(year,month,day);
            btn_addkh_ThemKH.setText("Sửa khách hàng");
        }
        //endregion

        btn_addkh_ThemKH.setOnClickListener(this);
        IMG_addkh_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        String chucnang;
        switch (id){
            case R.id.btn_addkh_ThemKH:
                if( !validateAge() | !validateEmail() | !validateFullName() | !validateGender() | !validatePassWord() |
                !validatePermission() | !validatePhone() | !validateUserName()){
                    return;
                }
                //Lấy dữ liệu từ view
                hoTen = TXTL_addkh_HoVaTen.getEditText().getText().toString();
                tenDN = TXTL_addkh_TenDN.getEditText().getText().toString();
                eMail = TXTL_addkh_Email.getEditText().getText().toString();
                sDT = TXTL_addkh_SDT.getEditText().getText().toString();
                matKhau = TXTL_addkh_MatKhau.getEditText().getText().toString();

                switch (RG_addkh_GioiTinh.getCheckedRadioButtonId()){
                    case R.id.rd_addkh_Nam: gioiTinh = "Nam"; break;
                    case R.id.rd_addkh_Nu: gioiTinh = "Nữ"; break;
                    case R.id.rd_addkh_Khac: gioiTinh = "Khác"; break;
                }
                switch (rg_addkh_Quyen.getCheckedRadioButtonId()){
                    case R.id.rd_addkh_QuanLy: quyen = 1; break;
                    case R.id.rd_addkh_KhachHang: quyen = 2; break;
                }

                ngaySinh = DT_addkh_NgaySinh.getDayOfMonth() + "/" + (DT_addkh_NgaySinh.getMonth() + 1)
                        +"/"+DT_addkh_NgaySinh.getYear();

                //truyền dữ liệu vào obj KHDTO
                KhachHangDTO khachHangDTO = new KhachHangDTO();
                khachHangDTO.setHOTENKH(hoTen);
                khachHangDTO.setTENDN(tenDN);
                khachHangDTO.setEMAIL(eMail);
                khachHangDTO.setSDT(sDT);
                khachHangDTO.setMATKHAU(matKhau);
                khachHangDTO.setGIOITINH(gioiTinh);
                khachHangDTO.setNGAYSINH(ngaySinh);
                khachHangDTO.setMAQUYEN(quyen);

                if(makh != 0){
                    ktra = khachHangDAO.SuaThongTinKH(khachHangDTO,makh);
                    chucnang = "sua";
                }else {
                    ktra = khachHangDAO.ThemKhachHang(khachHangDTO);
                    chucnang = "themkh";
                }
                //Thêm, sửa kh dựa theo obj khachhangDTO
                Intent intent = new Intent();
                intent.putExtra("ketquaktra",ktra);
                intent.putExtra("chucnang",chucnang);
                setResult(RESULT_OK,intent);
                finish();
                break;

            case R.id.img_addkh_back:
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                break;
        }
    }

    //region validate fields
    private boolean validateFullName(){
        String val = TXTL_addkh_HoVaTen.getEditText().getText().toString().trim();

        if(val.isEmpty()){
            TXTL_addkh_HoVaTen.setError(getResources().getString(R.string.not_empty));
            return false;
        }else {
            TXTL_addkh_HoVaTen.setError(null);
            TXTL_addkh_HoVaTen.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateUserName(){
        String val = TXTL_addkh_TenDN.getEditText().getText().toString().trim();
        String checkspaces = "\\A\\w{1,50}\\z";

        if(val.isEmpty()){
            TXTL_addkh_TenDN.setError(getResources().getString(R.string.not_empty));
            return false;
        }else if(val.length()>50){
            TXTL_addkh_TenDN.setError("Phải nhỏ hơn 50 ký tự");
            return false;
        }else if(!val.matches(checkspaces)){
            TXTL_addkh_TenDN.setError("Không được cách chữ!");
            return false;
        }
        else {
            TXTL_addkh_TenDN.setError(null);
            TXTL_addkh_TenDN.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateEmail(){
        String val = TXTL_addkh_Email.getEditText().getText().toString().trim();
        String checkspaces = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";

        if(val.isEmpty()){
            TXTL_addkh_Email.setError(getResources().getString(R.string.not_empty));
            return false;
        }else if(!val.matches(checkspaces)){
            TXTL_addkh_Email.setError("Email không hợp lệ!");
            return false;
        }
        else {
            TXTL_addkh_Email.setError(null);
            TXTL_addkh_Email.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePhone(){
        String val = TXTL_addkh_SDT.getEditText().getText().toString().trim();


        if(val.isEmpty()){
            TXTL_addkh_SDT.setError(getResources().getString(R.string.not_empty));
            return false;
        }else if(val.length() != 10){
            TXTL_addkh_SDT.setError("Số điện thoại không hợp lệ!");
            return false;
        }
        else {
            TXTL_addkh_SDT.setError(null);
            TXTL_addkh_SDT.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassWord(){
        String val = TXTL_addkh_MatKhau.getEditText().getText().toString().trim();

        if(val.isEmpty()){
            TXTL_addkh_MatKhau.setError(getResources().getString(R.string.not_empty));
            return false;
        }else if(!PASSWORD_PATTERN.matcher(val).matches()){
            TXTL_addkh_MatKhau.setError("Mật khẩu ít nhất 6 ký tự!");
            return false;
        }
        else {
            TXTL_addkh_MatKhau.setError(null);
            TXTL_addkh_MatKhau.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateGender(){
        if(RG_addkh_GioiTinh.getCheckedRadioButtonId() == -1){
            Toast.makeText(this,"Hãy chọn giới tính",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }

    private boolean validatePermission(){
        if(rg_addkh_Quyen.getCheckedRadioButtonId() == -1){
            Toast.makeText(this,"Hãy chọn quyền",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }

    private boolean validateAge(){
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int userAge = DT_addkh_NgaySinh.getYear();
        int isAgeValid = currentYear - userAge;

        if(isAgeValid < 10){
            Toast.makeText(this,"Bạn không đủ tuổi đăng ký!",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }
    //endregion

}