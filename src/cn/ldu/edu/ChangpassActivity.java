package cn.ldu.edu;

import cn.gdou.edu.R;
import cn.ldu.edu.net.LibAPI;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ChangpassActivity extends Activity {

	private Button back;
	private Button changpass;
	private EditText oldpass;
	private EditText newpass;
	private EditText renewpass;
    private Button book_back;
	private ProgressDialog mypDialog;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		back = (Button) findViewById(R.id.book_back);
		changpass = (Button) findViewById(R.id.yes);
		oldpass = (EditText) findViewById(R.id.oldpass);
		newpass = (EditText) findViewById(R.id.newpass);
		renewpass = (EditText) findViewById(R.id.renewpass);

		mypDialog = new ProgressDialog(ChangpassActivity.this);
		mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mypDialog.setTitle("修改密码");
		mypDialog.setMessage("正在修改...");
		mypDialog.setIndeterminate(false);
        book_back=(Button)findViewById(R.id.book_back);
        book_back.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                finish();
            }
        });
		back.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				ChangpassActivity.this.finish();
			}
		});

		changpass.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				changPassWord cpw=new changPassWord();
				if(oldpass.getText().toString().isEmpty()||
						newpass.getText().toString().isEmpty()||renewpass.getText().toString().isEmpty()){
					return;
				}
				cpw.execute(oldpass.getText().toString(),newpass.getText().toString(),renewpass.getText().toString());
			}
		});
	}

	class changPassWord extends AsyncTask<String, String, Void> {

		@Override
		protected void onPreExecute() {
			mypDialog.show();
			super.onPreExecute();
		}
		@Override
		protected Void doInBackground(String... params) {
			Looper.prepare();
			LibAPI lib = new LibAPI();
			SharedPreferences sp = getSharedPreferences("lib", MODE_APPEND);
			String cookies = sp.getString("cookie", "");
			int state;
			if (cookies != "") {
				lib.setCookie(cookies);
				state = lib.changPass(params[0], params[1], params[2]);
				System.out.println(state);
				if (302 == state) {
					sp.edit().clear().commit();
					mypDialog.cancel();
					startActivity(new Intent().setClass(getApplicationContext(), AndroidLIBActivity.class));
				} else if(200==state){
					mypDialog.cancel();
					startActivity(new Intent().setClass(getApplicationContext(), AndroidLIBActivity.class));
				}
			} else {

			}
			return null;
		}

	}
}
