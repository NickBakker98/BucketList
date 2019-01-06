package com.example.nick0.bucketlist;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface BucketlistDAO {

    @Query("SELECT * FROM bucketlist")
    //List<BucketListObject> getAllItems();
    LiveData<List<BucketListObject>> getAllItems();

    @Insert
    void insertItems(BucketListObject items);

    @Delete
    void deleteItems(BucketListObject items);

    @Update
    void updateItems(BucketListObject items);
}
