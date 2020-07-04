package com.application.orderingsystem;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.application.library.BluetoothControl;
import com.application.library.Callback;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    int tabId ;
    ListView foodClassificationView, foodMenuView;
    SimpleAdapter foodClassificationAdapter, foodMenuAdapter;
    List<Map<String, Object>> foodClassificationList;
    List<Map<String, Object>> foodMenuList;
    List<List<Map<String, Object>>> foodMenuLists;
    TextView tvCost;
    double totCost = 0;
    HashMap<String, Integer> items;
    HashMap<String,Integer> refMap ;
    BluetoothControl bluetoothControl ;
    Item.Food[][] foods = {
            {
                    new Item.Food(R.drawable.food_item_0_0, "大盘鸡","DaPanJi",12,"微辣｜鸡肉", "月售495   好评度93%","一人份"),
                    new Item.Food(R.drawable.food_item_0_1, "东坡肉","DongPoRou",20,"酱汁|猪肉", "月售378   好评度95%","一人份"),
                    new Item.Food(R.drawable.food_item_0_2, "红烧狮子头","SiXiWanZi",15,"酱汁|猪肉", "月售398   好评度97%","一人份"),
                    new Item.Food(R.drawable.food_item_0_3, "辣子鸡","LaZiJi",30,"重辣|鸡肉", "月售427   好评度98%","二人份"),
                    new Item.Food(R.drawable.food_item_0_4, "水煮鱼","ShuiZhuYu",18,"中辣｜鱼肉", "月售227   好评度94%","一人份"),
                    new Item.Food(R.drawable.food_item_0_5, "鸡汤煮干丝","JiTangGanSi",16,"麻香|鸡肉", "月售487   好评度99%","二人份"),
            },
            {
                    new Item.Food(R.drawable.food_item_1_0, "酱汁拍黄瓜","PaiHuangGua",10,"酱汁|黄瓜", "月售303   好评度94%","一人份"),
                    new Item.Food(R.drawable.food_item_1_1, "树胶土豆丝","TuDouSi",8,"微辣｜土豆", "月售314   好评度92%","一人份"),

            },
            {
                    new Item.Food(R.drawable.food_item_2_0, "双层牛肉堡","Hamburg",15,"微辣｜牛肉", "月售514   好评度97%","一人份"),
                    new Item.Food(R.drawable.food_item_2_1, "芝士火腿披萨","Pizza",35,"芝士｜火腿", "月售214   好评度96%","二人份"),
            },
            {
                    new Item.Food(R.drawable.food_item_3_0, "米饭","Rice",3, "大米","月售533   好评度94%","一人份"),
            },
            {
                    new Item.Food(R.drawable.food_item_4_0, "百威啤酒","Beer",5,"微辣｜鸡肉", "月售363   好评度98%","一听"),
                    new Item.Food(R.drawable.food_item_4_1, "酸梅汤","Soup",4,"微辣｜鸡肉", "月售337   好评度96%","一杯"),
            },
    };

    private static class  MyHandler extends Handler {
        public void handleMessage(Message mesg) {
            throw new RuntimeException();
        }
    }
    Handler myHandler ;

    void InitFoods() {
        foodMenuLists = new ArrayList<>() ;
        refMap = new HashMap<>() ;
        String[] names = {"好多肉", "小凉菜", "西式快餐", "主食", "饮品"};
        for (int i = 0; i < names.length; ++i) {
            foodClassificationList.add(new Item.Table()
                    .setName(names[i]).setIcon("classification" + i).toHashMap());
        }
        for (Item.Food[] food : foods) {
            ArrayList<Map<String, Object>> list = new ArrayList<>();
            int id = 0 ;
            for (Item.Food value : food) {
                list.add(value.getHashMap());
                refMap.put(value.name + "¥" + value.price,id ++) ;
            }
            foodMenuLists.add(list);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myHandler = new MyHandler() ;
        tabId = 0 ;
        foodClassificationView = findViewById(R.id.lv_food_classification);
        foodMenuView = findViewById(R.id.lv_food_menu);
        tvCost = findViewById(R.id.tv_cost);
        tvCost.setText("¥0.0");
        foodClassificationList = new ArrayList<>();
        items = new HashMap<>();
        InitFoods();
        foodMenuList = new ArrayList<>() ;
        foodMenuList.addAll(foodMenuLists.get(0));
        foodClassificationAdapter = new SimpleAdapter(this,
                foodClassificationList,
                R.layout.food_classification_item,
                new String[]{"name", "icon"},
                new int[]{R.id.tv_classification_name, R.id.img_classification_icon});
        foodClassificationView.setAdapter(foodClassificationAdapter);
        foodClassificationAdapter.notifyDataSetChanged();
        foodMenuAdapter = new SimpleAdapter(this,
                foodMenuList,
                R.layout.food_menu_item,
                new String[]{"name", "label", "sales", "description", "addimg", "subimg", "image","count"},
                new int[]{R.id.tv_food_name, R.id.tv_food_label, R.id.tv_food_sales, R.id.tv_food_description, R.id.img_food_add, R.id.img_food_sub, R.id.img_food_image,R.id.tv_food_count}
        );
        foodMenuView.setAdapter(foodMenuAdapter);
        foodMenuAdapter.notifyDataSetChanged();
        foodClassificationView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Toast.makeText(MainActivity.this, String.format("pos == %d, id == %d",position,id), Toast.LENGTH_SHORT).show();
                tabId = position ;
                foodMenuList.clear();
                foodMenuList.addAll(foodMenuLists.get(position)) ;
                foodMenuAdapter.notifyDataSetChanged();
            }
        });

        try {
            bluetoothControl = new BluetoothControl();
            bluetoothControl.write("Test\r\n") ;
            bluetoothControl.setCallback(new Callback() {
                @Override
                public void onRecieve(InputStream istream) {
                    AlertDialog.Builder builder  = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("提示:" ) ;
                    builder.setMessage("您点的餐已经做好了,请到柜台取餐!") ;
                    builder.setPositiveButton("知道了", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Message m = myHandler.obtainMessage();
                            myHandler.sendMessage(m);
                        }
                    });
                    builder.show();
                    try {
                        Looper.loop();
                    } catch (Exception ignored) {

                    }
                }
            });
            Toast.makeText(this, "蓝牙连接成功!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.d("MAIN-DEBUG",e.toString()) ;
            AlertDialog.Builder builder  = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("蓝牙连接错误" ) ;
            builder.setMessage(e.toString()) ;
            builder.setPositiveButton("确定" ,  null );
            builder.show();
        }

    }

    public void selectOk(View view) {
        String buff = "";
        for (HashMap.Entry<String, Integer> entry :
                items.entrySet()) {
            buff = buff + (entry.getKey() + ": " + entry.getValue() + "\n");
        }
        buff = buff + "tot: " + totCost + " $.";
        Toast.makeText(this, buff, Toast.LENGTH_SHORT).show();
        try {
            bluetoothControl.write(buff+"\n") ;
        } catch (Exception e) {
            AlertDialog.Builder builder  = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("发送菜单错误" ) ;
            builder.setMessage(e.toString()) ;
            builder.setPositiveButton("确定" ,  null );
            builder.show();
            e.printStackTrace();
        }
        Log.d("MAIN-DEBUG",buff);
    }

    @SuppressLint("SetTextI18n")
    public void foodItemSub(View view) {
       view = (View) view.getParent().getParent();
       TextView tvName = view.findViewById(R.id.tv_food_name) ;
       ///Toast.makeText(this, tvName.getText()+","+refMap.get(tvName.getText().toString()), Toast.LENGTH_SHORT).show();
       int id = Integer.parseInt(String.valueOf(refMap.get(tvName.getText().toString()))) ;
       Item.Food food = foods[tabId][id] ;
       String name = food.getEnName()+"$"+food.getPrice() ;
       if(food.getCount() == 0) {
           return ;
       }
       food.sub() ;
       totCost -= food.getPrice();
       if(food.getCount() == 0) {
           items.remove(name) ;
       } else {
           items.put(name,food.getCount()) ;
       }
       tvCost.setText("¥"+totCost);
       foodMenuAdapter.notifyDataSetChanged();
    }

    @SuppressLint("SetTextI18n")
    public void foodItemAdd(View view) {
        view = (View) view.getParent().getParent();
        TextView tvName = view.findViewById(R.id.tv_food_name) ;
        ///Toast.makeText(this, tvName.getText()+","+refMap.get(tvName.getText().toString()), Toast.LENGTH_SHORT).show();
        int id = Integer.parseInt(String.valueOf(refMap.get(tvName.getText().toString()))) ;
        Item.Food food = foods[tabId][id] ;
        String name = food.getEnName()+"$"+food.getPrice() ;
        food.add() ;
        totCost += food.getPrice();
        tvCost.setText("¥"+totCost);
        items.put(name,food.getCount());
        //Log.d("MAIN_DEBUG",""+foods[tabId][id].getCount()+foodMenuList.get(id).get("count")) ;
        foodMenuAdapter.notifyDataSetChanged();
    }

}