package com.example.ricardopessoa.androidgooglebooks.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;
import com.example.ricardopessoa.androidgooglebooks.Repository.GoogleBookRepository;
import com.example.ricardopessoa.androidgooglebooks.model.Book;
import com.example.ricardopessoa.androidgooglebooks.model.BookList;
import com.example.ricardopessoa.androidgooglebooks.model.ImageLinks;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListBooksViewModel extends AndroidViewModel {

  private static String TAG = ListBooksViewModel.class.getCanonicalName();
  private final MutableLiveData<List<Book>> listOfBooks;
  final MutableLiveData<Throwable> error;
  private String searchQuery = "android";
  private int startIndex = 0;
  private int maxResults = 20;

  public ListBooksViewModel(Application application) {
    super(application);
    listOfBooks = new MutableLiveData<>();
    error = new MutableLiveData<>();
    startNewSearch(searchQuery);
  }

  public MutableLiveData<List<Book>> getProjectListObservable() {
    return listOfBooks;
  }

  public MutableLiveData<Throwable> getErrorObservable() {
    return error;
  }

  private void getListOfBooks(String searchQuery, int startIndex, int maxResutls,
      Callback<BookList> callback) {
    GoogleBookRepository.getInstance()
        .getBookList(searchQuery, startIndex, maxResutls, GoogleBookRepository.TAG, callback);
  }

  public void loadMoreBooks() {
    this.startIndex = startIndex + 1;
    getListOfBooks(searchQuery, startIndex, this.maxResults, new Callback<BookList>() {
      @Override
      public void onResponse(Call<BookList> call, Response<BookList> response) {
        if (response != null && response.isSuccessful()) {
          if (listOfBooks.getValue() == null || listOfBooks.getValue().size() == 0) {
            listOfBooks.setValue(response.body().getBooks());
          } else {
            List<Book> listBooks = listOfBooks.getValue();
            listBooks.addAll(listBooks.size(), response.body().getBooks());
            listOfBooks.postValue(listBooks);
          }
        }
      }

      @Override
      public void onFailure(Call<BookList> call, Throwable t) {
        Log.d(TAG, "loadMoreBooks onFailure " + t.getMessage());
        error.setValue(t);
      }
    });
  }

  public void startNewSearch(String search) {
    if (search != null) {
      this.searchQuery = search;
    }

    this.startIndex = 0;
    getListOfBooks(searchQuery, startIndex, maxResults, new Callback<BookList>() {
      @Override
      public void onResponse(Call<BookList> call, Response<BookList> response) {
        if (response != null && response.isSuccessful()) {
          if (listOfBooks.getValue() != null) {
            listOfBooks.getValue().clear();
          }
          listOfBooks.setValue(response.body().getBooks());
        }
      }

      @Override
      public void onFailure(Call<BookList> call, Throwable t) {
        Log.d(TAG, "startNewSearch onFailure " + t.getMessage());
        error.setValue(t);
      }
    });
  }

  public String getImageURL(Book book) {
    if (book == null || book.getVolumeInfo() == null
        || book.getVolumeInfo().getImageLinks() == null) {
      return null;
    }

    ImageLinks imageLinks = book.getVolumeInfo().getImageLinks();
    if (!imageLinks.getSmallThumbnail().isEmpty()) {// TODO: check if url
      return imageLinks.getSmallThumbnail();
    }

    if (!imageLinks.getThumbnail().isEmpty()) {
      return imageLinks.getThumbnail();
    }

    return null;

  }


}
