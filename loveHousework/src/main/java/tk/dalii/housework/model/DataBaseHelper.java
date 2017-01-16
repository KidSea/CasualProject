package tk.dalii.housework.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 自定义DataBaseHelper类
 *
 * @author yuxuehai
 *
 */
public class DataBaseHelper extends SQLiteOpenHelper {

	// database name
	private static final String NAME = "housework.db";
	// database version
	private static final int VERSION = 1;
	// 初始化表数据数组
	private String[] houseworks = { "扫地", "洗碗", "做饭", "买菜", "洗衣服", "倒垃圾",
			"包干", "歇着", "唱歌", "跳舞" };

	// database 构造函数
	public DataBaseHelper(Context context) {
		super(context, NAME, null, VERSION);
	}

	public DataBaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, NAME, factory, VERSION);
	}

	/**
	 * 第一次创建数据库是时候被调用 创建 hw_selected表
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// 新建hw_selected表，存储的为gridview中选择项
		db.execSQL("CREATE TABLE hw_selected("
				+ "id integer primary key autoincrement,hwsid integer," + "name varchar(16)"
				+ ")");
		// 新建hw_houseworks表，存储的为listview中选择项--所有家务条目
		db.execSQL("CREATE TABLE hw_houseworks("
				+ "id integer primary key autoincrement," + "name varchar(16)"
				+ ")");
		// 初始化数据
		for (int i = 0; i < houseworks.length; i++) {
			//初始化表hw_houseworks
			db.execSQL("INSERT INTO hw_houseworks(name) values(?)",
					new Object[] { houseworks[i]});
			//初始化表hw_selected
			if(i<6){
				db.execSQL("INSERT INTO hw_selected(hwsid,name) values(?,?)",
						new Object[] { i+1,houseworks[i]});
			}
		}
	}

	/**
	 * 数据库版本更新 删除原表 重新创建
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS hw_selected");
		db.execSQL("DROP TABLE IF EXISTS hw_houseworks");
		onCreate(db);
	}

}
