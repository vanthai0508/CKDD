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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.CK.QLTV.Activities.AddCategoryActivity;
import com.CK.QLTV.Activities.HomeActivity;
import com.CK.QLTV.CustomAdapter.AdapterDisplayCategory;
import com.CK.QLTV.DAO.LoaiSachDAO;
import com.CK.QLTV.DTO.LoaiSachDTO;
import com.CK.QLTV.R;

import java.util.List;

public class DisplayCategoryFragment extends Fragment {

    GridView gvCategory;
    List<LoaiSachDTO> loaiSachDTOList;
    LoaiSachDAO loaiSachDAO;
    AdapterDisplayCategory adapter;
    FragmentManager fragmentManager;
    int magiohang;

    ActivityResultLauncher<Intent> resultLauncherCategory = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent intent = result.getData();
                        boolean ktra = intent.getBooleanExtra("ktra",false);
                        String chucnang = intent.getStringExtra("chucnang");
                        if(chucnang.equals("themloai"))
                        {
                            if(ktra){
                                HienThiDSLoai();
                                Toast.makeText(getActivity(),"Th??m th??nh c??ng",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getActivity(),"Th??m th???t b???i",Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            if(ktra){
                                HienThiDSLoai();
                                Toast.makeText(getActivity(),"S???a th??nh c??ng",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getActivity(),"s???a th???t b???i",Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                }
            });


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.displaycategory_layout,container,false);
        setHasOptionsMenu(true);
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle("Th??? lo???i s??ch");

        gvCategory = (GridView)view.findViewById(R.id.gvCategory);

        fragmentManager = getActivity().getSupportFragmentManager();

        loaiSachDAO = new LoaiSachDAO(getActivity());
        HienThiDSLoai();

        Bundle bDataCategory = getArguments();
        if(bDataCategory != null){
            magiohang = bDataCategory.getInt("magiohang");
        }

        gvCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int maloai = loaiSachDTOList.get(position).getMaLoai();
                String tenloai = loaiSachDTOList.get(position).getTenLoai();
                DisplayMenuFragment displayMenuFragment = new DisplayMenuFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("maloai",maloai);
                bundle.putString("tenloai",tenloai);
                bundle.putInt("magiohang",magiohang);
                displayMenuFragment.setArguments(bundle);

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.contentView,displayMenuFragment).addToBackStack("hienthiloai");
                transaction.commit();
            }
        });

        registerForContextMenu(gvCategory);

        return view;
    }

    //hi???n th??? contextmenu
    @Override
    public void onCreateContextMenu(ContextMenu menu,View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.edit_context_menu,menu);
    }

    //x??? l?? context menu
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int vitri = menuInfo.position;
        int maloai = loaiSachDTOList.get(vitri).getMaLoai();

        switch (id){
            case R.id.itEdit:
                Intent iEdit = new Intent(getActivity(), AddCategoryActivity.class);
                iEdit.putExtra("maloai",maloai);
                resultLauncherCategory.launch(iEdit);
                break;

            case R.id.itDelete:
                boolean ktra = loaiSachDAO.XoaLoaiSach(maloai);
                if(ktra){
                    HienThiDSLoai();
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

    //kh???i t???o n??t th??m lo???i
    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem itAddCategory = menu.add(1,R.id.itAddCategory,1,R.string.addCategory);
        itAddCategory.setIcon(R.drawable.ic_baseline_add_24);
        itAddCategory.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }

    //x??? l?? n??t th??m lo???i
    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.itAddCategory:
                Intent intent = new Intent(getActivity(), AddCategoryActivity.class);
                resultLauncherCategory.launch(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //hi???n th??? d??? li???u tr??n gridview
    private void HienThiDSLoai(){
        loaiSachDTOList = loaiSachDAO.LayDSLoaiSach();
        adapter = new AdapterDisplayCategory(getActivity(),R.layout.custom_layout_displaycategory, loaiSachDTOList);
        gvCategory.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
