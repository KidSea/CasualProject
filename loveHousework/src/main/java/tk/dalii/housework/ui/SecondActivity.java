package tk.dalii.housework.ui;

import java.util.ArrayList;

import tk.dalii.housework.R;
import tk.dalii.housework.controller.MyListViewAdapter;
import tk.dalii.housework.model.HouseWorkService;
import tk.dalii.housework.utils.DimenUtils;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
/**
 * @author yuxuehai
 */
public class SecondActivity extends Activity {
	private ListView mListView;
	private HouseWorkService service;
	// popupwindow
	private PopupWindow popupWindow_top;
	private PopupWindow popupWindow_bottom;
	private PopupWindow popupWindow_deletedialogcustom;

	private LayoutInflater mInflater;
	// 选择图片--全选标志
	private boolean selectAll_flag = true;
	// 内容集合--相应的ID集合--adapter
	private ArrayList<String> mList;
	private ArrayList<Integer> mListID;
	private MyListViewAdapter mMyListViewAdapter;
	// 添加到grids的集合
	public static ArrayList<Integer> mAddToGridsID;
	// 显示选中项条数
	private TextView selectedItems;
	// 全选按钮
	private Button btn_select_all_second;
	// hw_selected 表中 所有hwsid
	private ArrayList<Integer> mGridsHWSID;
	// 可以初始化popupwindow的标志
	private boolean popupwindow_show = true;
	// 长按震动
	private Vibrator mVibrator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		// ActionBar实现向上导航
		ActionBar actionBar = this.getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		// 初始化listview
		initListView();
	}

	/**
	 * activity没有完全启动是不能弹出PopupWindow的！！
	 */
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		// 初始化popupwindow
		if (hasFocus && popupwindow_show) {
			initPopupWindow(this.getWindow().getDecorView());// 相对于整个屏幕
			ToggleSelectImgState(true);
		}
	}

	/**
	 * 自定义按钮 点击事件
	 * 
	 * @param v
	 */
	public void btn_custom(View v) {
		if (mInflater == null) {
			return;
		}
		View alertDialogCustom = mInflater.inflate(R.layout.alertdialogcustom,
				null);
		final TextView et_input = (TextView) alertDialogCustom
				.findViewById(R.id.et_input);
		// 设置popupwindow不可见
		popupwindow_show = false;
		final AlertDialog b = new AlertDialog.Builder(this).setView(
				alertDialogCustom).show();
		// 设置按钮点击事件
		alertDialogCustom.findViewById(R.id.btn_alertdialog_okadd)
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						String str = et_input.getText().toString().trim();
						if (!str.equals("") && str != null) {
							service.add_hourseworks(str);
							initListView();
						}
						// 取消弹出框
						b.dismiss();
					}
				});
	}

	/**
	 * 初始化listview
	 */
	private void initListView() {
		// 新建service
		service = new HouseWorkService(this);
		// 获取到listview
		mListView = (ListView) findViewById(R.id.second_listview);
		mList = service.getAllData_hourseworks();
		mListID = service.getAllDataID_hourseworks();
		mGridsHWSID = service.getAllDataHWSID_selected();
		mMyListViewAdapter = new MyListViewAdapter(this, mList, mListID,
				mGridsHWSID);
		mListView.setAdapter(mMyListViewAdapter);
		// 长按弹出popupwindow
		mListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 弹出popupwindow
				initPopupWindow(view);
				// 选择图片可见
				ToggleSelectImgState(true);
				return false;
			}
		});
	}

	/**
	 * 初始化popupwindow
	 */
	private void initPopupWindow(View v) {
		// 判断popupWindow_top存在则不再初始化
		if (popupWindow_top != null) {
			return;
		}
		//长按震动
		longClickVibrator();
		// 点击切换选择图片的状态
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 初始化mAddToGridsID
				if (mAddToGridsID == null) {
					mAddToGridsID = new ArrayList<Integer>();
				}
				Integer temp = mListID.get(position);
				// 如果已包含点击的条目ID，就将其移除，不包含就将其添加进去，，并改变图片显示状态
				if (mAddToGridsID.contains(temp)) {
					mAddToGridsID.remove(temp);
					view.findViewById(R.id.second_listview_item_imageview)
							.setEnabled(true);
				} else {
					mAddToGridsID.add(temp);
					view.findViewById(R.id.second_listview_item_imageview)
							.setEnabled(false);
				}
				// 如果未全选
				if (mAddToGridsID.size() != (int) service
						.getCount_hourseworks()) {
					selectAll_flag = true;
					btn_select_all_second.setText("全选");
					ToggleSelectImgState(true);
				}
				selectedItems.setText("已选中"
						+ (mAddToGridsID == null ? 0 : mAddToGridsID.size())
						+ "项");
			}
		});
		mInflater = LayoutInflater.from(this);
		// 获取popupwindow自定义布局
		View popupWindow_view_top = mInflater.inflate(
				R.layout.popupwindow_top_second, null);
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
		popupWindow_view_top.findViewById(R.id.btn_cancel_second)
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						HidePopupWindow();
					}
				});
		// 点击全选按钮
		btn_select_all_second = (Button) popupWindow_view_top
				.findViewById(R.id.btn_select_all_second);
		btn_select_all_second.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 全选的话 就把查询出的所有ID 赋值给删除ID集合--反之置空
				if (selectAll_flag) {
					mAddToGridsID = service.getAllDataID_hourseworks();
					selectAll_flag = !selectAll_flag;
					((Button) v).setText("全不选");
					ToggleSelectImgState(true);
				} else {
					selectAll_flag = !selectAll_flag;
					((Button) v).setText("全选");
					if (mAddToGridsID != null) {
						mAddToGridsID.clear();
						mAddToGridsID = null;
					}
					ToggleSelectImgState(true);
				}
				selectedItems.setText("已选中"
						+ (mAddToGridsID == null ? 0 : mAddToGridsID.size())
						+ "项");
			}
		});
		// 设置选中项条目
		selectedItems = (TextView) popupWindow_view_top
				.findViewById(R.id.tv_selectitems_second);
		// 同上
		View popupWindow_view_bottom = mInflater.inflate(
				R.layout.popupwindow_bottom_second, null);
		popupWindow_bottom = new PopupWindow(popupWindow_view_bottom,
				LayoutParams.MATCH_PARENT, DimenUtils.Dp2Px(this, 50));
		popupWindow_bottom.setAnimationStyle(R.style.AnimationFade_bottom);
		popupWindow_bottom.setOutsideTouchable(false);
		popupWindow_bottom.showAtLocation(v, Gravity.BOTTOM, 0, 0);
		// 确认添加
		popupWindow_view_bottom.findViewById(R.id.btn_okadd_second)
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (mAddToGridsID != null && mAddToGridsID.size() > 0) {
							service.addToGrids(mAddToGridsID);
							HidePopupWindow();
							//initListView();
							BackMainActivity();
						} else {
							Toast.makeText(SecondActivity.this, "请先选择条目！",
									Toast.LENGTH_SHORT).show();
						}
					}
				});
		// 确认删除
		popupWindow_view_bottom.findViewById(R.id.btn_okdelete_second)
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (mAddToGridsID != null && mAddToGridsID.size() > 0) {
							//调出确认删除对话框
							DeleteDialogCustom(SecondActivity.this.getWindow().getDecorView());
						} else {
							Toast.makeText(SecondActivity.this, "请先选择条目！",
									Toast.LENGTH_SHORT).show();
						}
					}
				});
	}

	/**
	 * ActionBar
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// 设置ActionBar item选中事件
		switch (item.getItemId()) {
		case android.R.id.home:
			BackMainActivity();
			return true;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	/**
	 * 回到MainActivity
	 */
	private void BackMainActivity(){
		Intent intent = new Intent(this, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
		this.finish();
	}

	/**
	 * activity销毁的时候
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		popupwindow_show = true;
	}

	/**
	 * 切换显示gridview_item_imageview
	 * 
	 * @param flag
	 */
	private void ToggleSelectImgState(boolean visible_flag) {
		mMyListViewAdapter.setVisible_flag(visible_flag);
		mMyListViewAdapter.notifyDataSetChanged();
	}

	/**
	 * 关闭弹窗
	 */
	private void HidePopupWindow() {
		if (popupWindow_top != null && popupWindow_top.isShowing()) {
			// 关闭popupwindow
			popupWindow_top.dismiss();
			popupWindow_top = null;
			popupWindow_bottom.dismiss();
			popupWindow_bottom = null;
			// 清空并置空mAddToGridsID
			if (mAddToGridsID != null) {
				mAddToGridsID.clear();
				mAddToGridsID = null;
			}
			// 全选标志置为ture
			selectAll_flag = true;
			// 选择图片不可见
			ToggleSelectImgState(false);
			// 取消listview的点击事件
			mListView.setOnItemClickListener(null);
		}
	}

	/**
	 * 自定义删除弹出框
	 */
	private void DeleteDialogCustom(View v) {
		// 获取popupwindow自定义布局
		View popupWindow_view_deletedialogcustom = mInflater.inflate(
				R.layout.popupwindow_deletedialogcustom_second, null);
		// 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度---不获取焦点
		popupWindow_deletedialogcustom = new PopupWindow(popupWindow_view_deletedialogcustom,
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT,true);
		// 设置动画效果
		popupWindow_deletedialogcustom.setAnimationStyle(R.style.AnimationFade_bottom);
		// 设置外部可触摸
		//popupWindow_deletedialogcustom.setOutsideTouchable(true);
		// 第一个参数---父view-这里是位置显示方式,在屏幕的顶部
		popupWindow_deletedialogcustom.showAtLocation(v, Gravity.BOTTOM, 0,0);
		//设置text
		((TextView)popupWindow_view_deletedialogcustom.findViewById(R.id.tv_deletedialogcustom_second)).setText("确定要删除所选的"+mAddToGridsID.size()+"条选项吗?");
		// 点击取消让窗口消失
		popupWindow_view_deletedialogcustom.findViewById(R.id.btn_cancle_deletedialogcustom_second)
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						//取消弹出窗口
						if(popupWindow_deletedialogcustom!=null && popupWindow_deletedialogcustom.isShowing()){
							popupWindow_deletedialogcustom.dismiss();
							popupWindow_deletedialogcustom=null;
						}
					}
				});
		// 点击全选按钮
		popupWindow_view_deletedialogcustom.findViewById(R.id.btn_ok_deletedialogcustom_second)
		.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//确定删除
				service.deleteReal(mAddToGridsID);
				Toast.makeText(SecondActivity.this, "选项删除成功！！",
						Toast.LENGTH_SHORT).show();
				HidePopupWindow();
				initListView();
				//取消弹出窗口
				if(popupWindow_deletedialogcustom!=null && popupWindow_deletedialogcustom.isShowing()){
					popupWindow_deletedialogcustom.dismiss();
					popupWindow_deletedialogcustom=null;
				}
			}
		});
		
	}
	/**
	 * 长按震动
	 */
	public void longClickVibrator() {
		// 获取震动器
		if (mVibrator == null) {
			mVibrator = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
		}
		mVibrator.vibrate(new long[]{50,50}, -1);
		//mVibrator.cancel();
	}
}
