package com.ys.log.service;

import android.content.Context;

import com.ys.log.entity.MyLog;
import com.ys.log.until.GetDateUtil;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Date:2019/12/31
 * Time:16:18
 * author:Mr_YANG
 */
public class LogService {
    private Context ctx;

    public LogService(Context ctx) {
        this.ctx = ctx;
    }

    //测试数据
    public List<MyLog> getLog2(){
        List<MyLog> list=new ArrayList<MyLog>();
        //面向对象思想：封装
        MyLog ml=new MyLog();
        ml.setTitle("小明结婚了");
        ml.setContext("1231111");
        //new Date():当前系统的时间
        ml.setCdate(GetDateUtil.getDate_(new Date()));
        MyLog m2=new MyLog();
        m2.setTitle("小红结婚了");
        m2.setContext("666666666");
        //new Date():当前系统的时间
        m2.setCdate(GetDateUtil.getDate_(new Date()));
        //装载对象
        list.add(ml);
        list.add(m2);
        return list;
    }

    /**
     * IO 流 读文件
     * @return list集合
     */
    public  List<MyLog> getLog(){
        List<MyLog> list = new ArrayList<>();
        try {
            FileInputStream fis =this.ctx.openFileInput("logs");//文件字节流
            InputStreamReader isr = new InputStreamReader(fis);//字符流
            BufferedReader br =new BufferedReader(isr);
            String str=br.readLine();
            while (str!=null){
                //协议，逗号
                String [] sty =str.split(",");
                MyLog myLog=new MyLog();
                myLog.setTitle(sty[0]);
                myLog.setContext(sty[1]);
                myLog.setCdate(sty[2]);
                list.add(myLog);

                str=br.readLine();
            }
            //先开后关
            br.close();
            isr.close();
            fis.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    return  list;
    }

    /**
     * @see把app端的MyLog对象接收后进行存储
     * @paramMyLog 类
     * */
    public void addLog(MyLog ml){
        List<MyLog> list= getLog();
        list.add(ml);
        try {
            //name:文件名称  model:文件读和写的模式(私有的)
            FileOutputStream fos= this.ctx.openFileOutput("logs", this.ctx.MODE_PRIVATE);
            for(MyLog mg:list){
                //\r ----转义
                String str=mg.getTitle()+","+mg.getContext()+","+mg.getCdate()+"\r\n";
                fos.write(str.getBytes());
            }
            fos.flush();//刷新
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
