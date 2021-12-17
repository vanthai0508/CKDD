package com.CK.QLTV.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.CK.QLTV.Activities.AddKhActivity;
import com.CK.QLTV.Activities.HomeActivity;
import com.CK.QLTV.CustomAdapter.AdapterDisplaykh;
import com.CK.QLTV.DAO.KhachHangDAO;
import com.CK.QLTV.DTO.KhachHangDTO;
import com.CK.QLTV.R;

import java.util.List;

public class DisplayKhFragment extends Fragment {

    GridView gvkh;
    KhachHangDAO khachHangDAO;
    List<KhachHangDTO> khachHangDTOS;
    AdapterDisplaykh adapterDisplaykh;

    ActivityResultLauncher<Intent> resultLauncherAdd = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent intent = result.getData();
                        long ktra = intent.getLongExtra("ketquaktra",0);
                        String chucnang = intent.getStringExtra("chucnang");
                        if(chucnang.equals("themkh"))
                        {
                            if(ktra != 0){
                                HienThiDSKH();
                                Toast.makeText(getActivity(),"Thêm thành công",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getActivity(),"Thêm thất bại",Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            if(ktra != 0){
                                HienThiDSKH();
                                Toast.makeText(getActivity(),"Sửa thành công",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getActivity(),"Sửa thất bại",Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                }
            });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.displaykh_layout,container,false);
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle("Quản lý khách hàng");
        setHasOptionsMenu(true);

        gvkh = (GridView)view.findViewById(R.id.gvkh) ;

        khachHangDAO = new KhachHangDAO(getActivity());
        HienThiDSKH();

        registerForContextMenu(gvkh);

        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu,View v,ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.edit_context_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int vitri = menuInfo.position;
        int makh = khachHangDTOS.get(vitri).getMaKH();

        switch (id){
            case R.id.itEdit:
                Intent iEdit = new Intent(getActivity(), AddKhActivity.class);
                iEdit.putExtra("makh",makh);
                resultLauncherAdd.launch(iEdit);
                break;

            case R.id.itDelete:
                boolean ktra = khachHangDAO.XoaNV(makh);
                if(ktra){
                    HienThiDSKH();
                    Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.delete_sucessful)
                            ,Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.delete_failed)
                            ,Toast.LENGTH_SHORT).show();
                }
                break;
        }

        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem itAddkh = menu.add(1,R.id.itAddkh,1,"Thêm khách hàng");
        itAddkh.setIcon(R.drawable.ic_baseline_add_24);
        itAddkh.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.itAddkh:
                Intent iDangky = new Intent(getActivity(), AddKhActivity.class);
                resultLauncherAdd.launch(iDangky);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void HienThiDSKH(){
        khachHangDTOS = khachHangDAO.LayDSKH();
        adapterDisplaykh = new AdapterDisplaykh(getActivity(),R.layout.custom_layout_displaykh, khachHangDTOS);
        gvkh.setAdapter(adapterDisplaykh);
        adapterDisplaykh.notifyDataSetChanged();
    }
}
