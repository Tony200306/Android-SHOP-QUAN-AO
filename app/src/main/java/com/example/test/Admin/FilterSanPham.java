package com.example.test.Admin;

import android.widget.Filter;

import com.example.test.Model.Clothes;

import java.util.ArrayList;

public class FilterSanPham  extends Filter {
    ArrayList<Clothes> filterList;
    AdapterSanPham_admin adapterSanPhamAdmin;

    public FilterSanPham(ArrayList<Clothes> filterList, AdapterSanPham_admin adapterSanPhamAdmin) {
        this.filterList = filterList;
        this.adapterSanPhamAdmin = adapterSanPhamAdmin;
    }
    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {
        FilterResults results = new FilterResults();
        if(charSequence != null && charSequence.length() > 0){
            charSequence = charSequence.toString().toUpperCase();
            ArrayList<Clothes> filteredModels = new ArrayList<>();
            for(int i=0;i<filterList.size();i++){
                if(filterList.get(i).getTitle().toUpperCase().contains(charSequence)){
                    filteredModels.add(filterList.get(i));
                }
            }
            results.count = filteredModels.size();
            results.values = filteredModels;
        }
        else{
            results.count = filterList.size();
            results.values = filterList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            adapterSanPhamAdmin.productsArrayList = (ArrayList<Clothes>) filterResults.values;
            adapterSanPhamAdmin.notifyDataSetChanged();
    }

}
