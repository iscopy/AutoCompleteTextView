package com.win.autocompletetextview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

    private AutoCompleteTextView phone;
    private EditText pswd;
    private Button sign_in;
    private String[] array;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phone = (AutoCompleteTextView) findViewById(R.id.phone);

        array = new String[3];
        array[0] = MySharedPreferences.getPhone1(this)+"";
        array[1] = MySharedPreferences.getPhone2(this)+"";
        array[2] = MySharedPreferences.getPhone3(this)+"";

        //创建 AutoCompleteTextView 适配器 (输入提示)
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                array);
        //初始化autoCompleteTextView
        phone.setAdapter(adapter);
        //设置输入多少字符后提示，默认值为2，在此设为１
        phone.setThreshold(1);

        pswd = (EditText) findViewById(R.id.pswd);
        sign_in = (Button) findViewById(R.id.sign_in);


        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //登录判断。。。

                //这里假设登入成功了 if 里面的是判断是否登入成功（这里随便写的）
                if((phone.getText().length()+pswd.getText().length()) >= 10){
                    //判断登录的账号有没有保存
                    phones(MainActivity.this, array, phone.getText().toString());
                }
                startActivity(new Intent(MainActivity.this, Main2Activity.class));
            }
        });

    }
    //判断登录的账号有没有保存，没有就保存起来，替换一个最久没登录的
    public static void phones(Context context, String[]array, String phone){
        //当 phone 是新登录的账号的时候
        boolean trfa = (!phone.equals(array[0]) && !phone.equals(array[1]) && !phone.equals(array[2]));
        if(trfa){
            //循环一下，将最后一个替换成新的
            MySharedPreferences.setPhone3(array[1], context);
            MySharedPreferences.setPhone2(array[0], context);
            MySharedPreferences.setPhone1(phone, context);
        }
    }
}
