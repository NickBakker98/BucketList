package com.example.nick0.bucketlist;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import java.util.List;

public class MainViewModel {
    private BucketListRepository mRepository;
    private LiveData<List<BucketListObject>> mItems;

    public MainViewModel(Context context) {
        mRepository = new BucketListRepository(context);
        mItems = mRepository.getAllItems();
    }

    public LiveData<List<BucketListObject>> getItems() {
        return mItems;
    }

    public void insert(BucketListObject item) {
        mRepository.insert(item);
    }

    public void update(BucketListObject item) {
        mRepository.update(item);
    }

    public void delete(BucketListObject item) {
        mRepository.delete(item);
    }
}
