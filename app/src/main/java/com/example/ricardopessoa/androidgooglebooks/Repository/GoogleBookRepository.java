package com.example.ricardopessoa.androidgooglebooks.Repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;
import com.example.ricardopessoa.androidgooglebooks.Intefaces.IGoogleBooksService;
import com.example.ricardopessoa.androidgooglebooks.model.Book;
import com.example.ricardopessoa.androidgooglebooks.model.BookList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GoogleBookRepository {

  public static final String TAG = GoogleBookRepository.class.getCanonicalName();

  private IGoogleBooksService googleBooksService;
  private static GoogleBookRepository projectRepository;

  private GoogleBookRepository() {
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(IGoogleBooksService.GOOGLE_BOOK_SERVICE_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build();

    googleBooksService = retrofit.create(IGoogleBooksService.class);
  }


  public static GoogleBookRepository getInstance() {
    if (projectRepository == null) {
      projectRepository = new GoogleBookRepository();

    }
    return projectRepository;
  }


  public void getBookList(String searchQuery, int startIndex, int maxResults,
      String googleAPIKey, Callback<BookList> callBack) {

    googleBooksService.getListOfBooks(searchQuery, startIndex, maxResults, googleAPIKey)
        .enqueue(callBack);
  }

  public LiveData<Book> getBookDetails(String bookID, String googleAPIKey) {
    final MutableLiveData<Book> data = new MutableLiveData<>();
    googleBooksService.getBook(bookID, googleAPIKey).enqueue(new Callback<Book>() {
      @Override
      public void onResponse(Call<Book> call, Response<Book> response) {
        if (response.isSuccessful() && response.body() != null) {
          data.setValue(response.body());
        }
      }

      @Override
      public void onFailure(Call<Book> call, Throwable t) {
        Log.d("","TESTADASDFASDASDSADSA");
      }
    });

    return data;
  }
}
