package com.example.nick0.bucketlist;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Create the variables.
    private FloatingActionButton add_fab;
    private RecyclerView mBucketListRecyclerView;
    private MainViewModel mMainViewModel;

    List<BucketListObject> mBucketlistObjects = new ArrayList<>();
    BucketListAdapter mAdapter = new BucketListAdapter(this, mBucketlistObjects);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //This is the MainViewModel which handles the database.
        mMainViewModel = new MainViewModel(getApplicationContext());
        mMainViewModel.getItems().observe(this, new Observer<List<BucketListObject>>() {
            @Override
            public void onChanged(@Nullable List<BucketListObject> items) {
                mBucketlistObjects = items;
                mAdapter.notifyDataSetChanged();
                mAdapter.swapList(mBucketlistObjects);
            }
        });

        //Linking variables to the xml.file
        add_fab = findViewById(R.id.add_item_button);
        mBucketListRecyclerView = findViewById(R.id.recyclerView);

        //Setting the RecyclerView.
        RecyclerView.LayoutManager mLayoutManager = new StaggeredGridLayoutManager(
                1, LinearLayoutManager.VERTICAL);
        mBucketListRecyclerView.setLayoutManager(mLayoutManager);
        mBucketListRecyclerView.setAdapter(mAdapter);

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView,
                                  RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                return false;
            }

            //Deleting the items by swiping.
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int position = (viewHolder.getAdapterPosition());
                final BucketListObject bucketListObject = mBucketlistObjects.get(position);
                mMainViewModel.delete(mBucketlistObjects.get(position));
                mBucketlistObjects.remove(position);
                Toast.makeText(MainActivity.this, "Deleted: " + bucketListObject.getBucketListTitle(), Toast.LENGTH_LONG).show();
            }
        };

        //Attach the swipe to the recyclerView.
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mBucketListRecyclerView);

        //Add an OnClickListener to the add_fab.
        add_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddBucketItemActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    //Get the data from the AddBucketItemActivity and display this in the list.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String tmpTitle, tmpDescription;
        if (resultCode == RESULT_OK) {
            tmpTitle = data.getStringExtra(AddBucketItemActivity.EXTRA_TITLE);
            tmpDescription = data.getStringExtra(AddBucketItemActivity.EXTRA_DESCRIPTION);
            BucketListObject newItem = new BucketListObject(tmpTitle, tmpDescription);
            mBucketlistObjects.add(newItem);
            mAdapter.notifyDataSetChanged();
            mAdapter.swapList(mBucketlistObjects);
            mMainViewModel.insert(newItem);
        }
    }
}
