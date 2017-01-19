package com.example.circledemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import java.text.DecimalFormat;

public class CircleBar extends View {

	private RectF mColorWheelRectangle = new RectF();
	private Paint mDefaultWheelPaint;
	private Paint mColorWheelPaint;
	private Paint mColorWheelPaintCentre;
	private Paint mTextP, mTextnum, mTextch;
	private float circleStrokeWidth;
	private float mSweepAnglePer;
	private float mPercent;
	private int stepnumber, stepnumbernow;
	private float pressExtraStrokeWidth;
	private BarAnimation anim;
	private int stepnumbermax = 6000;// 默认最大步数
	private float mPercent_y, stepnumber_y, Text_y;
	private DecimalFormat fnum = new DecimalFormat("#.0");// 格式为保留小数点后一位

	public CircleBar(Context context) {
		super(context);
		init(null, 0);
	}

	public CircleBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs, 0);
	}

	public CircleBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(attrs, defStyle);
	}

	private void init(AttributeSet attrs, int defStyle) {

		mColorWheelPaint = new Paint();
		mColorWheelPaint.setColor(Color.rgb(249, 135, 49));
		mColorWheelPaint.setStyle(Paint.Style.STROKE);// 空心
		mColorWheelPaint.setStrokeCap(Paint.Cap.ROUND);// 圆角画笔
		mColorWheelPaint.setAntiAlias(true);// 去锯齿

		mColorWheelPaintCentre = new Paint();
		mColorWheelPaintCentre.setColor(Color.rgb(250, 250, 250));
		mColorWheelPaintCentre.setStyle(Paint.Style.STROKE);
		mColorWheelPaintCentre.setStrokeCap(Paint.Cap.ROUND);
		mColorWheelPaintCentre.setAntiAlias(true);

		mDefaultWheelPaint = new Paint();
		mDefaultWheelPaint.setColor(Color.rgb(127, 127, 127));
		mDefaultWheelPaint.setStyle(Paint.Style.STROKE);
		mDefaultWheelPaint.setStrokeCap(Paint.Cap.ROUND);
		mDefaultWheelPaint.setAntiAlias(true);

		mTextP = new Paint();
		mTextP.setAntiAlias(true);
		mTextP.setColor(Color.rgb(249, 135, 49));

		mTextnum = new Paint();
		mTextnum.setAntiAlias(true);
		mTextnum.setColor(Color.BLACK);

		mTextch = new Paint();
		mTextch.setAntiAlias(true);
		mTextch.setColor(Color.BLACK);

		anim = new BarAnimation();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawArc(mColorWheelRectangle, 0, 359, false, mDefaultWheelPaint);
		canvas.drawArc(mColorWheelRectangle, 0, 359, false,
				mColorWheelPaintCentre);
		canvas.drawArc(mColorWheelRectangle, 90, mSweepAnglePer, false,
				mColorWheelPaint);
		canvas.drawText(mPercent + "%", mColorWheelRectangle.centerX()
				- (mTextP.measureText(String.valueOf(mPercent) + "%") / 2),
				mPercent_y, mTextP);
		canvas.drawText(stepnumbernow + "", mColorWheelRectangle.centerX()
				- (mTextnum.measureText(String.valueOf(stepnumbernow)) / 2),
				stepnumber_y, mTextnum);
		canvas.drawText(
				"步数",
				mColorWheelRectangle.centerX()
						- (mTextch.measureText(String.valueOf("步数")) / 2),
				Text_y, mTextch);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int height = getDefaultSize(getSuggestedMinimumHeight(),
				heightMeasureSpec);
		int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
		int min = Math.min(width, height);// 获取View最短边的长度
		setMeasuredDimension(min, min);// 强制改View为以最短边为长度的正方形
		circleStrokeWidth = Textscale(40, min);// 圆弧的宽度
		pressExtraStrokeWidth = Textscale(2, min);// 圆弧离矩形的距离
		mColorWheelRectangle.set(circleStrokeWidth + pressExtraStrokeWidth,
				circleStrokeWidth + pressExtraStrokeWidth, min
						- circleStrokeWidth - pressExtraStrokeWidth, min
						- circleStrokeWidth - pressExtraStrokeWidth);// 设置矩形
		mTextP.setTextSize(Textscale(80, min));
		mTextnum.setTextSize(Textscale(160, min));
		mTextch.setTextSize(Textscale(50, min));
		mPercent_y = Textscale(190, min);
		stepnumber_y = Textscale(330, min);
		Text_y = Textscale(400, min);
		mColorWheelPaint.setStrokeWidth(circleStrokeWidth);
		mColorWheelPaintCentre.setStrokeWidth(circleStrokeWidth);
		mDefaultWheelPaint
				.setStrokeWidth(circleStrokeWidth - Textscale(2, min));
		mDefaultWheelPaint.setShadowLayer(Textscale(10, min), 0, 0,
				Color.rgb(127, 127, 127));// 设置阴影
	}

	/**
	 * 进度条动画
	 * 
	 * @author Administrator
	 * 
	 */
	public class BarAnimation extends Animation {
		public BarAnimation() {

		}

		/**
		 * 每次系统调用这个方法时， 改变mSweepAnglePer，mPercent，stepnumbernow的值，
		 * 然后调用postInvalidate()不停的绘制view。
		 */
		@Override
		protected void applyTransformation(float interpolatedTime,
				Transformation t) {
			super.applyTransformation(interpolatedTime, t);
			if (interpolatedTime < 1.0f) {
				mPercent = Float.parseFloat(fnum.format(interpolatedTime
						* stepnumber * 100f / stepnumbermax));// 将浮点值四舍五入保留一位小数
				mSweepAnglePer = interpolatedTime * stepnumber * 360
						/ stepnumbermax;
				stepnumbernow = (int) (interpolatedTime * stepnumber);
			} else {
				mPercent = Float.parseFloat(fnum.format(stepnumber * 100f
						/ stepnumbermax));// 将浮点值四舍五入保留一位小数
				mSweepAnglePer = stepnumber * 360 / stepnumbermax;
				stepnumbernow = stepnumber;
			}
			postInvalidate();
		}
	}

	/**
	 * 根据控件的大小改变绝对位置的比例
	 * 
	 * @param n
	 * @param m
	 * @return
	 */
	public float Textscale(float n, float m) {
		return n / 500 * m;
	}

	/**
	 * 更新步数和设置一圈动画时间
	 * 
	 * @param stepnumber
	 * @param time
	 */
	public void update(int stepnumber, int time) {
		this.stepnumber = stepnumber;
		anim.setDuration(time);
		//setAnimationTime(time);
		this.startAnimation(anim);
	}

	/**
	 * 设置每天的最大步数
	 * 
	 * @param Maxstepnumber
	 */
	public void setMaxstepnumber(int Maxstepnumber) {
		stepnumbermax = Maxstepnumber;
	}

	/**
	 * 设置进度条颜色
	 * 
	 * @param red
	 * @param green
	 * @param blue
	 */
	public void setColor(int red, int green, int blue) {
		mColorWheelPaint.setColor(Color.rgb(red, green, blue));
	}

	/**
	 * 设置动画时间
	 * 
	 * @param time
	 */
	public void setAnimationTime(int time) {
		anim.setDuration(time * stepnumber / stepnumbermax);// 按照比例设置动画执行时间
	}

}
