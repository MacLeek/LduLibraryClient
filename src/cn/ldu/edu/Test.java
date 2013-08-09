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
                        // l.Get();      //ȡ��cookie
                        //  l.loginURL();  //��½
                        //  s=l.Get_User();   //����û���Ϣ
                        System.out.println("is sending");
                        l.sendTreeHole(tweet_edit.getText().toString());
                        System.out.println("sent");
                        //	System.out.println(s);
                        //	System.out.println(s.getName());

                        //  Looper.prepare();
                    }
                }.start();
                Toast.makeText(Test.this, "�Ѿ��ɹ�����!", 1).show();
                tweet_edit.setText("");
                System.out.println("����!");
            }
        });
    }
    public void setDialog(ProgressDialog mypDialog){
        //ʵ����
        mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //���ý�������񣬷��ΪԲ�Σ���ת��
        mypDialog.setTitle("��½");
        //����ProgressDialog ����
        mypDialog.setMessage("��½��...");

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