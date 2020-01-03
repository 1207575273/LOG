package com.ys.log.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.ys.log.R;
import com.ys.log.entity.MyLog;
import com.ys.log.service.LogService;
import com.ys.log.until.GetDateUtil;

import java.util.Date;

public class AddLogActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText ed_title_addlog;
    private EditText ed_context_addlog;
    private Button bu_add_save;
    private LogService logService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_log);
        initView();
    }

    private void initView() {
        ed_title_addlog = (EditText) findViewById(R.id.ed_title_addlog);
        ed_context_addlog = (EditText) findViewById(R.id.ed_context_addlog);
        bu_add_save = (Button) findViewById(R.id.bu_add_save);
        bu_add_save.setOnClickListener(this);
        logService=new LogService(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bu_add_save:
                String title =ed_title_addlog.getText().toString();
                String context=ed_context_addlog.getText().toString();
                MyLog ml =new MyLog();
                ml.setTitle(title);
                ml.setContext(context);
                ml.setCdate(GetDateUtil.getDate_(new Date()));
                logService.addLog(ml);

                Intent intent=new Intent(AddLogActivity.this,LogListActivity.class);
                AddLogActivity.this.startActivity(intent);//跳转


                break;
        }
    }
}
