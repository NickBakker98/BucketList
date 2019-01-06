package com.example.nick0.bucketlist;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

public class AddBucketItemActivity extends AppCompatActivity {

    private EditText bucketListTitle;
    private EditText bucketListDescription;
    private FloatingActionButton saveButton;
    private String mTitle;
    private String mDescription;

    public static final String EXTRA_TITLE = "a title";
    public static final String EXTRA_DESCRIPTION = "a description";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bucket_item);

        bucketListTitle = findViewById(R.id.bucketlist_title);
        bucketListDescription = findViewById(R.id.bucketlist_description);
        saveButton = findViewById(R.id.save_button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mTitle = bucketListTitle.getText().toString();
                mDescription = bucketListDescription.getText().toString();

                //Check if all the fields have been filled (else Snackbar).
                if(TextUtils.isEmpty(mTitle) || TextUtils.isEmpty(mDescription)){
                    Snackbar.make(getCurrentFocus(),"Fill in all fields.", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                //Put the data which is added to the intent.
                Intent data = new Intent();
                data.putExtra(EXTRA_TITLE,mTitle);
                data.putExtra(EXTRA_DESCRIPTION,mDescription);
                setResult(RESULT_OK,data);
                finish();
            }
        });

    }
}
