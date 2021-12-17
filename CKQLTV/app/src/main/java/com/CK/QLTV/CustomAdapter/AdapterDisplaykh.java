package com.CK.QLTV.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.CK.QLTV.DAO.QuyenDAO;
import com.CK.QLTV.DTO.KhachHangDTO;
import com.CK.QLTV.R;

import java.util.List;

public class AdapterDisplaykh extends BaseAdapter {

    Context context;
    int layout;
    List<KhachHangDTO> khachHangDTOS;
    ViewHolder viewHolder;
    QuyenDAO quyenDAO;

    public AdapterDisplaykh(Context context, int layout, List<KhachHangDTO> khachHangDTOS){
        this.context = context;
        this.layout = layout;
        this.khachHangDTOS = khachHangDTOS;
        quyenDAO = new QuyenDAO(context);
    }

    @Override
    public int getCount() {
        return khachHangDTOS.size();
    }

    @Override
    public Object getItem(int position) {
        return khachHangDTOS.get(position);
    }

    @Override
    public long getItemId(int position) {
        return khachHangDTOS.get(position).getMaKH();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,parent,false);

            viewHolder.img_customkh_HinhKH = (ImageView)view.findViewById(R.id.img_customkh_HinhKH);
            viewHolder.txt_customkh_TenKH = (TextView)view.findViewById(R.id.txt_customkh_TenKH);
            viewHolder.txt_customkh_TenQuyen = (TextView)view.findViewById(R.id.txt_customkh_TenQuyen);
            viewHolder.txt_customkh_SDT = (TextView)view.findViewById(R.id.txt_customkh_SDT);
            viewHolder.txt_customkh_Email = (TextView)view.findViewById(R.id.txt_customkh_Email);

            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        KhachHangDTO khachHangDTO = khachHangDTOS.get(position);

        viewHolder.txt_customkh_TenKH.setText(khachHangDTO.getHOTENKH());
        viewHolder.txt_customkh_TenQuyen.setText(quyenDAO.LayTenQuyenTheoMa(khachHangDTO.getMAQUYEN()));
        viewHolder.txt_customkh_SDT.setText(khachHangDTO.getSDT());
        viewHolder.txt_customkh_Email.setText(khachHangDTO.getEMAIL());

        return view;
    }

    public class ViewHolder{
        ImageView img_customkh_HinhKH;
        TextView txt_customkh_TenKH, txt_customkh_TenQuyen,txt_customkh_SDT, txt_customkh_Email;
    }
}
