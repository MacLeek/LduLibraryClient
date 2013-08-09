package cn.ldu.edu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import cn.gdou.edu.R;
import cn.ldu.edu.data.*;
import cn.ldu.edu.net.LibAPI;
import android.app.ListActivity;

public class BorrowedInfoActivity extends Activity {
	private ListView list;
    private Button book_back;
	private ProgressDialog mypDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.book_borrowed);
        System.out.println("aaaaaa");

        book_back=(Button)findViewById(R.id.book_back);
        book_back.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                finish();
            }
        });


        mypDialog = new ProgressDialog(BorrowedInfoActivity.this);
		mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mypDialog.setTitle("载入");
		mypDialog.setMessage("正在加载...");
		mypDialog.setIndeterminate(false);

        list = (ListView) findViewById(R.id.book_borrowed_list);
		downBorrowedInfo dbi=new downBorrowedInfo();
        dbi.execute();
	}

class downBorrowedInfo extends AsyncTask<Void, String, List<BookInfo>> {

		@Override
		protected void onPreExecute() {
			mypDialog.show();
			super.onPreExecute();
		}

		@Override
		protected List<BookInfo> doInBackground(Void... params) {
			// TODO Auto-generated method stub
			LibAPI lib = new LibAPI();
			SharedPreferences sp = getSharedPreferences("lib", MODE_APPEND);
			String cookie = sp.getString("cookie", "");
			if (!cookie.isEmpty()) {
				lib.setCookie(cookie);
                //System.out.println("borrowed info1");

                return lib.getBorrowedContent();
			}
			return null;
		}

		@Override
		protected void onPostExecute(List<BookInfo> result) {
			mypDialog.cancel();
			List<Map<String, String>> list_content = new ArrayList<Map<String, String>>();
			Iterator<BookInfo> it = result.iterator();
			while (it.hasNext()) {
				Map<String, String> map = new HashMap<String, String>();
				BookInfo book=it.next();
				map.put("bookname", book.getBook_writer());
				map.put("lentdate", book.getBack_time());
				map.put("borrowedtime", book.getBorrowed_time());
                map.put("booktype", book.getBook_type());
                map.put("booknumber", book.getLogin_number());
                list_content.add(map);
			}
            System.out.println("00000");

            SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),
                    list_content, R.layout.book_borrowed_list_view, new String[] { "bookname",
							"lentdate", "borrowedtime","booktype" ,"booknumber"},
							new int[] { R.id.borrowed_title,R.id.lent_date,R.id.borrowed_date,R.id.book_type,R.id.book_number});
            System.out.println("111111");

            list.setAdapter(adapter);
            System.out.println("ttttt");
			super.onPostExecute(result);
		}

	}
}
