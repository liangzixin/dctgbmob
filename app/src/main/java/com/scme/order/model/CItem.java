package com.scme.order.model;

/**
 * Created by lzx on 2018/4/30.
 */

public class CItem implements java.io.Serializable{

    private int id =0;
    private String name="";
    private int amount=0;
    public CItem () {
        id =0;
        name = "";
    }

    public CItem (int _ID, String _Value) {
        id = _ID;
        name = _Value;
    }

    @Override
    public String toString() {           //为什么要重写toString()呢？因为适配器在显示数据的时候，如果传入适配器的对象不是字符串的情况下，直接就使用对象.toString()
        // TODO Auto-generated method stub
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}