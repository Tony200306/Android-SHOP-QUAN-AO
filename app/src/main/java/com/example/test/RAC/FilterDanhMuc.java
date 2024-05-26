//package com.example.test;
//
//import android.widget.Filter;
//
//import java.util.ArrayList;
//
//public class FilterDanhMuc extends Filter {
//
//    ArrayList<CategoryDomain> filterList;
//    AdapterDanhMuc adapterDanhMuc;
//
//    public FilterDanhMuc(ArrayList<CategoryDomain> filterList, AdapterDanhMuc adapterDanhMuc) {
//        this.filterList = filterList;
//        this.adapterDanhMuc = adapterDanhMuc;
//    }
//
//    @Override
//    protected FilterResults performFiltering(CharSequence charSequence) {
//        FilterResults results = new FilterResults();
//        if(charSequence != null && charSequence.length() > 0){
//            charSequence = charSequence.toString().toUpperCase();
//            ArrayList<CategoryDomain> filteredModels = new ArrayList<>();
//            for(int i=0;i<filterList.size();i++){
//                if(filterList.get(i).getTitle().toUpperCase().contains(charSequence)){
//                    filteredModels.add(filterList.get(i));
//                }
//            }
//
//            results.count = filteredModels.size();
//            results.values = filteredModels;
//        }
//        else{
//            results.count = filterList.size();
//            results.values = filterList;
//        }
//        return results;
//    }
//
//    @Override
//    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//        adapterDanhMuc.categoriesArrayList = (ArrayList<CategoryDomain>) filterResults.values;
//        adapterDanhMuc.notifyDataSetChanged();
//    }
//}
