package com.example.nick0.bucketlist;

import android.content.Intent;
import android.os.AsyncTask;
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

    private FloatingActionButton add_fab;
    private RecyclerView mBucketListRecyclerView;

    List<BucketListObject> mBucketlistObjects = new ArrayList<>();
    BucketListAdapter mAdapter = new BucketListAdapter(this, mBucketlistObjects);

    static AppDatabase db;

    public final static int TASK_GET_ALL_ITEMS = 0;
    public final static int TASK_DELETE_ITEMS = 1;
    public final static int TASK_UPDATE_ITEMS = 2;
    public final static int TASK_INSERT_ITEMS = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = AppDatabase.getInstance(this);
        mBucketlistObjects = db.bucketlistDAO().getAllItems();

        add_fab = findViewById(R.id.add_item_button);

        mBucketListRecyclerView = findViewById(R.id.recyclerView);
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

            //Deleting the games by swiping.
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int position = (viewHolder.getAdapterPosition());
                final BucketListObject bucketListObject = mBucketlistObjects.get(position);
                //new BucketListAsyncTask(TASK_DELETE_ITEMS).execute(bucketListObject);
                db.bucketlistDAO().deleteItems(mBucketlistObjects.get(position));
                mBucketlistObjects.remove(position);
                mAdapter.notifyItemRemoved(position);
                Toast.makeText(MainActivity.this, "Deleted: " + bucketListObject.getBucketListTitle(), Toast.LENGTH_LONG).show();
            }
        };



        add_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddBucketItemActivity.class);
                startActivityForResult(intent, 0);
            }
        });

    }

    private void updateUI() {
        if (mAdapter == null) {

            mAdapter = new BucketListAdapter(this, mBucketlistObjects);
            mBucketListRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.swapList(mBucketlistObjects);
        }
    }

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
            db.bucketlistDAO().insertItems(newItem);
        }
    }

    public class BucketListAsyncTask extends AsyncTask<BucketListObject, Void, List> {
        private int taskCode;

        public BucketListAsyncTask(int taskCode) {
            this.taskCode = taskCode;
        }

        @Override
        protected List doInBackground(BucketListObject... items) {
            switch (taskCode){
                case TASK_DELETE_ITEMS:
                    db.bucketlistDAO().deleteItems(items[0]);
                    break;
                case TASK_UPDATE_ITEMS:
                    db.bucketlistDAO().updateItems(items[0]);
                    break;
                case TASK_INSERT_ITEMS:
                    db.bucketlistDAO().insertItems(items[0]);
                    break;
            }
            //To return a new list with the updated data, we get all the data from the database again.
            return db.bucketlistDAO().getAllItems();
        }

        @Override
        protected void onPostExecute(List list) {
            super.onPostExecute(list);
            onItemDbUpdated(list);
        }


        public void onItemDbUpdated(List list) {
            mBucketlistObjects = list;
            mAdapter.swapList(mBucketlistObjects);
        }
    }
}
