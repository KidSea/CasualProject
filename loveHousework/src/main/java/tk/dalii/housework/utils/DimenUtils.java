package tk.dalii.housework.utils;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
/**
 * @author yuxuehai
 */
public class DimenUtils {
	/**
	 * dp转换为px
	 * 
	 * @param context
	 * @param dp
	 * @return
	 */
	public static int Dp2Px(Context context, float dp) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
	}

	/**
	 * px转换为dp
	 * 
	 * @param context
	 * @param px
	 * @return
	 */
	public static int Px2Dp(Context context, float px) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (px / scale + 0.5f);
	}
	/**
	 * 获取状态栏高度
	 * @param view
	 * @return
	 */
	public static int StatusBarHeight(View view){
		Rect outRect = new Rect();
		view.getWindowVisibleDisplayFrame(outRect);
		return outRect.top;
	}
}
