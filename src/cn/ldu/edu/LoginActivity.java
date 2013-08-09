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
		//初始化登陆进度条
		mypDialog=new ProgressDialog(LoginActivity.this);
		//实例化
        mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //设置进度条风格，风格为圆形，旋转的
        mypDialog.setTitle("登陆");
        //设置ProgressDialog 标题
        mypDialog.setMessage("登陆中...");
        //设置ProgressDialog 提示信息
//        mypDialog.setIcon(R.drawable.android);
        //设置ProgressDialog 标题图标
//        mypDialog.setButton("Google",this);
        //设置ProgressDialog 的一个Button
        mypDialog.setIndeterminate(false);
        //设置ProgressDialog 的进度条是否不明确
//        mypDialog.setCancelable(true);
        //设置ProgressDialog 是否可以按退回按键取消




        buttonTest=(Button)findViewById(R.id.buttonTest);
        buttonTest.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
              //  mypDialog.show();
                System.out.println("树洞");
                Intent intent=new Intent();
                intent.setClass(getApplicationContext(), Test.class);
                startActivity(intent);
            }
        });

        only_search=(Button)findViewById(R.id.only_search); //只搜索，不登陆
        only_search.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				System.out.println("搜索");
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
		String user=sp.getString("user", "");      //读取记住的用户名和密码
		String pass=sp.getString("password", "");
		if(user!=""){
			number.setText(user);
			password.setText(pass);
			recd.setChecked(true);
		}
		loginButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				mypDialog.show();
                System.out.println("登陆");

                new Thread(){
					@Override
					public void run() {
						
						StudentInfo s;
						String user=number.getText().toString().trim();
						String pass=password.getText().toString().trim();
						LibAPI l=new LibAPI(user, pass);
						l.Get();      //取得cookie
						l.loginURL();  //登陆
						s=l.Get_User();   //获得用户信息
						
						if(recd.isChecked()){      //如果打钩，保存用户名密码
							SharedPreferences sp=getSharedPreferences("lib", MODE_APPEND);
							Editor edit=sp.edit();
							edit.putString("user", user);
							edit.putString("password", pass);
							edit.putString("cookie", l.getCookie());
							edit.commit();
							
						}else{                   //否则不保存用户密码
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
		//实例化
        mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //设置进度条风格，风格为圆形，旋转的
        mypDialog.setTitle("登陆");
        //设置ProgressDialog 标题
        mypDialog.setMessage("登陆中...");
        //设置ProgressDialog 提示信息
//        mypDialog.setIcon(R.drawable.android);
        //设置ProgressDialog 标题图标
//        mypDialog.setButton("Google",this);
        //设置ProgressDialog 的一个Button
        mypDialog.setIndeterminate(false);
        //设置ProgressDialog 的进度条是否不明确
//        mypDialog.setCancelable(true);
        //设置ProgressDialog 是否可以按退回按键取消
	}
	@Override
	protected void onStop() {
		this.finish();
		mypDialog.cancel();
		super.onStop();
	}
}
