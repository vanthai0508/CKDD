package com.CK.QLTV.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.CK.QLTV.DAO.GioHangDAO;
import com.CK.QLTV.DAO.KhachHangDAO;
import com.CK.QLTV.DTO.DonDatDTO;
import com.CK.QLTV.DTO.KhachHangDTO;
import com.CK.QLTV.R;

import java.util.List;

public class AdapterRecycleViewStatistic extends RecyclerView.Adapter<AdapterRecycleViewStatistic.ViewHolder>{

    Context context;
    int layout;
    List<DonDatDTO> donDatDTOList;
    KhachHangDAO khachHangDAO;
    GioHangDAO gioHangDAO;

    public AdapterRecycleViewStatistic(Context context, int layout, List<DonDatDTO> donDatDTOList){

        this.context =context;
        this.layout = layout;
        this.donDatDTOList = donDatDTOList;
        khachHangDAO = new KhachHangDAO(context);
        gioHangDAO = new GioHangDAO(context);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterRecycleViewStatistic.ViewHolder holder, int position) {
        DonDatDTO donDatDTO = donDatDTOList.get(position);
        holder.txt_customstatistic_MaDon.setText("Mã đơn: "+donDatDTO.getMaDonDat());
        holder.txt_customstatistic_NgayDat.setText(donDatDTO.getNgayDat());
        if(donDatDTO.getTongTien().equals("0"))
        {
            holder.txt_customstatistic_TongTien.setVisibility(View.INVISIBLE);
        }else {
            holder.txt_customstatistic_TongTien.setVisibility(View.VISIBLE);
        }

        if (donDatDTO.getTinhTrang().equals("true"))
        {
            holder.txt_customstatistic_TrangThai.setText("Đã thanh toán");
        }else {
            holder.txt_customstatistic_TrangThai.setText("Chưa thanh toán");
        }
        KhachHangDTO khachHangDTO = khachHangDAO.LayKHTheoMa(donDatDTO.getMaKH());
        holder.txt_customstatistic_TenKH.setText(khachHangDTO.getHOTENKH());
        holder.txt_customstatistic_GiohangDat.setText(gioHangDAO.LayTenGioHangTheoMa(donDatDTO.getMagiohang()));
    }

    @Override
    public int getItemCount() {
        return donDatDTOList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txt_customstatistic_MaDon, txt_customstatistic_NgayDat, txt_customstatistic_TenKH,
                txt_customstatistic_GiohangDat, txt_customstatistic_TongTien,txt_customstatistic_TrangThai;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            txt_customstatistic_MaDon = itemView.findViewById(R.id.txt_customstatistic_MaDon);
            txt_customstatistic_NgayDat = itemView.findViewById(R.id.txt_customstatistic_NgayDat);
            txt_customstatistic_TenKH = itemView.findViewById(R.id.txt_customstatistic_TenKH);
            txt_customstatistic_GiohangDat = itemView.findViewById(R.id.txt_customstatistic_GiohangDat);
            txt_customstatistic_TongTien = itemView.findViewById(R.id.txt_customstatistic_TongTien);
            txt_customstatistic_TrangThai = itemView.findViewById(R.id.txt_customstatistic_TrangThai);
        }
    }
}
