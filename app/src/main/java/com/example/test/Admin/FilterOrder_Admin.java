package com.example.test.Admin;

import android.widget.Filter;

import com.example.test.Model.Request;
import com.example.test.OrderAdapter;

import java.util.ArrayList;

public class FilterOrder_Admin extends Filter {

    ArrayList<Request> filterList;
    OrderAdapter_Admin adapterOrder;

    public FilterOrder_Admin(ArrayList<Request> filterList, OrderAdapter_Admin adapterOrder) {
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
