package tk.dalii.housework.controller;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 自定义Viewpager
 * 
 * @author yuxuehai
 * 
 */
public class CustomViewPager extends ViewPager {

	private boolean isCanScroll = true;

	/**
	 * 属性方法
	 * @return
	 */
	public boolean isCanScroll() {
		return isCanScroll;
	}

	public void setCanScroll(boolean isCanScroll) {
		this.isCanScroll = isCanScroll;
	}

	public void setScanScroll(boolean isCanScroll) {
		this.isCanScroll = isCanScroll;
	}

	/**
	 * 覆盖方法
	 * @param context
	 */
	public CustomViewPager(Context context) {
		super(context);
	}

	public CustomViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	/**
	 * 如果他的返回值是true，那么说明本次触摸事件被消费掉了，会传进来一个新的触摸事件。如果是false，那么说明没有被消费掉，就不会换入一个新的事件。
	 */
	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		if (isCanScroll) {
			try {
				return super.onTouchEvent(arg0);
			} catch (Exception e) {
				return true;
			}
		}else{
			return true;
		}
	}
	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		if (isCanScroll) {
			try {
				return super.onInterceptTouchEvent(arg0);
			} catch (Exception e) {
				return true;
			}
		}else{
			return true;
		}
	}
}
