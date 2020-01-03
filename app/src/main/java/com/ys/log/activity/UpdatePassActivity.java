package com.ys.log.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ys.log.R;

public class UpdatePassActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText ed_updateac_name;
    private EditText ed_updateac_psw1, ed_updateac_psw2;
    private Button bu_updateac_save;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pass);
        initView();
    }

    private void initView() {
        ed_updateac_name = (EditText) findViewById(R.id.ed_updateac_name);
        ed_updateac_psw1 = (EditText) findViewById(R.id.ed_updateac_psw1);
        ed_updateac_psw2 = (EditText) findViewById(R.id.ed_updateac_psw2);
        bu_updateac_save = (Button) findViewById(R.id.bu_updateac_save);
        //1 文件名 2模式 私有
        sp = this.getSharedPreferences("user", this.MODE_PRIVATE);
        bu_updateac_save.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bu_updateac_save:
                String name = ed_updateac_name.getText().toString();
                String psw1 = ed_updateac_psw1.getText().toString();
                String psw2 = ed_updateac_psw2.getText().toString();
                /*获得正确的用户名与密码*/
                String sysNmae = sp.getString("username", "");
                String sysPwd = sp.getString("password", "");
                String Only_name_psw = sp.getString(sysNmae + sysPwd, "");//获得账号密码唯一码
                /*   System.out.println("sssssssssssssss" + sysNmae + "\t" + sysPwd + "\t" + Only_name_psw);*/
                if (Check_REG(name, psw1, psw2, Only_name_psw, sysNmae, sysPwd)) {
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putString("password", psw2);//这里根据业务流程不需要更改用户名
                    //为了保持唯一码 可以重构，这里给出一种方案，精准删除之前的唯一码在新建唯一码
                    edit.remove(sysNmae + psw1);//删除
                    edit.apply();
                    edit.putString(name + psw2, name + "," + psw2); //唯一码格式 keyvalue  --  key,value
                    edit.apply();
                    ToastUntils("修改成功");
                }


                break;
        }
    }

    /**
     * 简单检查账号与密码
     *
     * @param name          账号
     * @param psw1          旧密码
     * @param psw2          新密码
     * @param Only_name_psw 唯一码---账号密码
     * @return
     */
    private boolean Check_REG(String name, String psw1, String psw2, String Only_name_psw, String sysNmae, String sysPwd) {


        if (TextUtils.isEmpty(name)) {
            ToastUntils("账号不能为空");

            return false;
        }

        if (TextUtils.isEmpty(psw1) | TextUtils.isEmpty(psw2)) {
            ToastUntils("密码不能为空");
            ed_updateac_name.findFocus();
            return false;
        }

        String regex = "^[a-zA-Z0-9]{6,10}";//只能包含大小写和数字
        if (psw1.matches(regex) != true | psw2.matches(regex) != true) {
            ToastUntils("密码在A-Z和0-9中设置，且长度在6-10之间");
            ed_updateac_name.findFocus();

            return false;
        }
        if (psw1.equals(psw2)) {
            ToastUntils("新密码与原密码不能一样！！！");
            return false;
        }
        if (TextUtils.isEmpty(Only_name_psw)) {
            ToastUntils("查询不到请检查账户与密码是否有误！！！");
            return false;
        }
        String index[] = Only_name_psw.split(",");
        if (!index[0].equals(name)) {
            ToastUntils("账户不存在");
            return false;
        }
        if (!index[1].equals(psw1)) {
            ToastUntils("原始密码不正确");
            return false;
        }


        return true;
    }

    /**
     * 简单封装 Toast 提示
     * @param Message
     */
    private  void ToastUntils(String Message){
        Toast.makeText(this,Message,Toast.LENGTH_SHORT).show();
    }
}
