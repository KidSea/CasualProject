package tk.dalii.housework.controller;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * 检测手机摇晃的监听器
 * 
 * @author yuxuehai
 * 
 */
public class ShakeListener implements SensorEventListener {
	// 速度阀值，当摇晃速度到达这值后产生作用
	private static final int SPEED_THRESHOLD = 2000;
	// 两次检测的相隔时间
	private static final int UPDATE_INTERVAL_TIME = 100;
	// 传感感应器
	private SensorManager sensorManager;
	// 传感器
	private Sensor sensor;
	// 重力感应监听器
	private OnShakeListener onShakeListener;
	// 上下文
	private Context mContext;
	// 手机上一个位置时重力感应坐标
	private float lastX;
	private float lastY;
	private float lastZ;
	// 上次检测时间
	private long lastUpdateTime;
	//时间间隔
	long timeInterval;
	// 构造器
	public ShakeListener(Context c) {
		// 获得监听对象
		mContext = c;
		start();
	}

	/**
	 * 开始
	 */
	public void start() {
		// 获得传感感应器
		if(sensorManager==null){
			sensorManager = (SensorManager) mContext
					.getSystemService(Context.SENSOR_SERVICE);
		}
		if (sensorManager != null) {
			// 获取重力感应器
			sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		}
		// 注册
		if (sensor != null) {
			// 三个参数：监听器，感应器，感应精度
			sensorManager.registerListener(this, sensor,
					SensorManager.SENSOR_DELAY_GAME);
		}
	}

	/**
	 * 停止检测
	 */
	public void stop() {
		sensorManager.unregisterListener(this);
	}

	// 设置重力感应监听器
	public void setOnShakeListener(OnShakeListener listener) {
		onShakeListener = listener;
	}

	// 获取重力感应监听器
	public OnShakeListener getOnShakeListener() {
		return onShakeListener;
	}

	/**
	 * 重力感应获取变化数据
	 */
	@Override
	public void onSensorChanged(SensorEvent event) {
		//检查两次调用时间间隔
		if(checkInterval()){
			return;
		}
		// 获得xyz坐标
		float x = event.values[0];
		float y = event.values[1];
		float z = event.values[2];
		// 获得xyz变化值
		float deltaX = x - lastX;
		float deltaY = x - lastY;
		float deltaZ = x - lastZ;
		// 现在的坐标 变成last坐标
		lastX = x;
		lastY = y;
		lastZ = z;

		// 开方
		double speed = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ
				* deltaZ)
				/ timeInterval * 10000;
		// 达到速度阀值，发出提示
		if (speed >= SPEED_THRESHOLD) {
			onShakeListener.onShake();
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	// 摇晃监听接口
	public interface OnShakeListener {
		public void onShake();
	}

	/**
	 * 检测两次调用onShake方法时间间隔
	 * 
	 * @return
	 */
	public boolean checkInterval() {
		// 现在检测时间
		long currentUpdateTime = System.currentTimeMillis();
		// 两次时间间隔
		timeInterval = currentUpdateTime - lastUpdateTime;
		// 判断是否到了时间间隔
		if (timeInterval < UPDATE_INTERVAL_TIME) {
			return true;
		}
		// 现在的时间变成上次时间
		lastUpdateTime = currentUpdateTime;
		return false;
	}
}
