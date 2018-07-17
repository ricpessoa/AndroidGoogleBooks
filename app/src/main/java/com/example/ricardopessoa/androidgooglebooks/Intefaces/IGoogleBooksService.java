package com.example.ricardopessoa.androidgooglebooks.Intefaces;

import com.example.ricardopessoa.androidgooglebooks.model.Book;
import com.example.ricardopessoa.androidgooglebooks.model.BookList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IGoogleBooksService {
  String GOOGLE_BOOK_SERVICE_BASE_URL = "https://www.googleapis.com/";

  @GET("books/v1/volumes/{bookID}")
  Call<Book> getBook(@Path("bookID") String bookID, @Query("key") String keyApi);

  @GET("books/v1/volumes")
  Call<BookList> getListOfBooks(@Query("q") String seachQuery, @Query("startIndex") int startIndex,
      @Query("maxResults") int maxResults, @Query("keyes&key") String keyApi);
}
