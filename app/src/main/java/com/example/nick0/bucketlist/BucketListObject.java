package com.example.nick0.bucketlist;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "bucketlist")
public class BucketListObject {

    public BucketListObject(String bucketListTitle, String bucketListDescription){
        this.bucketListTitle = bucketListTitle;
        this.bucketListDescription = bucketListDescription;
    }

    @PrimaryKey (autoGenerate = true)
    private Long id;

    @ColumnInfo(name = "bucketListTitle")
    private String bucketListTitle;

    @ColumnInfo(name= "bucketListDescription")
    private String bucketListDescription;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getBucketListTitle() {
        return bucketListTitle;
    }

    public void setBucketListTitle(String bucketListTitle) {
        this.bucketListTitle = bucketListTitle;
    }

    public String getBucketListDescription(){
        return bucketListDescription;
    }

    public void setBucketListDescription(String bucketListDescription){
        this.bucketListDescription = bucketListDescription;
    }
}
