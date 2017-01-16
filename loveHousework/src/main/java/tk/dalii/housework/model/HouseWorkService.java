package tk.dalii.housework.model;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
/**
 * @author yuxuehai
 */
public class HouseWorkService {
	private DataBaseHelper mDataBaseHelper;
	private SQLiteDatabase db;

	public HouseWorkService(Context mContext) {
		super();
		mDataBaseHelper = new DataBaseHelper(mContext);
	}

	/**
	 * 表hw_selected添加单个条目
	 * 
	 * @param name
	 */
	public void add_selected(Integer hwsid,String name) {
		if(!db.isOpen()){
			db = mDataBaseHelper.getWritableDatabase();
		}
		db.execSQL("INSERT INTO hw_selected(hwsid,name) values(?,?)",
				new Object[] { hwsid,name });
		db.close();
	}

	/**
	 * 表hw_selected更新指定id条目
	 * 
	 * @param id
	 * @param name
	 */
	public void update_selected(Integer id, String name) {
		db = mDataBaseHelper.getWritableDatabase();
		db.execSQL("UPDATE hw_selected set name=? WHERE id=?", new Object[] {
				name, id });
		db.close();
	}

	/**
	 * 表hw_selected查询指定id条目
	 * 
	 * @param id
	 * @return
	 */
	public String find_selected(Integer id) {
		db = mDataBaseHelper.getReadableDatabase();
		String res = null;
		Cursor cursor = db.rawQuery("SELECT name FROM hw_selected WHERE id=?",
				new String[] { id + "" });
		// 迭代记录集
		if (cursor.moveToNext()) {
			res = cursor.getString(cursor.getColumnIndex("name"));
		}
		db.close();
		return res;
	}

	/**
	 * 表hw_selected删除指定di条目
	 * 
	 * @param id
	 */
	public void delete_selected(Integer id) {
		db = mDataBaseHelper.getWritableDatabase();
		db.execSQL("delete from hw_selected WHERE id=?", new Object[] { id });
		db.close();
	}
	/**
	 * 表hw_selected删除批量指定id条目
	 * 
	 * @param id
	 */
	public void delete_selected(ArrayList<Integer> ids) {
		db = mDataBaseHelper.getWritableDatabase();
		for (int i = 0; i < ids.size(); i++) {
			db.execSQL("delete from hw_selected WHERE id=?", new Object[] { ids.get(i) });
		}
		db.close();
	}

	/**
	 * 表hw_selected按偏移量6 分页查找
	 * 
	 * @param firstIndex
	 * @param offset
	 * @return
	 */
	public ArrayList<String> getScrollData_selected(int firstIndex, int offset) {
		ArrayList<String> res = new ArrayList<String>();
		db = mDataBaseHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(
				"select name from hw_selected limit ? offset ?", new String[] {
						offset + "", firstIndex + "" });
		while (cursor.moveToNext()) {
			res.add(cursor.getString(cursor.getColumnIndex("name")));
		}
		cursor.close();
		return res;
	}

	/**
	 * 表hw_selected按偏移量6 分页查找
	 * 
	 * @param firstIndex
	 * @param offset
	 * @return
	 */
	public ArrayList<Integer> getScrollDataID_selected(int firstIndex,
			int offset) {
		ArrayList<Integer> res = new ArrayList<Integer>();
		db = mDataBaseHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(
				"select id from hw_selected limit ? offset ?", new String[] {
						offset + "", firstIndex + "" });
		while (cursor.moveToNext()) {
			res.add(cursor.getInt(cursor.getColumnIndex("id")));
		}
		cursor.close();
		return res;
	}

	/**
	 * 表hw_selected获取所有数据ID
	 * 
	 * @return
	 */
	public ArrayList<Integer> getAllDataID_selected() {
		ArrayList<Integer> res = new ArrayList<Integer>();
		db = mDataBaseHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select id from hw_selected", null);
		while (cursor.moveToNext()) {
			res.add(cursor.getInt(cursor.getColumnIndex("id")));
		}
		cursor.close();
		return res;
	}
	/**
	 * 表hw_selected获取所有数据hwsid
	 * 
	 * @return
	 */
	public ArrayList<Integer> getAllDataHWSID_selected() {
		ArrayList<Integer> res = new ArrayList<Integer>();
		db = mDataBaseHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select hwsid from hw_selected", null);
		while (cursor.moveToNext()) {
			res.add(cursor.getInt(cursor.getColumnIndex("hwsid")));
		}
		cursor.close();
		return res;
	}
	/**
	 * 表hw_selected获取所有数据
	 * 
	 * @return
	 */
	public ArrayList<String> getAllData_selected() {
		ArrayList<String> res = new ArrayList<String>();
		db = mDataBaseHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select name from hw_selected", null);
		while (cursor.moveToNext()) {
			res.add(cursor.getString(cursor.getColumnIndex("name")));
		}
		cursor.close();
		return res;
	}

	/**
	 * 表hw_selected获取总记录数
	 * 
	 * @return
	 */
	public long getCount_selected() {
		db = mDataBaseHelper.getReadableDatabase();
		// 没有占位符的时候置为空null
		Cursor cursor = db.rawQuery("select count(*)from hw_selected", null);
		cursor.moveToFirst();
		long count = cursor.getLong(0);
		cursor.close();
		return count;
	}

	/**
	 * *************************************************************************
	 * 表hw_houseworks 相关操作
	 */
	/**
	 * 表hw_houseworks添加条目
	 * 
	 * @param name
	 */
	public void add_hourseworks(String name) {
		db = mDataBaseHelper.getWritableDatabase();
		db.execSQL("INSERT INTO hw_houseworks(name) values(?)",
				new Object[] { name });
		db.close();
	}

	/**
	 * 表hw_houseworks删除指定di条目
	 * 
	 * @param id
	 */
	public void delete_hourseworks(Integer id) {
		db = mDataBaseHelper.getWritableDatabase();
		db.execSQL("delete form hw_houseworks WHERE id=?", new Object[] { id });
		db.close();
	}

	/**
	 * 表hw_houseworks获取所有数据
	 * 
	 * @return
	 */
	public ArrayList<String> getAllData_hourseworks() {
		ArrayList<String> res = new ArrayList<String>();
		db = mDataBaseHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select name from hw_houseworks", null);
		while (cursor.moveToNext()) {
			res.add(cursor.getString(cursor.getColumnIndex("name")));
		}
		cursor.close();
		return res;
	}

	/**
	 * 表hw_houseworks获取所有数据ID
	 * 
	 * @return
	 */
	public ArrayList<Integer> getAllDataID_hourseworks() {
		ArrayList<Integer> res = new ArrayList<Integer>();
		db = mDataBaseHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select id from hw_houseworks", null);
		while (cursor.moveToNext()) {
			res.add(cursor.getInt(cursor.getColumnIndex("id")));
		}
		cursor.close();
		return res;
	}
	/**
	 * 表hw_houseworks获取总记录数
	 * 
	 * @return
	 */
	public long getCount_hourseworks() {
		db = mDataBaseHelper.getReadableDatabase();
		// 没有占位符的时候置为空null
		Cursor cursor = db.rawQuery("select count(*)from hw_houseworks", null);
		cursor.moveToFirst();
		long count = cursor.getLong(0);
		cursor.close();
		return count;
	}
	/**
	 * *************************************************************************
	 * 两张表相关操作
	 */
	/**
	 * 添加选项到hw_selected
	 * @param listID
	 * @param list
	 */
	public void addToGrids(ArrayList<Integer> listID){
		db = mDataBaseHelper.getReadableDatabase();
		String name;
		for (int i = 0; i < listID.size(); i++) {
			Cursor cursor = db.rawQuery("SELECT name FROM hw_houseworks WHERE id=?",
					new String[] { listID.get(i) + "" });
			// 迭代记录集
			if (cursor.moveToNext()) {
				name = cursor.getString(cursor.getColumnIndex("name"));
				db.execSQL("INSERT INTO hw_selected(hwsid,name) values(?,?)",
						new Object[] { listID.get(i),name });
			}
		}
		db.close();
	}
	/**
	 * 删除数据 从两张表中
	 * @param listID
	 */
	public void deleteReal(ArrayList<Integer> listID){
		db = mDataBaseHelper.getReadableDatabase();
		for (int i = 0; i < listID.size(); i++) {
			db.execSQL("delete from hw_houseworks WHERE id=?", new Object[] { listID.get(i) });
			db.execSQL("delete from hw_selected WHERE hwsid=?", new Object[] { listID.get(i) });
		}
		db.close();
	}
	
}
