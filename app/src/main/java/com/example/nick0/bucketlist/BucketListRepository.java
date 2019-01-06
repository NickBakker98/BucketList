package com.example.nick0.bucketlist;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class BucketListRepository {
    private AppDatabase mAppDatabase;
    private BucketlistDAO mBucketListDao;
    private LiveData<List<BucketListObject>> mItems;
    private Executor mExecutor = Executors.newSingleThreadExecutor();

    public BucketListRepository (Context context) {
        mAppDatabase = AppDatabase.getInstance(context);
        mBucketListDao = mAppDatabase.bucketlistDAO();
        mItems = mBucketListDao.getAllItems();
    }

    public LiveData<List<BucketListObject>> getAllItems() {
        return mItems;
    }

    public void insert(final BucketListObject item) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mBucketListDao.insertItems(item);
            }
        });
    }

    public void update(final BucketListObject item) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mBucketListDao.updateItems(item);
            }
        });
    }

    public void delete(final BucketListObject item) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mBucketListDao.deleteItems(item);
            }
        });
    }
}
