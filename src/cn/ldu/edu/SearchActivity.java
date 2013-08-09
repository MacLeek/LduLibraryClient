package cn.ldu.edu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.gdou.edu.R;
import cn.ldu.edu.data.SearchBook;
import cn.ldu.edu.data.StudentInfo;
import cn.ldu.edu.net.JsoupUtils;
import cn.ldu.edu.net.LibAPI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.os.AsyncTask;
import android.os.AsyncTask;

public class SearchActivity extends BaseActivity {

	private ListView list;
	private EditText search_edit;
	private LibAPI lib;
	private ProgressDialog mypDialog;
	private List<Map<String, String>> datelist;
	private Button top_back;
	private Button next;
	private Button pre;
	private TextView sum_number;
	private TextView page_number;
    private TextView search_res;
	private double sum = 0;
	private int n = 1;
    private String ss;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);

		sum_number = (TextView) findViewById(R.id.sum_number);
		page_number = (TextView) findViewById(R.id.page_number);

		next = (Button) findViewById(R.id.next);
		pre = (Button) findViewById(R.id.pre);

		mypDialog = new ProgressDialog(SearchActivity.this);
		mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mypDialog.setTitle("搜索");
		mypDialog.setMessage("正在查找...");
		mypDialog.setIndeterminate(false);

		top_back = (Button) findViewById(R.id.top_back);
        top_back.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                finish();
            }
        });
		// 下一页

		// 前一页

		//list = (ListView) findViewById(R.id.search_section_list);
		search = (ImageButton) findViewById(R.id.search);
		search_edit = (EditText) findViewById(R.id.search_edit);
        search_res=(TextView) findViewById(R.id.tushu);

        search.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
                Toast.makeText(SearchActivity.this, "请耐心等待", 1).show();

                    loadBookInfo load = new loadBookInfo();
                    load.execute(search_edit.getText().toString().trim());
                System.out.println("3");

                // lib=new LibAPI();
                    // lib.searchBook("ANYWORDS",
                    // search_edit.getText().toString().trim());


            //    new Thread(){
              //      @Override
                //    public void run() {

                        //StudentInfo s;
                        //String user=number.getText().toString().trim();
                        //String pass=password.getText().toString().trim();
                        LibAPI l=new LibAPI();
                        // l.Get();      //取得cookie
                        //  l.loginURL();  //登陆
                        //  s=l.Get_User();   //获得用户信息
                      //  System.out.println("is sending");
                //search_res.setText("请耐心等待");

                        ss=l.searchBook(search_edit.getText().toString());


                        //System.out.println(res);
                        //	System.out.println(s);
                        //	System.out.println(s.getName());

                        //  Looper.prepare();
                 //   }
               // }.start();
              //  Toast.makeText(SearchActivity.this, ss, 1).show();

                //loadBookInfo load = new loadBookInfo();
				//load.execute(search_edit.getText().toString().trim());
				// lib=new LibAPI();
			// lib.searchBook("ANYWORDS",
				// search_edit.getText().toString().trim());
			}
		});
        //search_res.setText(ss);

    }



    class loadBookInfo extends AsyncTask<String, ListView, List<SearchBook>> {

        @Override
        protected void onPreExecute() {
            InputMethodManager im = (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(getCurrentFocus()
                    .getApplicationWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
            // getWindow().addFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            mypDialog.show();
            super.onPreExecute();
        }

        @Override
        protected List<SearchBook> doInBackground(String... params) {
            lib = new LibAPI();
            List<SearchBook> list=new ArrayList<SearchBook>();
            ss=lib.searchBook(params[0]);
            System.out.println(ss);
            SearchBook book=new SearchBook();
            book.setName(ss);
            list.add(book);
            System.out.println("1");

            return list;
        }

        @Override
        protected void onPostExecute(List<SearchBook> result) {


            mypDialog.cancel();
            //Toast.makeText(SearchActivity.this, ss, 1).show();
            search_res.setText(ss);

            datelist = new ArrayList<Map<String, String>>();
            Iterator<SearchBook> it = result.iterator();
            while (it.hasNext()) {
                Map<String, String> map = new HashMap<String, String>();
                SearchBook book = it.next();
                map.put("title", book.getName());

                datelist.add(map);
            }
            System.out.println("2");

            SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),
                    datelist, R.layout.search_view, new String[] { "title"}, new int[] { R.id.Title });
           // list.setAdapter(adapter);


/*            list.setOnItemClickListener(new OnItemClickListener() {

                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    Intent intent = new Intent();
                    intent.putExtra("number", ((TextView) view
                            .findViewById(R.id.system_number)).getText());
                    intent.putExtra("bookname", ((TextView) view
                            .findViewById(R.id.Title)).getText());
                    intent.putExtra("date",
                            ((TextView) view.findViewById(R.id.date)).getText());
                    intent.putExtra("publish", ((TextView) view
                            .findViewById(R.id.publish)).getText());
                    intent.setClass(getApplicationContext(),
                            Search_book_info.class);
                    startActivityForResult(intent, RESULT_OK);
                }

            });*/
            super.onPostExecute(result);
        }

    }

}
/*

		@Override
		protected void onPostExecute(List<SearchBook> result) {

            LibAPI l=new LibAPI();
            // l.Get();      //取得cookie
            //  l.loginURL();  //登陆
            //  s=l.Get_User();   //获得用户信息
            System.out.println("is sending");
            //search_res.setText("请耐心等待");

            ss=l.searchBook(search_edit.getText().toString());

				Map<String, String> map = new HashMap<String, String>();
				map.put("结果", ss);

				datelist.add(map);


			SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),
					datelist, R.layout.search_view, new String[] { "结果"
							}, new int[] { R.id.tushu});
			list.setAdapter(adapter);

			super.onPostExecute(result);
		}

	*/



