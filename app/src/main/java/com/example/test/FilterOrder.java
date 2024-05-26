package com.example.test;

import android.widget.Filter;

import com.example.test.Admin.OrderAdapter_Admin;
import com.example.test.Model.Request;

import java.util.ArrayList;

public class FilterOrder extends Filter {

    ArrayList<Request> filterList;
    OrderAdapter adapterOrder;

    public FilterOrder(ArrayList<Request> filterList, OrderAdapter adapterOrder) {
        this.filterList = filterList;
        this.adapterOrder = adapterOrder;
    }
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();

        if(constraint != null && constraint.length() > 0){
            constraint = constraint.toString().toUpperCase();
            ArrayList<Request> filteredModels = new ArrayList<>();
            for(int i=0;i<filterList.size();i++){
                if(filterList.get(i).getName().toUpperCase().contains(constraint)){
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
        adapterOrder.Order = (ArrayList<Request>) results.values;
        adapterOrder.notifyDataSetChanged();
    }
}
