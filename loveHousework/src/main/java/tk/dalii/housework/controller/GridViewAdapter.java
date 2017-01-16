package tk.dalii.housework.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import tk.dalii.housework.R;
import tk.dalii.housework.ui.MainActivity;
import tk.dalii.housework.utils.DimenUtils;
/**
 * @author yuxuehai
 */
public class GridViewAdapter extends BaseAdapter {
	private Context mContext;
	// grid要显示的内容集合--相应的ID集合
	private ArrayList<String> mGrids;
	private ArrayList<Integer> mGridsID;
	private LayoutInflater mInflater;
	private GridView gridView;
	// 显示选择图片标志--全选标志
	private boolean visible_flag;

	/**
	 * get set 方法
	 * 
	 * @param visible_flag
	 */
	public void setVisible_flag(boolean visible_flag) {
		this.visible_flag = visible_flag;
	}

	public GridViewAdapter(Context mContext, ArrayList<String> mGrids,
			ArrayList<Integer> mGridsID, GridView gridView, boolean visible_flag) {
		super();
		this.mContext = mContext;
		this.mGrids = mGrids;
		this.mGridsID = mGridsID;
		this.mInflater = LayoutInflater.from(mContext);
		this.gridView = gridView;
		this.visible_flag = visible_flag;
	}

	/**
	 * 返回grid总数
	 */
	@Override
	public int getCount() {
		return mGrids.size();
	}

	@Override
	public Object getItem(int position) {
		return mGrids.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * 填装gird，性能优化的方式
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.viewpager_gridview_item,
					null);
			holder = new ViewHolder();

			holder.text = (TextView) convertView
					.findViewById(R.id.gridview_item_textview);
			holder.hitIndex = (TextView) convertView
					.findViewById(R.id.tv_hit_index);
			holder.image = (ImageView) convertView
					.findViewById(R.id.gridview_item_imageview);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.text.setHeight(gridView.getHeight() / 3
				- DimenUtils.Dp2Px(mContext, 7 * 2));
		holder.text.setText(mGrids.get(position));
		// 根据标志判断是否显示gridview_item_imageview
		if (visible_flag) {
			holder.image.setVisibility(View.VISIBLE);
			// 删除ID集合不为空--就检查将要初始化的ID是否包含在其中，是的话就更改gridview_item_imageview状态
			if (MainActivity.deleteGridsID != null
					&& MainActivity.deleteGridsID.contains(mGridsID
							.get(position))) {
				holder.image.setEnabled(false);
			} else {
				holder.image.setEnabled(true);
			}
		} else {
			holder.image.setVisibility(View.GONE);
		}
		// hitIds不为空--就检查要初始化的id是否包含在其中,是的话就更改背景
		if (MainActivity.hitIds != null
				&& MainActivity.hitIds.contains(mGridsID.get(position))) {
			//改变背景颜色，透明度为50%
			holder.text.setBackgroundColor(mContext.getResources().getColor(R.color.gray_main));
			holder.text.setAlpha(0.5f);
			//设置击中索引
			int hitIndex = MainActivity.hitIds.indexOf(mGridsID.get(position))+1;
			holder.hitIndex.setText(hitIndex+"");
			holder.hitIndex.setVisibility(View.VISIBLE);
		}
		return convertView;
	}

	private class ViewHolder {
		TextView text;
		TextView hitIndex = null;
		ImageView image;
	}
}
