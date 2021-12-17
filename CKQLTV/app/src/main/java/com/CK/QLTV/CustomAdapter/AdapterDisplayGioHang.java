package com.CK.QLTV.CustomAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.CK.QLTV.Activities.HomeActivity;
import com.CK.QLTV.Activities.PaymentActivity;
import com.CK.QLTV.DAO.GioHangDAO;
import com.CK.QLTV.DAO.DonDatDAO;
import com.CK.QLTV.DTO.GioHangDTO;
import com.CK.QLTV.DTO.DonDatDTO;
import com.CK.QLTV.Fragments.DisplayCategoryFragment;
import com.CK.QLTV.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class AdapterDisplayGioHang extends BaseAdapter implements View.OnClickListener{

    Context context;
    int layout;
    List<GioHangDTO> gioHangDTOList;
    ViewHolder viewHolder;
    GioHangDAO gioHangDAO;
    DonDatDAO donDatDAO;
    FragmentManager fragmentManager;

    public AdapterDisplayGioHang(Context context, int layout, List<GioHangDTO> gioHangDTOList){
        this.context = context;
        this.layout = layout;
        this.gioHangDTOList = gioHangDTOList;
        gioHangDAO = new GioHangDAO(context);
        donDatDAO = new DonDatDAO(context);
        fragmentManager = ((HomeActivity)context).getSupportFragmentManager();
    }

    @Override
    public int getCount() {
        return gioHangDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return gioHangDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return gioHangDTOList.get(position).getMagiohang();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewHolder = new ViewHolder();
            view = inflater.inflate(layout,parent,false);

            viewHolder.imgGioHang = (ImageView) view.findViewById(R.id.img_customgiohang_GioHang);
            viewHolder.imgMuaSach = (ImageView) view.findViewById(R.id.img_customtable_DatSach);
            viewHolder.imgThanhToan = (ImageView) view.findViewById(R.id.img_customtable_ThanhToan);
            viewHolder.imgAnNut = (ImageView) view.findViewById(R.id.img_customtable_AnNut);
            viewHolder.txttengiohang = (TextView)view.findViewById(R.id.txt_customgiohang_TenGioHang);

            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if(gioHangDTOList.get(position).isDuocChon()){
            HienThiButton();
        }else {
            AnButton();
        }

        GioHangDTO gioHangDTO = gioHangDTOList.get(position);

        String kttinhtrang = gioHangDAO.LayTinhTrangGioHangTheoMa(gioHangDTO.getMagiohang());
        //đổi hình theo tình trạng
        if(kttinhtrang.equals("true")){
            viewHolder.imgGioHang.setImageResource(R.drawable.ic_shopping_cart_black_24dp);
        }else {
            viewHolder.imgGioHang.setImageResource(R.drawable.ic_shopping_cart_black_24dp);
        }

        viewHolder.txttengiohang.setText(gioHangDTO.getTengiohang());
        viewHolder.imgGioHang.setTag(position);

        //sự kiện click
        viewHolder.imgGioHang.setOnClickListener(this);
        viewHolder.imgMuaSach.setOnClickListener(this);
        viewHolder.imgThanhToan.setOnClickListener(this);
        viewHolder.imgAnNut.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        viewHolder = (ViewHolder) ((View) v.getParent()).getTag();

        int vitri1 = (int) viewHolder.imgGioHang.getTag();

        int magiohang = gioHangDTOList.get(vitri1).getMagiohang();
        String tengiohang = gioHangDTOList.get(vitri1).getTengiohang();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String ngaydat= dateFormat.format(calendar.getTime());

        switch (id){
            case R.id.img_customgiohang_GioHang:
                int vitri = (int)v.getTag();
                gioHangDTOList.get(vitri).setDuocChon(true);
                HienThiButton();
                break;

            case R.id.img_customtable_AnNut:
                AnButton();
                break;

            case R.id.img_customtable_DatSach:
                Intent getIHome = ((HomeActivity)context).getIntent();
                int makh = getIHome.getIntExtra("makh",0);
                String tinhtrang = gioHangDAO.LayTinhTrangGioHangTheoMa(magiohang);

                if(tinhtrang.equals("false")){
                    //Thêm bảng gọi sách và update tình trạng bàn
                    DonDatDTO donDatDTO = new DonDatDTO();
                    donDatDTO.setMagiohang(magiohang);
                    donDatDTO.setMaKH(makh);
                    donDatDTO.setNgayDat(ngaydat);
                    donDatDTO.setTinhTrang("false");
                    donDatDTO.setTongTien("0");

                    long ktra = donDatDAO.ThemDonDat(donDatDTO);
                    gioHangDAO.CapNhatTinhTrangGioHang(magiohang,"true");
                    if(ktra == 0){ Toast.makeText(context,context.getResources().getString(R.string.add_failed),Toast.LENGTH_SHORT).show(); }
                }
                //chuyển qua trang category
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                DisplayCategoryFragment displayCategoryFragment = new DisplayCategoryFragment();

                Bundle bDataCategory = new Bundle();
                bDataCategory.putInt("magiohang",magiohang);
                displayCategoryFragment.setArguments(bDataCategory);

                transaction.replace(R.id.contentView,displayCategoryFragment).addToBackStack("hienthiGioHang");
                transaction.commit();
                break;

            case R.id.img_customtable_ThanhToan:
                //chuyển dữ liệu qua trang thanh toán
                Intent iThanhToan = new Intent(context, PaymentActivity.class);
                iThanhToan.putExtra("magiohang",magiohang);
                iThanhToan.putExtra("tengiohang",tengiohang);
                iThanhToan.putExtra("ngaydat",ngaydat);
                context.startActivity(iThanhToan);
                break;
        }
    }

    private void HienThiButton(){
        viewHolder.imgMuaSach.setVisibility(View.VISIBLE);
        viewHolder.imgThanhToan.setVisibility(View.VISIBLE);
        viewHolder.imgAnNut.setVisibility(View.VISIBLE);
    }
    private void AnButton(){
        viewHolder.imgMuaSach.setVisibility(View.INVISIBLE);
        viewHolder.imgThanhToan.setVisibility(View.INVISIBLE);
        viewHolder.imgAnNut.setVisibility(View.INVISIBLE);
    }

    public class ViewHolder{
        ImageView imgGioHang, imgMuaSach, imgThanhToan, imgAnNut;
        TextView txttengiohang;
    }
}
