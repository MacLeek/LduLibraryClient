package cn.ldu.edu;



import cn.gdou.edu.R;
import cn.ldu.edu.data.MenuDialog;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class BaseActivity extends Activity{


	protected ImageButton login;
	protected ImageButton search;
	protected TextView title;
	protected MenuDialog dialog;
	
	public void initOnCreate(){
		 title=(TextView)findViewById(R.id.title);
	        title.setOnClickListener(new OnClickListener() {			
				public void onClick(View v) {
					int top = title.getTop();
					int height = title.getHeight();
					int x = top + height;
					if (null == dialog) {
						dialog = new MenuDialog(BaseActivity.this);
						dialog.bindEvent(BaseActivity.this);
						dialog.setPosition(-1, x);
					}

					// toggle dialog
					if (dialog.isShowing()) {
						dialog.dismiss(); // 没机会触发
					} else {
						dialog.show();
                        System.out.println("menu show");
                    }
				}
			});
	        
	        Intent intent=getIntent();
	        if(intent!=null){
	        	title.setText(intent.getStringExtra("name"));
	        }
	        search=(ImageButton)findViewById(R.id.search);
	        login=(ImageButton)findViewById(R.id.writeMessage);
	        login.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					
					Intent intent=new Intent();
			        intent.setClass(getApplicationContext(), LoginActivity.class);
			        startActivity(intent);
					
					/*SharedPreferences sp=getSharedPreferences("lib", MODE_APPEND);
					String cookie=sp.getString("cookie", "");							
					if(cookie!=""){
						Toast.makeText(getApplicationContext(), "已经登陆", Toast.LENGTH_SHORT).show();
					}else{
					Intent intent=new Intent();
			        intent.setClass(getApplicationContext(), LoginActivity.class);
			        startActivity(intent);
					}*/
				}
			});
	        search.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					Intent intent=new Intent();
					intent.setClass(getApplicationContext(), SearchActivity.class);
					startActivity(intent);
				}
			});
	}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);  
    }
    
   
    
    @Override
    protected void onStop() {
    	super.onStop();
    }
}
