package com.ys.log.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ys.log.R;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText ed_main_username;
    private EditText ed_main_password;
    private Button bu_mian_login;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private String sysNmae;
    private String sysPwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        ed_main_username = (EditText) findViewById(R.id.ed_main_username);
        ed_main_password = (EditText) findViewById(R.id.ed_main_password);
        bu_mian_login = (Button) findViewById(R.id.bu_mian_login);
        initSharedPreferences();
        bu_mian_login.setOnClickListener(this);

    }

    //    getSharedPreferences 保存数据是直接get八大基本类型加String  sp.getString("username","admin");
//    SharedPreferences.Editor edit = sp.edit();
//    使用编辑器 editor编辑
//    已保存的数据
//       edit.putString("username","ys");
//    //[2.6]记得把edit进行提交
//       edit.apply();
//    or commint
    private void initSharedPreferences() {
        //1 文件名 2模式 私有  发现单纯使用SharedPreferences 不使用编辑器可能无法存数据
        sp = this.getSharedPreferences("user", this.MODE_PRIVATE);
        editor = sp.edit();
        boolean count = sp.getBoolean("fist", true);
        //判断程序与第几次运行，如果是第一次就执行初始化initSharedPreferences
        if (count) {
            editor.putString("username", "admin");
            editor.putString("password", "123456");
            editor.apply();
            sysNmae = sp.getString("username", "");
            sysPwd = sp.getString("password", "");
            editor.putString(sysNmae + sysPwd, sysNmae + "," + sysPwd);//key+value证明账号密码唯一
            editor.apply();

        }
        editor.putBoolean("fist", false);
        editor.apply();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bu_mian_login:
                String inputNmae = ed_main_username.getText().toString();
                String inputPwd = ed_main_password.getText().toString();
                sysNmae = sp.getString("username", "");
                sysPwd = sp.getString("password", "");
                String index[] = sp.getString(sysNmae + sysPwd, "").split(",");//验证账号唯一性
                if (sysNmae.equals(inputNmae) && sysPwd.equals(inputPwd) && sysNmae.equals(index[0]) && sysPwd.equals(index[1])) {
                    Intent intent = new Intent(MainActivity.this, LogListActivity.class);
                    MainActivity.this.startActivity(intent);
                    Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(MainActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }


}
