package cn.ldu.edu;

import cn.gdou.edu.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by happylyang on 13-5-31.
 */
public class About extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);


    Button okBtn = (Button) findViewById(R.id.ok_btn);
    okBtn.setOnClickListener(new Button.OnClickListener() {

        public void onClick(View v) {
            finish();
        }
    });
}
}