package cn.ldu.edu;

import cn.gdou.edu.R;
import cn.ldu.edu.data.StudentInfo;
import cn.ldu.edu.net.LibAPI;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

public class LoginActivity extends Activity {
	private Button loginButton;
	private EditText number; 
	private EditText password;
	private CheckBox recd;
	private ProgressDialog mypDialog;
	private Button only_search;
    private Button buttonTest;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
		//��ʼ����½������
		mypDialog=new ProgressDialog(LoginActivity.this);
		//ʵ����
        mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //���ý�������񣬷��ΪԲ�Σ���ת��
        mypDialog.setTitle("��½");
        //����ProgressDialog ����
        mypDialog.setMessage("��½��...");
        //����ProgressDialog ��ʾ��Ϣ
//        mypDialog.setIcon(R.drawable.android);
        //����ProgressDialog ����ͼ��
//        mypDialog.setButton("Google",this);
        //����ProgressDialog ��һ��Button
        mypDialog.setIndeterminate(false);
        //����ProgressDialog �Ľ������Ƿ���ȷ
//        mypDialog.setCancelable(true);
        //����ProgressDialog �Ƿ���԰��˻ذ���ȡ��




        buttonTest=(Button)findViewById(R.id.buttonTest);
        buttonTest.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
              //  mypDialog.show();
                System.out.println("����");
                Intent intent=new Intent();
                intent.setClass(getApplicationContext(), Test.class);
                startActivity(intent);
            }
        });

        only_search=(Button)findViewById(R.id.only_search); //ֻ����������½
        only_search.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				System.out.println("����");
                Intent intent=new Intent();
				intent.setClass(getApplicationContext(), SearchActivity.class);
				startActivity(intent);
			}
		});
		recd=(CheckBox)findViewById(R.id.recd);
		number=(EditText)findViewById(R.id.number);
		password=(EditText)findViewById(R.id.pass);
		loginButton=(Button)findViewById(R.id.signin_button);
		SharedPreferences sp=getSharedPreferences("lib", MODE_APPEND);
		String user=sp.getString("user", "");      //��ȡ��ס���û���������
		String pass=sp.getString("password", "");
		if(user!=""){
			number.setText(user);
			password.setText(pass);
			recd.setChecked(true);
		}
		loginButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				mypDialog.show();
                System.out.println("��½");

                new Thread(){
					@Override
					public void run() {
						
						StudentInfo s;
						String user=number.getText().toString().trim();
						String pass=password.getText().toString().trim();
						LibAPI l=new LibAPI(user, pass);
						l.Get();      //ȡ��cookie
						l.loginURL();  //��½
						s=l.Get_User();   //����û���Ϣ
						
						if(recd.isChecked()){      //����򹳣������û�������
							SharedPreferences sp=getSharedPreferences("lib", MODE_APPEND);
							Editor edit=sp.edit();
							edit.putString("user", user);
							edit.putString("password", pass);
							edit.putString("cookie", l.getCookie());
							edit.commit();
							
						}else{                   //���򲻱����û�����
							SharedPreferences sp=getSharedPreferences("lib", MODE_APPEND);
							sp.edit().clear();
						}
						
//						System.out.println(s);
//						System.out.println(s.getName());
						Intent intent=new Intent();
						intent.setClass(getApplicationContext(), AndroidLIBActivity.class);
						intent.putExtra("name", s.getName());
						startActivity(intent);
						
						Looper.prepare();
					}
				}.start();
				Looper.loop();
//				mypDialog.show();
				/*StudentInfo s;
				String user=number.getText().toString().trim();
				String pass=password.getText().toString().trim();
				LibAPI l=new LibAPI(user, pass);
				l.Get();
				l.loginURL();
				s=l.Get_User();
				
				if(recd.isChecked()){
					SharedPreferences sp=getSharedPreferences("lib", MODE_APPEND);
					Editor edit=sp.edit();
					edit.putString("user", user);
					edit.putString("password", pass);
					edit.putString("cookie", l.getCookie());
					edit.commit();
					
				}else{
					SharedPreferences sp=getSharedPreferences("lib", MODE_APPEND);
					sp.edit().clear();
				}
				
//				System.out.println(s);
//				System.out.println(s.getName());
				Intent intent=new Intent();
				intent.setClass(getApplicationContext(), AndroidLIBActivity.class);
				intent.putExtra("name", s.getName());
				startActivity(intent);*/
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
        //����ProgressDialog ��ʾ��Ϣ
//        mypDialog.setIcon(R.drawable.android);
        //����ProgressDialog ����ͼ��
//        mypDialog.setButton("Google",this);
        //����ProgressDialog ��һ��Button
        mypDialog.setIndeterminate(false);
        //����ProgressDialog �Ľ������Ƿ���ȷ
//        mypDialog.setCancelable(true);
        //����ProgressDialog �Ƿ���԰��˻ذ���ȡ��
	}
	@Override
	protected void onStop() {
		this.finish();
		mypDialog.cancel();
		super.onStop();
	}
}
