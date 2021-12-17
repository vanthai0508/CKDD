package com.CK.QLTV.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.CK.QLTV.DAO.GioHangDAO;
import com.CK.QLTV.DAO.KhachHangDAO;
import com.CK.QLTV.DTO.DonDatDTO;
import com.CK.QLTV.DTO.KhachHangDTO;
import com.CK.QLTV.R;

import java.util.List;

public class AdapterDisplayStatistic extends BaseAdapter {

    Context context;
    int layout;
    List<DonDatDTO> donDatDTOS;
    ViewHolder viewHolder;
    KhachHangDAO khachHangDAO;
    GioHangDAO gioHangDAO;

    public AdapterDisplayStatistic(Context context, int layout, List<DonDatDTO> donDatDTOS){
        this.context = context;
        this.layout = layout;
        this.donDatDTOS = donDatDTOS;
        khachHangDAO = new KhachHangDAO(context);
        gioHangDAO = new GioHangDAO(context);
    }

    @Override
    public int getCount() {
        return donDatDTOS.size();
    }

    @Override
    public Object getItem(int position) {
        return donDatDTOS.get(position);
    }

    @Override
    public long getItemId(int position) {
        return donDatDTOS.get(position).getMaDonDat();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,parent,false);

            viewHolder.txt_customstatistic_MaDon = (TextView)view.findViewById(R.id.txt_customstatistic_MaDon);
            viewHolder.txt_customstatistic_NgayDat = (TextView)view.findViewById(R.id.txt_customstatistic_NgayDat);
            viewHolder.txt_customstatistic_TenKH = (TextView)view.findViewById(R.id.txt_customstatistic_TenKH);
            viewHolder.txt_customstatistic_TongTien = (TextView)view.findViewById(R.id.txt_customstatistic_TongTien);
            viewHolder.txt_customstatistic_TrangThai = (TextView)view.findViewById(R.id.txt_customstatistic_TrangThai);
            viewHolder.txt_customstatistic_GiohangDat = (TextView)view.findViewById(R.id.txt_customstatistic_GiohangDat);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        DonDatDTO donDatDTO = donDatDTOS.get(position);

        viewHolder.txt_customstatistic_MaDon.setText("Mã đơn: "+donDatDTO.getMaDonDat());
        viewHolder.txt_customstatistic_NgayDat.setText(donDatDTO.getNgayDat());
        viewHolder.txt_customstatistic_TongTien.setText(donDatDTO.getTongTien()+" VNĐ");
        if (donDatDTO.getTinhTrang().equals("true"))
        {
            viewHolder.txt_customstatistic_TrangThai.setText("Đã thanh toán");
        }else {
            viewHolder.txt_customstatistic_TrangThai.setText("Chưa thanh toán");
        }
        KhachHangDTO khachHangDTO = khachHangDAO.LayKHTheoMa(donDatDTO.getMaKH());
        viewHolder.txt_customstatistic_TenKH.setText(khachHangDTO.getHOTENKH());
        viewHolder.txt_customstatistic_GiohangDat.setText(gioHangDAO.LayTenGioHangTheoMa(donDatDTO.getMagiohang()));

        return view;
    }
    public class ViewHolder{
        TextView txt_customstatistic_MaDon, txt_customstatistic_NgayDat, txt_customstatistic_TenKH
                ,txt_customstatistic_TongTien,txt_customstatistic_TrangThai, txt_customstatistic_GiohangDat;

    }
}
