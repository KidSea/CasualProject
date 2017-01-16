package tk.dalii.housework.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import tk.dalii.housework.R;
import tk.dalii.housework.ui.SecondActivity;
/**
 * @author yuxuehai
 */
public class MyListViewAdapter extends BaseAdapter {
	private Context mContext;
	// listview要显示的内容集合--相应的ID集合--hw_selected 表中 所有hwsid
	private ArrayList<String> mList;
	private ArrayList<Integer> mListID;
	private ArrayList<Integer> mGridsHWSID;
	// inflater
	private LayoutInflater mInflater;
	// 选择图片 可见标志
	private boolean visible_flag;

	/**
	 * get set 方法
	 * 
	 * @param visible_flag
	 */
	public void setVisible_flag(boolean visible_flag) {
		this.visible_flag = visible_flag;
	}

	/**
	 * 构造函数
	 * 
	 * @param mContext
	 * @param mList
	 * @param mListID
	 */
	public MyListViewAdapter(Context mContext, ArrayList<String> mList,
			ArrayList<Integer> mListID, ArrayList<Integer> mGridsHWSID) {
		super();
		this.mContext = mContext;
		this.mList = mList;
		this.mListID = mListID;
		this.mInflater = LayoutInflater.from(mContext);
		this.mGridsHWSID = mGridsHWSID;
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return mList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	/**
	 * 优化性能方式
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		//if (convertView == null) {
			// 填充listview_item
			convertView = mInflater
					.inflate(R.layout.second_listview_item, null);
			holder = new ViewHolder();
			holder.text = (TextView) convertView
					.findViewById(R.id.second_listview_item_textview);
			holder.image = (ImageView) convertView
					.findViewById(R.id.second_listview_item_imageview);
			convertView.setTag(holder);
		/*} else {
			holder = (ViewHolder) convertView.getTag();
		}*/
		holder.text.setText(mList.get(position));
		// 判断mGridsHWSID若包含mListID 则改变背景颜色
		System.out.println(mGridsHWSID.toString()+";;;;");
		System.out.println(mListID.toString());
		if (mGridsHWSID.size() > 0
				&& mGridsHWSID.contains(mListID.get(position))) {
			holder.text.setTextColor(mContext.getResources().getColor(
					R.color.white));
			holder.text.setBackgroundColor(mContext.getResources().getColor(
					R.color.pink_main));
			holder.text.setAlpha(0.6f);
		}
		// 让选择图片不可点击
		holder.image.setClickable(false);
		if (visible_flag) {
			holder.image.setVisibility(View.VISIBLE);
			// mAddToGridsID集合不为空--就检查将要初始化的ID是否包含在其中，是的话就更改gridview_item_imageview状态
			if (SecondActivity.mAddToGridsID != null
					&& SecondActivity.mAddToGridsID.contains(mListID
							.get(position))) {
				holder.image.setEnabled(false);
			} else {
				holder.image.setEnabled(true);
			}
		} else {
			holder.image.setVisibility(View.GONE);
		}
		return convertView;
	}

	private class ViewHolder {
		TextView text;
		ImageView image;
	}
}
