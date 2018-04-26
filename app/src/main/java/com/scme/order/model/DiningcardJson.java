package com.scme.order.model;

import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2018-04-26.
 */

public class DiningcardJson implements java.io.Serializable{
 private    int count;
  private  int sum;
  private List<Diningcard> diningcard;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public List<Diningcard> getDiningcard() {
        return diningcard;
    }

    public void setDiningcard(List<Diningcard> diningcard) {
        this.diningcard = diningcard;
    }
}
