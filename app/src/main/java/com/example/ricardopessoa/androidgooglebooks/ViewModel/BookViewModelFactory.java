package com.example.ricardopessoa.androidgooglebooks.ViewModel;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

/**
 * This BookViewModelFactory is a workaround to create a viewmodel with a default value
 * in this case with the bookID to perform right away a search
 */
public class BookViewModelFactory extends ViewModelProvider.NewInstanceFactory {

  private Application mApplication;
  private String mBookID;


  public BookViewModelFactory(Application application, String bookID) {
    mApplication = application;
    mBookID = bookID;
  }


  @Override
  public <T extends ViewModel> T create(Class<T> modelClass) {
    return (T) new BookViewModel(mApplication, mBookID);
  }
}