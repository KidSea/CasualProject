package tk.dalii.housework.controller;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;

/**
 * 
 * @author yuxuehai 功能描述：ViewPager适配器，用来绑定数据和view
 */
public class ViewPagerAdapter extends PagerAdapter {
	// 界面列表
	private ArrayList<View> mViews;

	public ViewPagerAdapter(ArrayList<View> mViews) {
		super();
		this.mViews = mViews;
	}

	// 获取当前界面数
	@Override
	public int getCount() {
		if (mViews != null) {
			return mViews.size();
		}
		return 0;
	}

	// 初始化position位置的界面
	@Override
	public Object instantiateItem(View container, int position) {
		((ViewPager) container).addView(mViews.get(position), 0);
		return mViews.get(position);
	}

	// 判断是否由对象生成界面
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	// 销毁position位置的界面
	@Override
	public void destroyItem(View container, int position, Object object) {
		((ViewPager) container).removeView(mViews.get(position));
	}
	
}
