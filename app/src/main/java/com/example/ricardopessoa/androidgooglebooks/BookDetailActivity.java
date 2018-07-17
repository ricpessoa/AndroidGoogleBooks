package com.example.ricardopessoa.androidgooglebooks;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

public class BookDetailActivity extends AppCompatActivity {

  public static final String VAR_BOOK_ID = "VAR_BOOK_ID";
  public static final String VAR_BOOK_NAME = "VAR_BOOK_NAME";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_book_detail);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    Bundle bundle = getIntent().getExtras();
    if (bundle != null) {
      String bookTitle = bundle.getString(VAR_BOOK_NAME);
      if (bookTitle != null && !bookTitle.isEmpty()) {
        setTitle(bookTitle);
      }
    }
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // handle arrow click here
    if (item.getItemId() == android.R.id.home) {
      finish(); // close this activity and return to preview activity (if there is any)
    }

    return super.onOptionsItemSelected(item);
  }

}
