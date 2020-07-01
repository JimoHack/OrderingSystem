package com.application.orderingsystem;

import java.util.HashMap;

public class Item {
    static class Table {
        int icon = R.mipmap.ic_launcher ;
        String name = "鸡类" ;

        HashMap<String,Object> toHashMap() {
            HashMap<String,Object> hashMap = new HashMap<>() ;
            hashMap.put("name",name) ;
            hashMap.put("icon",icon) ;
            return hashMap;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setIcon(int image) {
            this.icon = image;
        }

        public int getIcon() {
            return icon;
        }
    }

    static class Food {
        String name = "大盘鸡";
        double price = 12.0 ;
        String description = "一人份" ;
        String sales = "月售495   好评度93%";
        String label = "微辣|鸡肉";
        int image = R.mipmap.ic_launcher;

        HashMap<String,Object> toHashMap() {
            HashMap<String,Object> hashMap = new HashMap<>() ;
            hashMap.put("name",name + "¥" + price) ;
            hashMap.put("description",description) ;
            hashMap.put("addimg",R.drawable.add) ;
            hashMap.put("subimg",R.drawable.sub) ;
            hashMap.put("image",image) ;
            hashMap.put("label",label) ;
            hashMap.put("sales",sales) ;
            return hashMap ;
        }

        public Food setImage(int image) {
            this.image = image;
            return this ;
        }

        public Food setName(String name) {
            this.name = name;
            return this ;
        }

        public Food setDescription(String description) {
            this.description = description;
            return this ;
        }

        public Food setLabel(String label) {
            this.label = label;
            return this ;
        }

        public Food setPrice(double price) {
            this.price = price;
            return this ;
        }

        public Food setSales(String sales) {
            this.sales = sales;
            return this ;
        }

        public int getImage() {
            return image;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }

        public String getDescription() {
            return description;
        }

        public String getLabel() {
            return label;
        }

        public String getSales() {
            return sales;
        }

    }
}
