package com.meizu.yuxuehai.googledatabinding.model.local;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by yuxuehai on 18-1-27.
 */
@Entity(tableName = "user")
public class RoomTable {

    @NonNull
    public String getId() {
        return mId;
    }

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "entryid")
    private final String mId;

    public RoomTable(@NonNull String id) {
        mId = id;
    }
}
