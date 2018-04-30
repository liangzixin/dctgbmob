package com.scme.order.model;

import java.util.List;

/**
 * Created by Administrator on 2018-04-26.
 */

public class EatsJson implements java.io.Serializable{
 private    int count;
  private  int sum;
 private List<Teats> eats;

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


    public List<Teats> getEats() {
        return eats;
    }

    public void setEats(List<Teats> eats) {
        this.eats = eats;
    }
//    public class Myeats{
//     private int    departmentid;
//     private String eatdate;
//     private int eatid;
//     private int eatid1;
//     private int eatnumber;
//     private boolean newer;
//    private  String operator;
//    private  String operatortime;
//    private  String pkClazz;
//    private  String unclear;
//    private  int unitprice;
//    private  int workerid;
//
//        public int getDepartmentid() {
//            return departmentid;
//        }
//
//        public void setDepartmentid(int departmentid) {
//            this.departmentid = departmentid;
//        }
//
//        public String getEatdate() {
//            return eatdate;
//        }
//
//        public void setEatdate(String eatdate) {
//            this.eatdate = eatdate;
//        }
//
//        public int getEatid() {
//            return eatid;
//        }
//
//        public void setEatid(int eatid) {
//            this.eatid = eatid;
//        }
//
//        public int getEatid1() {
//            return eatid1;
//        }
//
//        public void setEatid1(int eatid1) {
//            this.eatid1 = eatid1;
//        }
//
//        public int getEatnumber() {
//            return eatnumber;
//        }
//
//        public void setEatnumber(int eatnumber) {
//            this.eatnumber = eatnumber;
//        }
//
//        public boolean isNewer() {
//            return newer;
//        }
//
//        public void setNewer(boolean newer) {
//            this.newer = newer;
//        }
//
//        public String getOperator() {
//            return operator;
//        }
//
//        public void setOperator(String operator) {
//            this.operator = operator;
//        }
//
//        public String getOperatortime() {
//            return operatortime;
//        }
//
//        public void setOperatortime(String operatortime) {
//            this.operatortime = operatortime;
//        }
//
//        public String getPkClazz() {
//            return pkClazz;
//        }
//
//        public void setPkClazz(String pkClazz) {
//            this.pkClazz = pkClazz;
//        }
//
//        public String getUnclear() {
//            return unclear;
//        }
//
//        public void setUnclear(String unclear) {
//            this.unclear = unclear;
//        }
//
//        public int getUnitprice() {
//            return unitprice;
//        }
//
//        public void setUnitprice(int unitprice) {
//            this.unitprice = unitprice;
//        }
//
//        public int getWorkerid() {
//            return workerid;
//        }
//
//        public void setWorkerid(int workerid) {
//            this.workerid = workerid;
//        }
//    }
}
