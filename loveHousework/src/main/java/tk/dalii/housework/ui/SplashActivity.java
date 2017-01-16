package tk.dalii.housework.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import tk.dalii.housework.QManager;
import tk.dalii.housework.R;
/**
 * @author yuxuehai
 */
public class SplashActivity extends Activity {
	//splash 显示时间
	private final int SPLASH_DISPLAY_LENGTH = 2000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_view);
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				//开始广告
				startPush(SplashActivity.this);
				//开启主activity--finish
				Intent mainIntent = new Intent(SplashActivity.this,MainActivity.class);
				SplashActivity.this.startActivity(mainIntent);
				SplashActivity.this.finish();
			}
		}, SPLASH_DISPLAY_LENGTH);
	}
	/**
	 * 开始push广告
	 * @param mContext
	 */
	public void startPush(Context mContext){
		//获取实例--设置产品id--设置渠道id--开启广告
		QManager push = QManager.getInstance(mContext);
		push.setKey(mContext, "432e4d51fb19e2d0084b0d4b5e7f9cc3");
		push.setChannelId(mContext, "360");
		push.show(mContext, true);
	}
}
