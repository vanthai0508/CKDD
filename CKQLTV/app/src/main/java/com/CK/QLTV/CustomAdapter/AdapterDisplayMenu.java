package com.CK.QLTV.CustomAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.CK.QLTV.DTO.SachDTO;
import com.CK.QLTV.R;

import java.util.List;

public class AdapterDisplayMenu extends BaseAdapter {

    Context context;
    int layout;
    List<SachDTO> sachDTOList;
    Viewholder viewholder;

    //constructor
    public AdapterDisplayMenu(Context context, int layout, List<SachDTO> sachDTOList){
        this.context = context;
        this.layout = layout;
        this.sachDTOList = sachDTOList;
    }

    @Override
    public int getCount() {
        return sachDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return sachDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return sachDTOList.get(position).getMaSach();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            viewholder = new Viewholder();
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,parent,false);

            viewholder.img_custommenu_HinhSach = (ImageView)view.findViewById(R.id.img_custommenu_HinhSach);
            viewholder.txt_custommenu_TenSach = (TextView) view.findViewById(R.id.txt_custommenu_TenSach);
            viewholder.txt_custommenu_TinhTrang = (TextView)view.findViewById(R.id.txt_custommenu_TinhTrang);
            viewholder.txt_custommenu_GiaTien = (TextView)view.findViewById(R.id.txt_custommenu_GiaTien);
            view.setTag(viewholder);
        }else {
            viewholder = (Viewholder) view.getTag();
        }
        SachDTO sachDTO = sachDTOList.get(position);
        viewholder.txt_custommenu_TenSach.setText(sachDTO.getTenSach());
        viewholder.txt_custommenu_GiaTien.setText(sachDTO.getGiaTien()+" VNĐ");

        //hiển thị tình trạng của sách
        if(sachDTO.getTinhTrang().equals("true")){
            viewholder.txt_custommenu_TinhTrang.setText("Còn sách");
        }else{
            viewholder.txt_custommenu_TinhTrang.setText("Hết sách");
        }

        //lấy hình ảnh
        if(sachDTO.getHinhAnh() != null){
            byte[] menuimage = sachDTO.getHinhAnh();
            Bitmap bitmap = BitmapFactory.decodeByteArray(menuimage,0,menuimage.length);
            viewholder.img_custommenu_HinhSach.setImageBitmap(bitmap);
        }else {
            viewholder.img_custommenu_HinhSach.setImageResource(R.drawable.conan);
        }

        return view;
    }

    //tạo viewholer lưu trữ component
    public class Viewholder{
        ImageView img_custommenu_HinhSach;
        TextView txt_custommenu_TenSach, txt_custommenu_GiaTien,txt_custommenu_TinhTrang;

    }
}
