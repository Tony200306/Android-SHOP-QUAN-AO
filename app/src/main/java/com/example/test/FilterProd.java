package com.example.test;

import android.widget.Filter;

import com.example.test.Model.Clothes;

import java.util.ArrayList;

public class FilterProd extends Filter {
    ArrayList<Clothes> filterList;

    AdapterSanPham_Customer adapterSanPhamCustomer;

    public FilterProd(ArrayList<Clothes> filterList, AdapterSanPham_Customer adapterSanPhamCustomer) {
        this.filterList = filterList;
        this.adapterSanPhamCustomer = adapterSanPhamCustomer;
    }
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
        if(constraint != null && constraint.length() > 0){
            constraint = constraint.toString().toUpperCase();
            ArrayList<Clothes> filteredModels = new ArrayList<>();
            for(int i=0;i<filterList.size();i++){
                if(filterList.get(i).getTitle().toUpperCase().contains(constraint)){
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
    protected void publishResults(CharSequence constraint, FilterResults results) {
            adapterSanPhamCustomer.productsArrayList  =(ArrayList<Clothes>)  results.values;
            adapterSanPhamCustomer.notifyDataSetChanged();

    }
}
