package cn.ldu.edu;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cn.gdou.edu.R;
import cn.ldu.edu.data.StudentInfo;
import cn.ldu.edu.net.LibAPI;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by happylyang on 13-5-30.
 */
public class Test extends Activity {
    private Button top_send_btn;
    private Button top_back;
    private ProgressDialog mypDialog;
    private EditText tweet_edit;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write);
        top_send_btn=(Button)findViewById(R.id.top_send_btn);
        tweet_edit=(EditText)findViewById(R.id.tweet_edit);

        top_back=(Button)findViewById(R.id.top_back);
        top_back.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                finish();
            }
        });

        top_send_btn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                new Thread(){
                    @Override
                    public void run() {

                        StudentInfo s;
                        //String user=number.getText().toString().trim();
                        //String pass=password.getText().toString().trim();
                        LibAPI l=new LibAPI();
                        // l.Get();      //取得cookie
                        //  l.loginURL();  //登陆
                        //  s=l.Get_User();   //获得用户信息
                        System.out.println("is sending");
                        l.sendTreeHole(tweet_edit.getText().toString());
                        System.out.println("sent");
                        //	System.out.println(s);
                        //	System.out.println(s.getName());

                        //  Looper.prepare();
                    }
                }.start();
                Toast.makeText(Test.this, "已经成功发送!", 1).show();
                tweet_edit.setText("");
                System.out.println("发送!");
            }
        });
    }
    public void setDialog(ProgressDialog mypDialog){
        //实例化
        mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //设置进度条风格，风格为圆形，旋转的
        mypDialog.setTitle("登陆");
        //设置ProgressDialog 标题
        mypDialog.setMessage("登陆中...");

        mypDialog.setIndeterminate(false);

    }
    /*
    @Override
    protected void onStop() {
        this.finish();
        mypDialog.cancel();
        super.onStop();
    }*/
}