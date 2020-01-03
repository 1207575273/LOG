package com.ys.log.comm;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

   /* getSharedPreferences 保存数据是直接get八大基本类型加String sp.getString("username","admin");
            SharedPreferences.Editor edit=sp.edit();
            使用编辑器 editor编辑已保存的数据
            edit.putString("username","ys");
            //[2.6]记得把edit进行提交
            edit.apply();
            or commint*/

/**
 * Date:2019/12/30
 * Time:16:48
 * author:Mr_YANG
 */
public class SharedHelper {
    private Context mContext;


    public SharedHelper() {
    }

    public SharedHelper(Context mContext) {
        this.mContext = mContext;
    }


    //存 SharedPreferences
    public void save(String key, String values) {
        SharedPreferences sp = mContext.getSharedPreferences("mysp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, values);
        editor.apply();
        Toast.makeText(mContext, "信息已写入"+values, Toast.LENGTH_SHORT).show();
    }
    //取（读）SharedPreferences key
    public String read(String key) {
        SharedPreferences sp = mContext.getSharedPreferences("mysp", Context.MODE_PRIVATE);
        return sp.getString(key, "");
    }
}
