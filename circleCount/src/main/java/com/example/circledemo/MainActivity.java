package com.example.circledemo;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	private CircleBar circleBar;
	private EditText setnum;
	private Button setnumbutton;
	int i = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		circleBar = (CircleBar) findViewById(R.id.circle);
		setnum = (EditText) findViewById(R.id.setnum);
		setnumbutton = (Button) findViewById(R.id.setnumbutton);
		circleBar.setMaxstepnumber(10000);
		setnumbutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				/**
				 * 点击设置步数
				 */
				circleBar.update(Integer.parseInt(setnum.getText().toString()),
						700);
			}
		});

	}

}
