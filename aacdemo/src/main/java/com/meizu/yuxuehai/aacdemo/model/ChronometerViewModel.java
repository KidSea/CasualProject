package com.meizu.yuxuehai.aacdemo.model;

import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

/**
 * Created by yuxuehai on 18-1-21.
 */

public class ChronometerViewModel extends ViewModel {

    @Nullable
    private long startDate;

    @Nullable
    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }
}
