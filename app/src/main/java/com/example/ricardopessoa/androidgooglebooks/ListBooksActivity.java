package com.example.ricardopessoa.androidgooglebooks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class ListBooksActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
  }

  @Override
  protected void onNewIntent(Intent intent) {
    setIntent(intent);

    Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment);
    // not good, but we need to send the data and action to fragment perform the search
    if (fragment instanceof ListBooksFragment) {
      ((ListBooksFragment) fragment).performSearchQuery(intent);
    }
  }
}
