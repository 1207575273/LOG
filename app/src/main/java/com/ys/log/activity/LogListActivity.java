package com.ys.log.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.ys.log.R;
import com.ys.log.entity.MyLog;
import com.ys.log.service.LogService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogListActivity extends AppCompatActivity {

    private LogService logService;
    private List<MyLog> myLogList;
    private ListView LogList_listview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_list);
        initView();
    }


    private void initView() {
        LogList_listview = (ListView) findViewById(R.id.LogList_listview);
        logService = new LogService(this);
        //myLogList = logService.getLog2();
        myLogList = logService.getLog();
        // 设配器加载资源  vwlogdate 字符数据匹配加载，   id 是xml资源加载
        SimpleAdapter sa = new SimpleAdapter(this, getDate(), R.layout.item_add_log, new String[]{"vwlogtitle", "vwlogdate"}, new int[]{R.id.vwlogtitle, R.id.vwlogdate});
        LogList_listview.setAdapter(sa);//绑定到数据listview
    }

    //获取数据 数据源
    private List<? extends Map<String, ?>> getDate() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (MyLog newMylog : myLogList) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("vwlogtitle", newMylog.getTitle());
            map.put("vwlogdate", newMylog.getCdate());
            list.add(map);
        }

        return list;
    }

    /**
     * 创建 子按钮
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);//加载菜单布局
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_item:
                Intent intent = new Intent(this, AddLogActivity.class);
                startActivity(intent);
                break;
            case R.id.update_item:
                Intent intent2 = new Intent(this, UpdatePassActivity.class);
                startActivity(intent2);

                break;
            default:
                Toast.makeText(this, "操作有误！", Toast.LENGTH_SHORT).show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
