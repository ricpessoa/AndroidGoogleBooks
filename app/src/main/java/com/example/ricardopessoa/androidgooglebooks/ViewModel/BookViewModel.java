package com.example.ricardopessoa.androidgooglebooks.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import com.example.ricardopessoa.androidgooglebooks.BuildConfig;
import com.example.ricardopessoa.androidgooglebooks.Intefaces.IGoogleBooksService;
import com.example.ricardopessoa.androidgooglebooks.Repository.GoogleBookRepository;
import com.example.ricardopessoa.androidgooglebooks.model.Book;

public class BookViewModel extends AndroidViewModel {

  private final LiveData<Book> bookObservable;
  private final String bookID;

  public BookViewModel(@NonNull Application application,
      final String bookID) {
    super(application);
    this.bookID = bookID;
    bookObservable = GoogleBookRepository.getInstance()
        .getBookDetails(bookID, BuildConfig.GC_API_KEY);
  }

  public LiveData<Book> getObservableProject() {
    return bookObservable;
  }

}
