package com.example.test;

import android.content.Context;
import android.widget.Toast;

import com.example.test.Model.Clothes;
import com.example.test.Model.SharedPreferences;

import java.util.ArrayList;

public class ManagementCart {
    private Context context;
    private SharedPreferences sharedPreferences;

    public ManagementCart(Context context) {
        this.context = context;
        this.sharedPreferences = new SharedPreferences(context);
    }
    public void insertClothes(Clothes item)
    {
        ArrayList<Clothes> lstFood =getListCart();
        boolean existAlready = false;
        int n = 0;
        for(int i =0;i<lstFood.size();i++)
        {
            if(lstFood.get(i).getTitle().equals(item.getTitle())){
                existAlready = true;
                n = i ;
                break;
            }
        }
        if(existAlready){
            lstFood.get(n).setNumberInCart(item.getNumberInCart());
        }
        else {
            lstFood.add(item);
        }
        sharedPreferences.putListObject("CartList",lstFood);
        Toast.makeText(context,"Added To Your Cart", Toast.LENGTH_LONG).show();
    }

    public ArrayList<Clothes> getListCart()
    {
        return sharedPreferences.getListObject("CartList");
    }
    public ArrayList<Clothes> GetEmpty()
    {
        ArrayList<Clothes> lstclothes = new ArrayList<Clothes> ();
        sharedPreferences.putListObject("CartList",lstclothes);
        return sharedPreferences.getListObject("CartList");

    }


    public void plusNumberFood(ArrayList<Clothes>ListFood, int position, ChangeNumberItemsListener changeNumberItemsListener)
    {
        ListFood.get(position).setNumberInCart(ListFood.get(position).getNumberInCart()+1);
        sharedPreferences.putListObject("CartList",ListFood);
        changeNumberItemsListener.changed();
    }
    public void minusNumberFood(ArrayList<Clothes>ListClothes, int position, ChangeNumberItemsListener changeNumberItemsListener)
    {
        if(ListClothes.get(position).getNumberInCart()==1)
        {
            ListClothes.remove(position);
        }
        else {
            ListClothes.get(position).setNumberInCart(ListClothes.get(position).getNumberInCart()-1);

        }
        sharedPreferences.putListObject("CartList",ListClothes);
        changeNumberItemsListener.changed();

    }
    public Double getTotalFee()
    {
        ArrayList<Clothes> listFood = getListCart() ;
        double fee = 0;
        for(int i =0; i<listFood.size();i++)
        {
            fee = fee + (listFood.get(i).getFee() *listFood.get(i).getNumberInCart());
        }
        return fee;
    }
}
