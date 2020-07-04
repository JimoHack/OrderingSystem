package com.application.orderingsystem;

import java.lang.reflect.Field;
import java.util.HashMap;

public class Item {
    public static int getResId(String variableName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    static class Table {
        int icon = R.mipmap.ic_launcher;
        String name = "鸡类";

        HashMap<String, Object> toHashMap() {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("name", name);
            hashMap.put("icon", icon);
            return hashMap;
        }

        public Table setName(String name) {
            this.name = name;
            return this;
        }

        public String getName() {
            return name;
        }

        public Table setIcon(int image) {
            this.icon = image;
            return this;
        }

        public Table setIcon(String image) {
            return setIcon(getResId(image, R.drawable.class));
        }

        public int getIcon() {
            return icon;
        }
    }

    static class Food {
        String name = "大盘鸡";
        String enName = "DaPanJi" ;
        double price = 12.0;
        String description = "一人份";
        String sales = "月售495   好评度93%";
        String label = "微辣|鸡肉";
        int count ;
        int image = R.mipmap.ic_launcher;
        HashMap<String, Object> hashMap ;
        HashMap<String, Object> getHashMap() {
            if(hashMap == null)
                hashMap = new HashMap<>() ;
            hashMap.put("name", name + "¥" + price);
            hashMap.put("description", description);
            hashMap.put("addimg", R.drawable.add);
            hashMap.put("subimg", R.drawable.sub);
            hashMap.put("image", image);
            hashMap.put("label", label);
            hashMap.put("sales", sales);
            hashMap.put("count", count);
            return hashMap;
        }

        Food(int image, String name,String enName,double price, String label, String sales, String description) {
            setName(name);
            setImage(image);
            setDescription(description);
            setLabel(label);
            setPrice(price);
            setSales(sales);
            setPrice(price);
            setEnName(enName);
            count = 0 ;
        }

        public Food add() {
            ++ count ;
            hashMap.put("count", count);
            return this ;
        }

        public int getCount() {
            return count;
        }

        public Food sub() {
            -- count ;
            hashMap.put("count", count);
            return this ;
        }

        public Food setImage(String image) {
            return setImage(getResId(image, R.drawable.class));
        }

        public Food setImage(int image) {
            this.image = image;
            return this;
        }

        public Food setName(String name) {
            this.name = name;
            return this;
        }

        public Food setEnName(String enName) {
            this.enName = enName;
            return this ;
        }

        public Food setDescription(String description) {
            this.description = description;
            return this;
        }

        public Food setLabel(String label) {
            this.label = label;
            return this;
        }

        public Food setPrice(double price) {
            this.price = price;
            return this;
        }

        public Food setSales(String sales) {
            this.sales = sales;
            return this;
        }

        public int getImage() {
            return image;
        }

        public String getName() {
            return name;
        }

        public String getEnName() {
            return enName;
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
