package com.example.mygame2048.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

import com.example.mygame2048.utis.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @yuxuehai
 */
public class GameView extends GridLayout {
	
	private Card[][] cardsMap=new Card[4][4];
	private List<Point>emptyPoint=new ArrayList<Point>();
	
	private static int cardWidth=0;

	public GameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initGameView();
	}

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initGameView();
	}

	public GameView(Context context) {
		super(context);
		initGameView();
	}
	
	public static int getCardWidth() {
		return cardWidth;
	}

	private void initGameView(){
		
		setColumnCount(4);
		setBackgroundColor(0xffbbada0);
		
		setOnTouchListener(new OnTouchListener() {
			private float startX,startY,offsetX,offsetY;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					startX=event.getX();
					startY=event.getY();
					break;
				case MotionEvent.ACTION_UP:
					offsetX=event.getX()-startX;
					offsetY=event.getY()-startY;
					if (Math.abs(offsetX) > Math.abs(offsetY)) {
						if (offsetX > 5) {
							swipeRight();
						} else if (offsetX < -5) {
							swipeLeft();
						}
					} else {
						if (offsetY > 5) {
							swipeDown();
						} else if (offsetY < -5) {
							swipeUp();
						}
					}
					break;
				default:
					break;
				}
				return true;
			}
		});
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		
		cardWidth=(Math.min(w, h)-10)/4;
		addCards(cardWidth,cardWidth);
		
		startGame();
	}
	
	public void startGame(){
		
		ObjectUtils.getMainActivity().clearScore();
		ObjectUtils.getMainActivity().showBestScore(ObjectUtils.getMainActivity().getBestScore());
		
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				cardsMap[x][y].setNum(0);
			}	
		}
		getRandomNum();
		getRandomNum();
	}
	
	private void getRandomNum(){	
		emptyPoint.clear();
		
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				if(cardsMap[x][y].getNum()<=0){
					emptyPoint.add(new Point(x,y));
				}
			}
		}
		if(emptyPoint.size()>0){
			Point p=emptyPoint.remove((int)(Math.random()*emptyPoint.size()));
			cardsMap[p.x][p.y].setNum(Math.random()>0.1?2:4);

			ObjectUtils.getMainActivity().getAnimLayer().creatScaleTo1(cardsMap[p.x][p.y]);
		}
	}
	
	private void addCards(int cardWith,int cardHeight){
		Card card=null;
		for (int y = 0; y < 4; y++){
			for (int x = 0; x < 4; x++) {
				card=new Card(getContext());
				card.setNum(0);
				addView(card, cardWith, cardHeight);
				
				cardsMap[x][y]=card;
			}
		}
	}

	public void checkGameIsEnd(){
		boolean gameIsEnd=true;
		LABEL1:
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				if(cardsMap[x][y].getNum()==0||
						(x>0&&cardsMap[x][y].equals(cardsMap[x-1][y]))||
						(x<3&&cardsMap[x][y].equals(cardsMap[x+1][y]))||
						(y>0&&cardsMap[x][y].equals(cardsMap[x][y-1]))||
						(y<3&&cardsMap[x][y].equals(cardsMap[x][y+1]))){
					gameIsEnd=false;
					break LABEL1;
				}
			}
		}
		if(gameIsEnd){
			new AlertDialog.Builder(getContext()).setTitle("提示").setMessage("游戏结束！").setPositiveButton("重新开始", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					startGame();
				}
			}).show();
		}
	}
	
	private void swipeLeft(){
		boolean IsAddCard=false;
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				for(int x1=x+1; x1<4; x1++){
					if(cardsMap[x1][y].getNum()>0){
						if(cardsMap[x][y].getNum()<=0){
							ObjectUtils.getMainActivity().getAnimLayer().creatTranAnimLayer(cardsMap[x1][y], cardsMap[x][y], x1, x, y, y);
							
							cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
							cardsMap[x1][y].setNum(0);
							
							x--;
							IsAddCard=true;
						}else if(cardsMap[x1][y].equals(cardsMap[x][y])){
							ObjectUtils.getMainActivity().getAnimLayer().creatTranAnimLayer(cardsMap[x1][y], cardsMap[x][y], x1, x, y, y);
							
							cardsMap[x][y].setNum(cardsMap[x1][y].getNum() * 2);
							cardsMap[x1][y].setNum(0);
							ObjectUtils.getMainActivity().addScore(cardsMap[x][y].getNum());
							IsAddCard = true;
						}
						break;
					}
				}
			}
		}
		if (IsAddCard == true) {
			getRandomNum();
			checkGameIsEnd();
		}
	}
	
	private void swipeRight(){
		boolean IsAddCard=false;
		for (int y = 0; y < 4; y++) {
			for (int x = 3; x >=0; x--) {
				for(int x1=x-1; x1>=0; x1--){
					if(cardsMap[x1][y].getNum()>0){
						if(cardsMap[x][y].getNum()<=0){
							ObjectUtils.getMainActivity().getAnimLayer().creatTranAnimLayer(cardsMap[x1][y], cardsMap[x][y], x1, x, y, y);
							
							cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
							cardsMap[x1][y].setNum(0);

							x++;
							IsAddCard=true;
						}else if(cardsMap[x1][y].equals(cardsMap[x][y])){
							ObjectUtils.getMainActivity().getAnimLayer().creatTranAnimLayer(cardsMap[x1][y], cardsMap[x][y], x1, x, y, y);
							
							cardsMap[x][y].setNum(cardsMap[x1][y].getNum() * 2);
							cardsMap[x1][y].setNum(0);
							ObjectUtils.getMainActivity().addScore(cardsMap[x][y].getNum());
							IsAddCard = true;
						}									      
						break;
					}
				}
			}
		}
		if (IsAddCard == true) {
			getRandomNum();
			checkGameIsEnd();
		}
	}
	
	private void swipeUp(){
		boolean IsAddCard=false;
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				for(int y1=y+1; y1<4; y1++){
					if(cardsMap[x][y1].getNum()>0){
						if(cardsMap[x][y].getNum()<=0){
							ObjectUtils.getMainActivity().getAnimLayer().creatTranAnimLayer(cardsMap[x][y1], cardsMap[x][y], x, x, y1, y);
							
							cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
							cardsMap[x][y1].setNum(0);

							y--;
							IsAddCard=true;
						}else if(cardsMap[x][y1].equals(cardsMap[x][y])){
							ObjectUtils.getMainActivity().getAnimLayer().creatTranAnimLayer(cardsMap[x][y1], cardsMap[x][y], x, x, y1, y);
							
							cardsMap[x][y].setNum(cardsMap[x][y1].getNum() * 2);
							cardsMap[x][y1].setNum(0);
							ObjectUtils.getMainActivity().addScore(cardsMap[x][y].getNum());
							IsAddCard = true;
						}
						break;
					}
				}
			}
		}
		if (IsAddCard == true) {
			getRandomNum();
			checkGameIsEnd();
		}
	}
	
	private void swipeDown(){
		boolean IsAddCard=false;
		for (int x = 0; x < 4; x++) {
			for (int y = 3; y >=0; y--) {
				for(int y1=y-1; y1>=0; y1--){
					if(cardsMap[x][y1].getNum()>0){
						if(cardsMap[x][y].getNum()<=0){
							ObjectUtils.getMainActivity().getAnimLayer().creatTranAnimLayer(cardsMap[x][y1], cardsMap[x][y], x, x, y1, y);
							
							cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
							cardsMap[x][y1].setNum(0);

							y++;
							IsAddCard=true;
						}else if(cardsMap[x][y1].equals(cardsMap[x][y])){
							ObjectUtils.getMainActivity().getAnimLayer().creatTranAnimLayer(cardsMap[x][y1], cardsMap[x][y], x, x, y1, y);
							
							cardsMap[x][y].setNum(cardsMap[x][y1].getNum() * 2);
							cardsMap[x][y1].setNum(0);
							ObjectUtils.getMainActivity().addScore(cardsMap[x][y].getNum());
							IsAddCard = true;
						}
						break;
					}
				}
			}
		}
		if(IsAddCard==true){
			getRandomNum();
			checkGameIsEnd();
		}
	}
}
