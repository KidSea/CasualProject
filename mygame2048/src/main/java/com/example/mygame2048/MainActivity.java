package com.example.mygame2048;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mygame2048.dao.Init;
import com.example.mygame2048.ui.AnimLayer;
import com.example.mygame2048.ui.GameView;
import com.example.mygame2048.utis.ObjectUtils;

public class MainActivity extends AppCompatActivity implements Init, View.OnClickListener {


    public static final String SP_KEY_BEST_SCORE = "bestScore";

    private TextView mScore;
    private TextView mHScore;
    private GameView mGameview;
    private AnimLayer mAnimLayer;
    private Button mGmBt;

    private int score = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ObjectUtils.setMainActivity(this);

        initData();
        initView();

    }

    public AnimLayer getAnimLayer() {
        return mAnimLayer;
    }

    public void clearScore() {
        score = 0;
        showScore();
    }

    private void showScore() {
        mScore.setText(score + "");
    }

    public void addScore(int s) {
        score += s;
        showScore();

        int maxScore = Math.max(score, getBestScore());
        saveBestScore(maxScore);
        showBestScore(maxScore);

    }

    public void saveBestScore(int s) {
        SharedPreferences.Editor e = getPreferences(MODE_PRIVATE).edit();
        e.putInt(SP_KEY_BEST_SCORE, s);
        e.commit();
    }

    public void showBestScore(int s) {
        mHScore.setText(s + "");
    }

    public int getBestScore() {
        return getPreferences(MODE_PRIVATE).getInt(SP_KEY_BEST_SCORE, 0);
    }

    @Override
    public void initView() {
        mScore = (TextView) findViewById(R.id.tv_score);
        mHScore = (TextView) findViewById(R.id.tv_max_score);

        mGameview = (GameView) findViewById(R.id.my_gameview);
        mAnimLayer = (AnimLayer) findViewById(R.id.my_animlayer);

        mGmBt = (Button) findViewById(R.id.bt_new);
        mGmBt.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        if (mGameview != null) {
            mGameview.startGame();
        }

    }
}
