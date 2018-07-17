package com.example.ricardopessoa.androidgooglebooks.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class BookList {

  //List of books
  @SerializedName("items")
  private List<Book> books;

  public BookList(List<Book> books) {
    this.books = books;
  }

  public List<Book> getBooks() {
    return books;
  }

  public void setBooks(List<Book> books) {
    this.books = books;
  }
}
