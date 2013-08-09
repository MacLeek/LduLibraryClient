package cn.ldu.edu;

import cn.gdou.edu.R;
import cn.ldu.edu.data.StudentInfo;
import cn.ldu.edu.net.LibAPI;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

public class AndroidLIBActivity extends BaseActivity {
	private TextView book_number;// 证号
	private TextView user_location;// 用户名
	private TextView user_url;// 当前状态
	private TextView state;// 类型
	private TextView position;// 单位
	private TextView call_number;
	private TextView cell_phone;
	private TextView address;
	private TextView emaill;
	private TextView other;
	private TextView money;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);	
		initOnCreate();
		book_number = (TextView) findViewById(R.id.textView2);
		user_location = (TextView) findViewById(R.id.user_location);
		user_url = (TextView) findViewById(R.id.user_url);
		state = (TextView) findViewById(R.id.state_content);
		position = (TextView) findViewById(R.id.position_content);
		call_number = (TextView) findViewById(R.id.call_number_content);
		cell_phone = (TextView) findViewById(R.id.cellphone_content);
		address = (TextView) findViewById(R.id.address_content);
		emaill = (TextView) findViewById(R.id.email_content);
		other = (TextView) findViewById(R.id.other_content);
		money = (TextView) findViewById(R.id.money_content);
		
		downUserInfo dui=new downUserInfo();
		dui.execute();
	}

	class downUserInfo extends AsyncTask<Void, String, StudentInfo> {

		@Override
		protected StudentInfo doInBackground(Void... params) {
			SharedPreferences sp = getSharedPreferences("lib", MODE_APPEND);
			String cookie = sp.getString("cookie", "");
			if (cookie != "") {
				LibAPI lib = new LibAPI();
				lib.setCookie(cookie);
				System.out.println(cookie);
				return lib.Get_User();
			} else {
				return null;
			}
		}

		@Override
		protected void onPostExecute(StudentInfo result) {
			if (result != null) {
				book_number.setText(result.getNumber());
				user_location.setText(result.getName());
				user_url.setText(result.getState());
				state.setText(result.getType());
				position.setText(result.getUnits());
				call_number.setText(result.getTele());
				cell_phone.setText(result.getMobile());
				address.setText(result.getAddress());
				emaill.setText(result.getEmail());
				other.setText(result.getOther());
				money.setText(result.getMoney());
			}
			super.onPostExecute(result);
		}
	}
}