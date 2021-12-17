package com.CK.QLTV.Activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.CK.QLTV.DAO.SachDAO;
import com.CK.QLTV.DTO.SachDTO;
import com.CK.QLTV.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddMenuActivity extends AppCompatActivity implements View.OnClickListener{

    Button btn_addmenu_ThemSach;
    RelativeLayout layout_trangthaisach;
    ImageView IMG_addmenu_ThemHinh, IMG_addmenu_back;
    TextView TXT_addmenu_title;
    TextInputLayout txtl_addmenu_TenSach,TXTL_addmenu_GiaTien,txtl_addmenu_TheLoaiSach;
    RadioGroup RG_addmenu_TinhTrang;
    RadioButton rd_addmenu_ConSach, rd_addmenu_HetSach;
    SachDAO sachDAO;
    String tenloai, sTenSach,sGiaTien,sTinhTrang;
    Bitmap bitmapold;
    int maloai;
    int masach = 0;

    ActivityResultLauncher<Intent> resultLauncherOpenIMG = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK && result.getData() != null){
                        Uri uri = result.getData().getData();
                        try{
                            InputStream inputStream = getContentResolver().openInputStream(uri);
                            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                            IMG_addmenu_ThemHinh.setImageBitmap(bitmap);
                        }catch (FileNotFoundException e){
                            e.printStackTrace();
                        }
                    }
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addmenu_layout);

        //region Lấy đối tượng view
        IMG_addmenu_ThemHinh = (ImageView)findViewById(R.id.img_addmenu_ThemHinh);
        IMG_addmenu_ThemHinh.setTag(R.drawable.conan);
        IMG_addmenu_back = (ImageView)findViewById(R.id.img_addmenu_back);
        txtl_addmenu_TenSach = (TextInputLayout)findViewById(R.id.txtl_addmenu_TenSach);
        TXTL_addmenu_GiaTien = (TextInputLayout)findViewById(R.id.txtl_addmenu_GiaTien);
        txtl_addmenu_TheLoaiSach = (TextInputLayout)findViewById(R.id.txtl_addmenu_TheLoaiSach);
        btn_addmenu_ThemSach = (Button)findViewById(R.id.btn_addmenu_ThemSach);
        TXT_addmenu_title = (TextView)findViewById(R.id.txt_addmenu_title);
        layout_trangthaisach = (RelativeLayout)findViewById(R.id.layout_trangthaisach);
        RG_addmenu_TinhTrang = (RadioGroup)findViewById(R.id.rg_addmenu_TinhTrang);
        rd_addmenu_ConSach = (RadioButton)findViewById(R.id.rd_addmenu_ConSach);
        rd_addmenu_HetSach = (RadioButton)findViewById(R.id.rd_addmenu_HetSach);
        //endregion

        Intent intent = getIntent();
        maloai = intent.getIntExtra("maLoai",-1);
        tenloai = intent.getStringExtra("tenLoai");
        sachDAO = new SachDAO(this);  //khởi tạo đối tượng dao kết nối csdl
        txtl_addmenu_TheLoaiSach.getEditText().setText(tenloai);

        BitmapDrawable olddrawable = (BitmapDrawable)IMG_addmenu_ThemHinh.getDrawable();
        bitmapold = olddrawable.getBitmap();

        //region Hiển thị trang sửa nếu được chọn từ context menu sửa
        masach = getIntent().getIntExtra("masach",0);
        if(masach != 0){
            TXT_addmenu_title.setText("Sửa thể loại");
            SachDTO sachDTO = sachDAO.LaySachTheoMa(masach);

            txtl_addmenu_TenSach.getEditText().setText(sachDTO.getTenSach());
            TXTL_addmenu_GiaTien.getEditText().setText(sachDTO.getGiaTien());

            byte[] menuimage = sachDTO.getHinhAnh();
            Bitmap bitmap = BitmapFactory.decodeByteArray(menuimage,0,menuimage.length);
            IMG_addmenu_ThemHinh.setImageBitmap(bitmap);

            layout_trangthaisach.setVisibility(View.VISIBLE);
            String tinhtrang = sachDTO.getTinhTrang();
            if(tinhtrang.equals("true")){
                rd_addmenu_ConSach.setChecked(true);
            }else {
                rd_addmenu_HetSach.setChecked(true);
            }

            btn_addmenu_ThemSach.setText("Sua sach");
        }

        //endregion

        IMG_addmenu_ThemHinh.setOnClickListener(this);
        btn_addmenu_ThemSach.setOnClickListener(this);
        IMG_addmenu_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        boolean ktra;
        String chucnang;
        switch (id){
            case R.id.img_addmenu_ThemHinh:
                Intent iGetIMG = new Intent();
                iGetIMG.setType("image/*");
                iGetIMG.setAction(Intent.ACTION_GET_CONTENT);
                resultLauncherOpenIMG.launch(Intent.createChooser(iGetIMG,getResources().getString(R.string.choseimg)));
                break;

            case R.id.img_addmenu_back:
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                break;

            case R.id.btn_addmenu_ThemSach:
                //ktra validation
                if(!validateImage() | !validateName() | !validatePrice()){
                    return;
                }

                sTenSach = txtl_addmenu_TenSach.getEditText().getText().toString();
                sGiaTien = TXTL_addmenu_GiaTien.getEditText().getText().toString();
                switch (RG_addmenu_TinhTrang.getCheckedRadioButtonId()){
                    case R.id.rd_addmenu_ConSach: sTinhTrang = "true";   break;
                    case R.id.rd_addmenu_HetSach: sTinhTrang = "false";  break;
                }

                SachDTO sachDTO = new SachDTO();
                sachDTO.setMaLoai(maloai);
                sachDTO.setTenSach(sTenSach);
                sachDTO.setGiaTien(sGiaTien);
                sachDTO.setTinhTrang(sTinhTrang);
                sachDTO.setHinhAnh(imageViewtoByte(IMG_addmenu_ThemHinh));
                if(masach!= 0){
                    ktra = sachDAO.SuaSach(sachDTO,masach);
                    chucnang = "suasach";
                }else {
                    ktra = sachDAO.ThemSach(sachDTO);
                    chucnang = "themsach";
                }

                //Thêm, sửa sách dựa theo obj loaisachDTO
                Intent intent = new Intent();
                intent.putExtra("ktra",ktra);
                intent.putExtra("chucnang",chucnang);
                setResult(RESULT_OK,intent);
                finish();

                break;
        }
    }

    //Chuyển ảnh bitmap về mảng byte lưu vào csdl
    private byte[] imageViewtoByte(ImageView imageView){
        Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    //region Validate field
    private boolean validateImage(){
        BitmapDrawable drawable = (BitmapDrawable)IMG_addmenu_ThemHinh.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        if(bitmap == bitmapold){
            Toast.makeText(getApplicationContext(),"Xin chọn hình ảnh",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }

    private boolean validateName(){
        String val = txtl_addmenu_TenSach.getEditText().getText().toString().trim();
        if(val.isEmpty()){
            txtl_addmenu_TenSach.setError(getResources().getString(R.string.not_empty));
            return false;
        }else {
            txtl_addmenu_TenSach.setError(null);
            txtl_addmenu_TenSach.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePrice(){
        String val = TXTL_addmenu_GiaTien.getEditText().getText().toString().trim();
        if(val.isEmpty()){
            TXTL_addmenu_GiaTien.setError(getResources().getString(R.string.not_empty));
            return false;
        }else if(!val.matches(("\\d+(?:\\.\\d+)?"))){
            TXTL_addmenu_GiaTien.setError("Giá tiền không hợp lệ");
            return false;
        }else {
            TXTL_addmenu_GiaTien.setError(null);
            TXTL_addmenu_GiaTien.setErrorEnabled(false);
            return true;
        }
    }
    //endregion

}