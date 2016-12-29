package com.example.mynfcdemon;

import android.app.Activity;
import android.nfc.NfcAdapter;
import android.os.Bundle;

public class MainActivity extends Activity {

	
	//private NfcAdapter nfcAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//nfcAdapter = NfcAdapter.getDefaultAdapter(this); 
		//运行APK  把标签靠近识别区即可。
	}

	

}
