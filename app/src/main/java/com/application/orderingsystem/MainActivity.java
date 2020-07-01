package com.application.orderingsystem;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    ListView foodClassificationView,foodMenuView ;
    SimpleAdapter foodClassificationAdapter,foodMenuAdapter ;
    List<Map<String,Object>> foodClassificationList ;
    List<Map<String,Object>> foodMenuList ;
    TextView tvCost ;
    double totCost = 0 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        foodClassificationView = findViewById(R.id.lv_food_classification) ;
        foodMenuView = findViewById(R.id.lv_food_menu) ;
        tvCost = findViewById(R.id.tv_cost) ;
        tvCost.setText("0.0");
        foodClassificationList = new ArrayList<>();
        foodMenuList = new ArrayList<>() ;
        for(int i = 0 ; i< 20 ;++ i) {
            foodClassificationList.add(new Item.Table().toHashMap()) ;
        }
        for(int i = 0 ;i < 10 ; ++ i) {
            foodMenuList.add(new Item.Food().toHashMap()) ;
        }
        foodClassificationAdapter = new SimpleAdapter(this,
                foodClassificationList,
                R.layout.food_classification_item,
                new String[]{"name", "icon"},
                new int[]{R.id.tv_classification_name, R.id.img_classification_icon}) ;
        foodClassificationView.setAdapter(foodClassificationAdapter);
        foodClassificationAdapter.notifyDataSetChanged();

        foodMenuAdapter = new SimpleAdapter(this,
                foodMenuList,
                R.layout.food_menu_item,
                new String[]{"name","label","sales","description","addimg","subimg","image"},
                new int[]{R.id.tv_food_name,R.id.tv_food_label,R.id.tv_food_sales,R.id.tv_food_description,R.id.img_food_add,R.id.img_food_sub,R.id.img_food_image}
                ) ;
        foodMenuView.setAdapter(foodMenuAdapter);
        foodMenuAdapter.notifyDataSetChanged();
    }

    public void selectOk(View view) {

    }

    public void foodItemSub(View view) {
        view = (View) view.getParent() ;
        TextView tvCount = ((View) view.getParent()).findViewById(R.id.tv_food_count) ;
        int cnt = Integer.parseInt(tvCount.getText().toString()) ;
        if(cnt == 0) {
            return ;
        }
        tvCount.setText(String.valueOf(cnt - 1));
        view = (View) view.getParent() ;
        TextView tvName = view.findViewById(R.id.tv_food_name) ;
        double cost = Double.parseDouble(tvName.getText().toString().split("¥")[1]) ;
        totCost -= cost ;
        tvCost.setText(String.valueOf(totCost));
    }

    public void foodItemAdd(View view) {
        view = (View) view.getParent() ;
        TextView tvCount = ((View) view.getParent()).findViewById(R.id.tv_food_count) ;
        tvCount.setText(String.valueOf(Integer.parseInt(tvCount.getText().toString())+1));
        view = (View) view.getParent() ;
        TextView tvName = view.findViewById(R.id.tv_food_name) ;
        double cost = Double.parseDouble(tvName.getText().toString().split("¥")[1]) ;
        totCost += cost ;
        tvCost.setText(String.valueOf(totCost));
    }
}