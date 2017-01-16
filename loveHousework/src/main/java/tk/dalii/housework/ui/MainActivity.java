package tk.dalii.housework.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import cn.domob.android.ads.DomobAdEventListener;
import cn.domob.android.ads.DomobAdView;
import cn.domob.android.ads.DomobAdManager.ErrorCode;

import tk.dalii.housework.R;
import tk.dalii.housework.controller.CustomViewPager;
import tk.dalii.housework.controller.GridViewAdapter;
import tk.dalii.housework.controller.ShakeListener;
import tk.dalii.housework.controller.ShakeListener.OnShakeListener;
import tk.dalii.housework.controller.ViewPagerAdapter;
import tk.dalii.housework.model.HouseWorkService;
import tk.dalii.housework.utils.DimenUtils;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author yuxuehai
 */
public class MainActivity extends Activity implements OnClickListener,
		OnPageChangeListener {
	// 定义ViewPager对象--ViewPager适配器--ArrayList存放View--引导view布局资源--底部point图片--记录当前位置
	private CustomViewPager viewpager;
	private ViewPagerAdapter vpAdapter;
	private ArrayList<View> views;
	private static int[] viewLayouts = { R.layout.viewpager_shake,
			R.layout.viewpager_gridview };
	private ImageView[] viewpager_points;
	// 设置默认当前页
	private int currentIndex = viewLayouts.length - 1;
	LayoutInflater mInflater;
	// 底部导航按钮--开始按钮
	LinearLayout nav_btn;
	Button nav_btn_start;
	/**
	 * 以下是gridview
	 * **************************************************************
	 */
	private HouseWorkService service;
	private ArrayList<String> grids;
	private ArrayList<Integer> gridsID;
	public static ArrayList<Integer> deleteGridsID;
	// 总记录数
	private int count;
	// 显示图片可选标志--全选标志
	private boolean visible_flag = false;
	private boolean selectAll_flag = true;
	private GridViewAdapter mGridViewAdapter;
	// popupwindow
	private PopupWindow popupWindow_top;
	private PopupWindow popupWindow_top_shake;
	private PopupWindow popupWindow_bottom;
	private PopupWindow popupWindow_deletedialogcustom;
	// 选中条目数
	private TextView selectedItems;
	// 全选按钮
	private Button btn_select_all;
	// gridview gv
	private GridView gv;
	/**
	 * 以下是shake **************************************************************
	 */
	private ShakeListener mShakeListener = null;
	private Vibrator mVibrator;
	// 声音池--存放soundID
	private SoundPool soundPool;
	private HashMap<Integer, Integer> soundPoolMap;
	// 摇晃出来的字体内容 viewpager_shake_text
	private TextView viewpager_shake_text;
	// 全部 -- 命中--data id集合
	private ArrayList<String> allDatas;
	private ArrayList<Integer> allIds;
	public static ArrayList<Integer> hitIds;
	private String hitData;
	private Integer hitId;
	// Random对象
	private Random mRandom;
	// 开始标志
	private boolean start_flag = true;

	/**
	 * 再按一次退出 ****************************************************
	 */
	private long waitTime = 2000;
	private long touchTime = 0;
	/**
	 * 多盟广告*************************
	 */
	private RelativeLayout mAdRelativeLayout;
	private DomobAdView mAdBanner;
	private Timer timer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 初始打气筒
		mInflater = LayoutInflater.from(this);
		// ActionBar实现向上导航
		ActionBar actionBar = this.getActionBar();
		actionBar.show();
		// 初始化view
		initView();
	}

	/**
	 * 初始化组件士xin
	 */
	private void initView() {
		// 获取到nav_btn_start按钮引用
		nav_btn_start = (Button) findViewById(R.id.nav_btn_start);
		// 初始化数据库service
		service = new HouseWorkService(this);
		// 实例化ArrayList对象--ViewPager--ViewPager适配器
		views = new ArrayList<View>();
		viewpager = (CustomViewPager) findViewById(R.id.viewpager);
		// 更改viewpager页面切换时间
		// UpdateViewPagerSpeed(200);
		vpAdapter = new ViewPagerAdapter(views);
		// 初始化数据
		initData();
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		// 获取总记录数
		count = (int) service.getCount_selected();
		// 清空一下
		if (views.size() > 0) {
			views.clear();
		}
		// 初始化布局
		for (int i = 0; i < viewLayouts.length + (count - 1) / 6; i++) {
			if (i >= viewLayouts.length) {
				views.add(mInflater.inflate(
						viewLayouts[viewLayouts.length - 1], null));
			} else {
				views.add(mInflater.inflate(viewLayouts[i], null));
			}
		}
		// 设置适配器
		viewpager.setAdapter(vpAdapter);
		// 设置当前view--gridView
		viewpager.setCurrentItem(currentIndex);
		initGrids(views.get(currentIndex));
		// 设置监听器
		viewpager.setOnPageChangeListener(this);
		// 初始化底部point
		initViewPagerPoint();
	}

	/**
	 * 初始化小点
	 */
	private void initViewPagerPoint() {
		LinearLayout linerLayout = (LinearLayout) findViewById(R.id.viewpager_point);
		viewpager_points = new ImageView[viewLayouts.length + (count - 1) / 6];
		// 清空linerLayout中的小点
		if (linerLayout.getChildCount() > 0) {
			linerLayout.removeAllViewsInLayout();
		}
		// 循环添加小点
		for (int i = 0; i < viewpager_points.length; i++) {
			// inflate出ImageView对象 并设置参数
			ImageView point = (ImageView) mInflater.inflate(
					R.layout.viewpager_point_item, null);
			// 默认为白色
			point.setEnabled(true);
			// 设置监听
			point.setOnClickListener(this);
			// 设置位置tag，方便取出与当前位置对应
			point.setTag(i);
			viewpager_points[i] = point;
			linerLayout.addView(point);
		}
	}

	/**
	 * 长按震动
	 */
	public void longClickVibrator() {
		// 获取震动器
		if (mVibrator == null) {
			mVibrator = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
		}
		mVibrator.vibrate(new long[] { 50, 50 }, -1);
		// mVibrator.cancel();
	}

	/**
	 * activity生命周期函数--恢复
	 */
	@Override
	protected void onResume() {
		super.onResume();
		// 恢复shake
		if (currentIndex == 0) {
			initShake();
		} else if (hitIds != null) {
			// 重新开始
			Restart();
			initView();
		}
	}

	/**
	 * 重新开始
	 */
	private void Restart() {
		if (hitIds != null) {
			hitIds.clear();
			hitIds = null;
		}
		start_flag = true;
		nav_btn_start.setText("开始");
	}

	/**
	 * activity生命周期函数--暂停
	 */
	@Override
	protected void onPause() {
		super.onPause();
		if (mShakeListener != null) {
			mShakeListener.stop();
		}
	}

	/**
	 * btn_add添加grid选项
	 * 
	 * @param view
	 */
	public void nav_btn_add(View view) {
		Intent intent = new Intent(this, SecondActivity.class);
		startActivity(intent);
	}

	/**
	 * nav_btn_start定位到shake页面
	 * 
	 * @param view
	 */
	public void nav_btn_start(View view) {
		if (!start_flag) {
			start_flag = true;
		}
		setCurView(0);
		setCurDot(0);
	}

	/**
	 * 当滑动状态改变时调用
	 */
	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	/**
	 * 当当前页面被滑动时调用
	 */
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	/**
	 * 当前的页面被选中事调用
	 */
	@Override
	public void onPageSelected(int position) {
		// 设置底部小点选中状态
		setCurDot(position);
	}

	/**
	 * 通过点击事件切换当前页面
	 */
	@Override
	public void onClick(View v) {
		int position = (Integer) v.getTag();
		setCurView(position);
		setCurDot(position);
	}

	/**
	 * 设置当前页面的位置
	 */
	private void setCurView(int position) {
		if (position < 0 || position >= viewLayouts.length + (count - 1) / 6) {
			return;
		}
		viewpager.setCurrentItem(position);
	}

	/**
	 * 设置当前的小点的位置
	 */
	private void setCurDot(int position) {
		if (position < 0 || position > viewpager_points.length - 1
				|| currentIndex == position) {
			return;
		}
		// 解决删除一页grid后角标越界bug
		if (currentIndex >= viewpager_points.length) {
			currentIndex = viewpager_points.length - 1;
		}
		viewpager_points[currentIndex].setEnabled(true);
		viewpager_points[position].setEnabled(false);
		currentIndex = position;

		// 设置grids
		initGrids(views.get(currentIndex));

		if (!visible_flag) {
			// 如果不是进行删除操作 ---就初始化Shake
			if (position == 0) {
				// 初始化震动
				initShake();
				// 设置shake点击事件
				findViewById(R.id.viewpager_shake).setOnClickListener(
						new OnClickListener() {

							@Override
							public void onClick(View v) {
								if (mShakeListener.checkInterval()) {
									mShakeListener.getOnShakeListener()
											.onShake();
								}
							}
						});
				// 设置底部导航不可见--动画效果
				if (nav_btn == null) {
					nav_btn = (LinearLayout) findViewById(R.id.nav_btn);
				}
				nav_btn.startAnimation(AnimationUtils.loadAnimation(this,
						R.anim.out_toptobottom));
				nav_btn.setVisibility(View.GONE);
				// 设置顶部标题popupwindow_top_shake--动画
				initPopupWindow_Top_Shake(nav_btn);
			} else {
				if (mShakeListener != null) {
					mShakeListener.stop();
				}
				if (nav_btn != null) {
					// 更改nav_btn_start按钮名称
					if (start_flag) {
						nav_btn_start.setText("开始");
					} else {
						nav_btn_start.setText("重新开始");
					}
					// 设置底部导航可见
					nav_btn.setVisibility(View.VISIBLE);
					nav_btn.startAnimation(AnimationUtils.loadAnimation(this,
							R.anim.in_bottomtotop));
					nav_btn = null;
					// 设置顶部标题栏不可见
					HidePopupWindow_Top_Shake();
				}
			}
		}
	}

	/**
	 * 初始化 popupwindow_top_shake
	 */
	private void initPopupWindow_Top_Shake(View v) {
		// 如果popupWindow_top_shake存在则不再初始化
		if (popupWindow_top_shake != null) {
			return;
		}
		// 获取自定义popupWindow_view布局
		View popupWindow_view_top_shake = mInflater.inflate(
				R.layout.popupwindow_top_shake, null);
		// 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度---不获取焦点
		popupWindow_top_shake = new PopupWindow(popupWindow_view_top_shake,
				LayoutParams.MATCH_PARENT, DimenUtils.Dp2Px(this, 50));
		// 设置动画效果
		popupWindow_top_shake.setAnimationStyle(R.style.AnimationFade_top);
		// 设置外部可触摸
		popupWindow_top_shake.setOutsideTouchable(true);
		// 第一个参数---父view-这里是位置显示方式,在屏幕的顶部
		popupWindow_top_shake.showAtLocation(v, Gravity.TOP, 0,
				DimenUtils.StatusBarHeight(v) - 2);
	}

	/**
	 * 设置顶部标题栏不可见
	 */
	private void HidePopupWindow_Top_Shake() {
		if (popupWindow_top_shake != null && popupWindow_top_shake.isShowing()) {
			// 关闭popupwindow
			popupWindow_top_shake.dismiss();
			popupWindow_top_shake = null;
		}
	}

	/**
	 * 以下是gridview
	 * **************************************************************
	 */
	private void initGrids(View gridView) {
		if (currentIndex != 0 || visible_flag) {
			// 获取分页显示的数据 跟对应的ID
			grids = service.getScrollData_selected((visible_flag ? currentIndex
					: currentIndex - 1) * 6, 6);
			gridsID = service.getScrollDataID_selected(
					(visible_flag ? currentIndex : currentIndex - 1) * 6, 6);
			if (grids.size() == 0) {
				gridView.findViewById(R.id.remind).setVisibility(View.VISIBLE);
			} else {
				gridView.findViewById(R.id.remind).setVisibility(View.GONE);
			}
			gv = (GridView) gridView.findViewById(R.id.gridview);
			mGridViewAdapter = new GridViewAdapter(this, grids, gridsID, gv,
					visible_flag);
			gv.setAdapter(mGridViewAdapter);

			if (!visible_flag) {
				// 长按显示可选择imageview
				gv.setOnItemLongClickListener(new OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> arg0, View v,
							int position, long arg3) {
						// 震动
						longClickVibrator();
						// 调用弹出PopupWindow方法
						initPopupWindow(v);
						// 去除shake页面
						viewLayouts = new int[] { R.layout.viewpager_gridview };
						currentIndex--;
						// 数据更新
						ToggleSelectImgState_initView(true);
						GridsOnItemClick(v, position);
						return false;
					}
				});
			}
			// 如果选择图片可见 就设置单击事件
			if (visible_flag) {
				// 点击事件 切换选中状态
				gv.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						//initPopupWindow(arg1);
						GridsOnItemClick(arg1, arg2);
					}
				});
			}
		}
	}

	/**
	 * Grids在选择图片可见条件下的单机改变状态方法
	 * 
	 * @param view
	 * @param position
	 */
	private void GridsOnItemClick(View view, int position) {
		// 初始化要删除的id数组
		if (deleteGridsID == null) {
			deleteGridsID = new ArrayList<Integer>();
		}
		Integer temp = gridsID.get(position);
		// 如果已包含点击的条目ID，就将其移除，不包含就将其添加进去，，并改变图片显示状态
		if (deleteGridsID.contains(temp)) {
			deleteGridsID.remove(temp);
			view.findViewById(R.id.gridview_item_imageview).setEnabled(true);
		} else {
			deleteGridsID.add(temp);
			view.findViewById(R.id.gridview_item_imageview).setEnabled(false);
		}
		// 如果没有全选
		if (deleteGridsID.size() != service.getCount_selected()) {
			selectAll_flag = true;
			btn_select_all.setText("全选");
			ToggleSelectImgState(true);
		}
		selectedItems.setText("已选中"
				+ (deleteGridsID == null ? 0 : deleteGridsID.size()) + "项");
	}

	/**
	 * 初始化initPopupWindow方法
	 */
	private void initPopupWindow(View v) {
		// 判断popupwindow 存在则不执行
		if (null != popupWindow_top && popupWindow_bottom != null) {
			return;
		}

		// 获取自定义popupWindow_view布局
		final View popupWindow_view_top = mInflater.inflate(R.layout.popupwindow_top,
				null);
		// 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度---不获取焦点
		popupWindow_top = new PopupWindow(popupWindow_view_top,
				LayoutParams.MATCH_PARENT, DimenUtils.Dp2Px(this, 50));
		// 设置动画效果
		popupWindow_top.setAnimationStyle(R.style.AnimationFade_top);
		// 设置外部可触摸
		popupWindow_top.setOutsideTouchable(false);
		// 第一个参数---父view-这里是位置显示方式,在屏幕的顶部
		popupWindow_top.showAtLocation(v, Gravity.TOP, 0,
				DimenUtils.StatusBarHeight(v) - 2);
		// 点击取消让窗口消失
		popupWindow_view_top.findViewById(R.id.btn_cancel).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						HidePopupWindow();
					}
				});
		// 点击全选按钮
		btn_select_all = (Button) popupWindow_view_top
				.findViewById(R.id.btn_select_all);
		btn_select_all.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 全选的话 就把查询出的所有ID 赋值给删除ID集合--反之置空
				if (selectAll_flag) {
					deleteGridsID = service.getAllDataID_selected();
					selectAll_flag = false;
					((Button) v).setText("全不选");
					ToggleSelectImgState(true);

				} else {
					selectAll_flag = true;
					((Button) v).setText("全选");
					deleteGridsID.clear();
					deleteGridsID = null;
					ToggleSelectImgState(true);
				}
				selectedItems.setText("已选中"
						+ (deleteGridsID == null ? 0 : deleteGridsID.size())
						+ "项");

			}
		});
		// 选中条目数
		selectedItems = (TextView) popupWindow_view_top
				.findViewById(R.id.tv_selectitems);
		// 同上
		View popupWindow_view_bottom = mInflater.inflate(
				R.layout.popupwindow_bottom, null);
		popupWindow_bottom = new PopupWindow(popupWindow_view_bottom,
				LayoutParams.MATCH_PARENT, DimenUtils.Dp2Px(this, 50));
		popupWindow_bottom.setAnimationStyle(R.style.AnimationFade_bottom);
		popupWindow_bottom.setOutsideTouchable(false);
		popupWindow_bottom.showAtLocation(v, Gravity.BOTTOM, 0, 0);
		// 删除条目操作
		popupWindow_view_bottom.findViewById(R.id.btn_cancel)
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (deleteGridsID != null && deleteGridsID.size() > 0) {
							DeleteDialogCustom(MainActivity.this.getWindow()
									.getDecorView());
						} else {
							Toast.makeText(MainActivity.this, "请先选择条目！",
									Toast.LENGTH_SHORT).show();
						}
					}
				});
	}

	/**
	 * 切换显示gridview_item_imageview
	 * 
	 * @param flag
	 */
	private void ToggleSelectImgState(boolean visible_flag) {
		this.visible_flag = visible_flag;
		mGridViewAdapter.setVisible_flag(visible_flag);
		mGridViewAdapter.notifyDataSetChanged();
	}

	/**
	 * 切换显示gridview_item_imageview
	 * 
	 * @param flag
	 */
	private void ToggleSelectImgState_initView(boolean visible_flag) {
		this.visible_flag = visible_flag;
		mGridViewAdapter.setVisible_flag(visible_flag);
		initView();
	}

	/**
	 * 删除后更新界面数据
	 */
	private void MyNotifyDataChange() {
		HidePopupWindow();
	}

	/**
	 * 关闭弹窗
	 */
	private void HidePopupWindow() {
		// 添加shake页面
		viewLayouts = new int[] { R.layout.viewpager_shake,
				R.layout.viewpager_gridview };
		currentIndex++;
		if (popupWindow_top != null && popupWindow_top.isShowing()) {
			// 关闭popupwindow
			popupWindow_top.dismiss();
			popupWindow_top = null;
			popupWindow_bottom.dismiss();
			popupWindow_bottom = null;
			// 隐藏gridview_item_imageview--更改全选状态
			ToggleSelectImgState_initView(false);
			selectAll_flag = true;
			// 释放删除ID集合资源，并置空
			if (deleteGridsID != null) {
				deleteGridsID.clear();
				deleteGridsID = null;
			}
			// gv解绑点击事件
			gv.setOnItemClickListener(null);
		}
		// initView();
	}

	/**
	 * 使用反射更改 viewpager页面切换时间
	 * 
	 * @param speed
	 */
	/*
	 * private void UpdateViewPagerSpeed(int speed) { try { if (viewpager ==
	 * null) { return; } Field field =
	 * ViewPager.class.getDeclaredField("mScroller"); field.setAccessible(true);
	 * FixedSpeedScroller scroller = new FixedSpeedScroller(
	 * viewpager.getContext(), new AccelerateInterpolator());
	 * field.set(viewpager, scroller); scroller.setmDuration(speed); } catch
	 * (Exception e) { e.printStackTrace(); } }
	 */

	/**
	 * 自定义删除弹出框
	 */
	private void DeleteDialogCustom(View v) {
		// 获取popupwindow自定义布局
		View popupWindow_view_deletedialogcustom = mInflater.inflate(
				R.layout.popupwindow_deletedialogcustom, null);
		// 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度---不获取焦点
		popupWindow_deletedialogcustom = new PopupWindow(
				popupWindow_view_deletedialogcustom, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT, true);
		// 设置动画效果
		popupWindow_deletedialogcustom
				.setAnimationStyle(R.style.AnimationFade_bottom);
		// 设置外部可触摸
		// popupWindow_deletedialogcustom.setOutsideTouchable(true);
		// 第一个参数---父view-这里是位置显示方式,在屏幕的顶部
		popupWindow_deletedialogcustom.showAtLocation(v, Gravity.BOTTOM, 0, 0);
		// 设置text
		((TextView) popupWindow_view_deletedialogcustom
				.findViewById(R.id.tv_deletedialogcustom)).setText("确定要移除所选的"
				+ deleteGridsID.size() + "条选项吗?");
		// 点击取消让窗口消失
		popupWindow_view_deletedialogcustom.findViewById(
				R.id.btn_cancle_deletedialogcustom).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// 取消弹出窗口
						if (popupWindow_deletedialogcustom != null
								&& popupWindow_deletedialogcustom.isShowing()) {
							popupWindow_deletedialogcustom.dismiss();
							popupWindow_deletedialogcustom = null;
						}
					}
				});
		// 点击确定按钮
		popupWindow_view_deletedialogcustom.findViewById(
				R.id.btn_ok_deletedialogcustom).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// 确定删除
						// 调用服务的方法 操作数据库
						service.delete_selected(deleteGridsID);
						// 设置start_flag 为true---hitIds=null--设置为开始
						Restart();
						// 通知adapter数据更新
						MyNotifyDataChange();
						// 取消弹出窗口
						if (popupWindow_deletedialogcustom != null
								&& popupWindow_deletedialogcustom.isShowing()) {
							popupWindow_deletedialogcustom.dismiss();
							popupWindow_deletedialogcustom = null;
						}
					}
				});
	}

	/**
	 * 初始化Shake--摇一摇
	 */
	public void initShake() {
		// 初始化核心算法所需数据
		if (start_flag) {
			allDatas = service.getAllData_selected();
			allIds = service.getAllDataID_selected();
			mRandom = new Random();
			hitIds = new ArrayList<Integer>();
			// start_flag标志改为false
			start_flag = false;
		}
		// 获取 摇晃出的字体内容引用--并设置为不可见
		viewpager_shake_text = (TextView) findViewById(R.id.viewpager_shake_text);
		viewpager_shake_text.setVisibility(View.GONE);
		// 加载声音
		loadSound();
		// 获取震动器
		if (mVibrator == null) {
			mVibrator = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
		}
		// 设置震动监听器 效果
		mShakeListener = new ShakeListener(this);
		mShakeListener.setOnShakeListener(new OnShakeListener() {

			@Override
			public void onShake() {
				// 开启banner广告
				startAdBanner();
				// 判断选项已全部选出
				if (allIds.size() == 0) {
					if (service.getAllData_selected().size() == 0) {
						Toast.makeText(MainActivity.this, "请先添加选项！",
								Toast.LENGTH_SHORT).show();
						return;
					}
					Toast.makeText(MainActivity.this, "请重新开始！",
							Toast.LENGTH_SHORT).show();
					return;
				}
				// 设置viewpager不可滑动
				viewpager.setCanScroll(false);
				coreAlgorithm();// 调用核心算法
				startShakeAnim();// 开始震动动画
				startVibrator();// 开始震动
				mShakeListener.stop();// 取消监听器
				soundPool.play(soundPoolMap.get(0), 1.0f, 1.0f, 0, 0, 1.0f);// 播放声音
				//
				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						// 播放声音
						soundPool.play(soundPoolMap.get(1), 1.0f, 1.0f, 0, 0,
								1.0f);// 播放声音
						// 停止震动
						mVibrator.cancel();
						// 再次注册监听器
						if (currentIndex == 0) {
							mShakeListener.start();
							//设置viewpager可滑动
							viewpager.setCanScroll(true);
						}
					}
				}, 2000);
			}
		});
	}

	/**
	 * 震动动画
	 */
	public void startShakeAnim() {
		// shake_top
		Animation ani_shake_top = AnimationUtils.loadAnimation(this,
				R.anim.up_shaketop);
		((ImageView) findViewById(R.id.viewpager_shake_top))
				.startAnimation(ani_shake_top);
		// shake_bottom
		Animation ani_shake_bottom = AnimationUtils.loadAnimation(this,
				R.anim.down_shakebottom);
		((ImageView) findViewById(R.id.viewpager_shake_bottom))
				.startAnimation(ani_shake_bottom);
		// shake_text
		Animation ani_shake_text = AnimationUtils.loadAnimation(this,
				R.anim.down_shaketext);
		// 设置内容
		viewpager_shake_text.setText(hitData);
		viewpager_shake_text.setVisibility(View.VISIBLE);
		viewpager_shake_text.startAnimation(ani_shake_text);
	}

	/**
	 * 摇一摇核心算法
	 */
	private void coreAlgorithm() {
		// 获取产生随机数的范围--得到命中索引
		int max = allIds.size();
		int hitIndex = mRandom.nextInt(max);
		// 将数据存储到命中命中数据集合--并移除
		hitId = allIds.remove(hitIndex);
		hitData = allDatas.remove(hitIndex);
		hitIds.add(hitId);
		// int s = random.nextInt(max)%(max-min+1) + min;
		// random.nextInt(max)表示生成[0,max]之间的随机数，然后对(max-min+1)取模。
		// 以生成[10,20]随机数为例，首先生成0-20的随机数，然后对(20-10+1)取模得到[0-10]之间的随机数，然后加上min=10，最后生成的是10-20的随机数
	}

	/**
	 * 开始震动
	 */
	public void startVibrator() {
		// 第一个｛｝里面是节奏数组， 第二个参数是重复次数，-1为不重复，非-1从pattern的指定下标开始重复
		mVibrator.vibrate(new long[] { 500, 200, 500, 200 }, -1);
	}

	/**
	 * 加载声音
	 */
	@SuppressLint("UseSparseArrays")
	public void loadSound() {
		soundPoolMap = new HashMap<Integer, Integer>();
		soundPool = new SoundPool(2, AudioManager.STREAM_SYSTEM, 5);
		new Thread() {
			public void run() {
				try {
					soundPoolMap.put(
							0,
							soundPool.load(
									getAssets().openFd(
											"sound/shake_sound_male.mp3"), 1));
					soundPoolMap.put(1, soundPool.load(
							getAssets().openFd("sound/shake_match.mp3"), 1));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	/**
	 * 重写onBackPressed方法直接监听返回键 系统先是onKeyDown，如果return true了，就不会onBackPressed啦！
	 */
	@Override
	public void onBackPressed() {
		long currentTime = System.currentTimeMillis();
		if ((currentTime - touchTime) >= waitTime) {
			Toast.makeText(this, "再按一次保证退出！", Toast.LENGTH_SHORT).show();
			touchTime = currentTime;
		} else {
			this.finish();
		}
	}

	/**
	 * 多盟广告
	 */
	private void startAdBanner() {
		// 获取广告位置
		if (mAdRelativeLayout == null) {
			mAdRelativeLayout = (RelativeLayout) findViewById(R.id.ad_banner);
		}
		// 创建广告 宽自适应 高50
		if (mAdBanner == null) {
			mAdBanner = new DomobAdView(this, "56OJw/souNLPK2iKf/",
					"16TLuASvApfVkNUfnizY7Yvi",
					DomobAdView.INLINE_SIZE_FLEXIBLE);
			mAdBanner.setAdEventListener(new DomobAdEventListener() {

				// 离开应用回调
				@Override
				public void onDomobLeaveApplication(DomobAdView arg0) {
					// TODO Auto-generated method stub

				}

				// 成功接收到广告返回回调
				@Override
				public void onDomobAdReturned(DomobAdView arg0) {

				}

				// 返回当前context
				@Override
				public Context onDomobAdRequiresCurrentContext() {
					// TODO Auto-generated method stub
					return null;
				}

				// 成功打开回调
				@Override
				public void onDomobAdOverlayPresented(DomobAdView arg0) {
					// TODO Auto-generated method stub

				}

				// 关闭回调
				@Override
				public void onDomobAdOverlayDismissed(DomobAdView arg0) {
					// TODO Auto-generated method stub

				}

				// 广告请求失败
				@Override
				public void onDomobAdFailed(DomobAdView arg0, ErrorCode arg1) {
					// TODO Auto-generated method stub

				}

				// 广告点击回调
				@Override
				public void onDomobAdClicked(DomobAdView arg0) {
					// TODO Auto-generated method stub

				}
			});
			// 将广告view添加到广告位
			mAdRelativeLayout.addView(mAdBanner);
		}
		if (mAdRelativeLayout.getVisibility() == View.GONE) {
			mAdRelativeLayout.setVisibility(View.VISIBLE);
			mAdRelativeLayout.startAnimation(AnimationUtils.loadAnimation(this,
					R.anim.in_bottomtotop));
		}
		if (timer == null) {
			timer = new Timer();
			// 计时器--5秒后广告消失
			timer.schedule(new TimerTask() {
				// 任务栈
				@Override
				public void run() {
					// 更新ui
					// 利用Activity.runOnUiThread(Runnable)把更新ui的代码创建在Runnable中，然后在需要更新ui时，把这个Runnable对象传给Activity.runOnUiThread(Runnable)。
					// 这样Runnable对像就能在ui程序中被调用。如果当前线程是UI线程,那么行动是立即执行。如果当前线程不是UI线程,操作是发布到事件队列的UI线程
					MainActivity.this.runOnUiThread(new Runnable() {

						@Override
						public void run() {
							mAdRelativeLayout.startAnimation(AnimationUtils
									.loadAnimation(MainActivity.this,
											R.anim.out_toptobottom));
							mAdRelativeLayout.setVisibility(View.GONE);
						}
					});
					timer.cancel();
					timer = null;
				}
			}, 5000);
		}
	}
}
