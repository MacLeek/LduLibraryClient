package cn.ldu.edu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.gdou.edu.R;
import cn.ldu.edu.data.SearchBookAddress;
import cn.ldu.edu.net.LibAPI;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class Search_book_info extends Activity {

	private Button back;
	private ListView list;
	private TextView bookname;
	private TextView book_search_number;
	private TextView book_publish;
	private Intent intent;
	private List<Map<String, String>> datelist;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.book_info);
		intent=getIntent();
		back=(Button)findViewById(R.id.book_back);
		back.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Search_book_info.this.setResult(RESULT_OK);
				Search_book_info.this.finish();
			}
		});
		bookname=(TextView)findViewById(R.id.book_name);
		book_search_number=(TextView)findViewById(R.id.book_search_number);
		book_publish=(TextView)findViewById(R.id.book_publish);
		bookname.setText(intent.getStringExtra("bookname"));
		book_search_number.setText(intent.getStringExtra("date"));
		book_publish.setText(intent.getStringExtra("publish"));
		list=(ListView)findViewById(R.id.book_address_list);
		
		downBookAddressInfo dom=new downBookAddressInfo();
		dom.execute();
		
		
		
	}
	
	class downBookAddressInfo extends AsyncTask<Void, ListView, List<SearchBookAddress>>{

		@Override
		protected List<SearchBookAddress> doInBackground(Void... params) {
			LibAPI lib=new LibAPI();
			return lib.getBookInfo(intent.getStringExtra("number"));
		}
		@Override
		protected void onPostExecute(List<SearchBookAddress> result) {
			datelist=new ArrayList<Map<String,String>>();
			Iterator<SearchBookAddress> it=result.iterator();
			while(it.hasNext()){
				Map<String,String> map=new HashMap<String, String>();
				SearchBookAddress book=it.next();
				map.put("address", book.getAddress());
				map.put("islent", book.getIslent());
				datelist.add(map);
			}
			SimpleAdapter adapter=new SimpleAdapter(getApplicationContext(), datelist, R.layout.book_info_list_view
					, new String[]{"address","islent"}, 
					new int[]{R.id.address,R.id.islent});
			list.setAdapter(adapter);
			super.onPostExecute(result);
		}
	}
}
