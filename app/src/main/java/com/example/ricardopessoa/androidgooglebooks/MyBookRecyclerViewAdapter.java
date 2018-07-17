package com.example.ricardopessoa.androidgooglebooks;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ricardopessoa.androidgooglebooks.ListBooksFragment.OnBookClickLister;

import com.example.ricardopessoa.androidgooglebooks.model.Book;
import java.util.ArrayList;
import java.util.List;


public class MyBookRecyclerViewAdapter extends
    RecyclerView.Adapter<MyBookRecyclerViewAdapter.ViewHolder> {

  private List<Book> mValues = new ArrayList<>();
  private final OnBookClickLister mListener;

  public MyBookRecyclerViewAdapter(List<Book> items, OnBookClickLister listener) {
    mValues = items;
    mListener = listener;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.fragment_book, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(final ViewHolder holder, int position) {
    final Book book = mValues.get(position);
    holder.mItem = book;
    holder.mTextViewBookTitle.setText(book.getVolumeInfo().getTitle());

    if (book.getVolumeInfo().getRatingsCount() > 0) {
      holder.mTextViewBookRating
          .setText(book.getVolumeInfo().getAverageRating() + " " + holder.mView.getContext()
              .getString(R.string.start_icon));
      holder.mTextViewBookRating.setVisibility(View.VISIBLE);
    } else {
      holder.mTextViewBookRating.setVisibility(View.INVISIBLE);
    }
    if (!book.getAuthorsAndYear().isEmpty()) {
      holder.mTextViewAuthorsYear.setText(book.getAuthorsAndYear());
      holder.mTextViewAuthorsYear.setVisibility(View.VISIBLE);
    } else {
      holder.mTextViewAuthorsYear.setVisibility(View.GONE);
    }

    if (book.getVolumeInfo().getPrintType() != null) {
      holder.mTextViewTypeBook.setText(book.getVolumeInfo().getPrintType());
      holder.mTextViewTypeBook.setVisibility(View.VISIBLE);
    } else {
      holder.mTextViewTypeBook.setVisibility(View.GONE);
    }

    if (book.getVolumeInfo().getImageLinks() != null
        && book.getVolumeInfo().getImageLinks().getSmallThumbnail() != null) {
      Glide.with(holder.mView)
          .load(book.getVolumeInfo().getImageLinks().getSmallThumbnail())
          .into(holder.mImageViewBook);
    }

    holder.mView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (null != mListener) {
          mListener.OnBookClick(holder.mItem);
        }
      }
    });
  }

  @Override
  public int getItemCount() {
    return mValues.size();
  }

  public void setBookList(List<Book> bookList) {
    if (bookList != null) {
      if (mValues != null && mValues.size() > 0) {
        mValues.clear();
      }
      this.mValues.addAll(bookList);
    } else {
      this.mValues.clear();
    }
    this.notifyDataSetChanged();

  }

  public class ViewHolder extends RecyclerView.ViewHolder {

    public final View mView;
    public final ImageView mImageViewBook;
    public final TextView mTextViewBookTitle, mTextViewAuthorsYear, mTextViewTypeBook, mTextViewBookPrice, mTextViewBookRating;
    public Book mItem;

    public ViewHolder(View view) {
      super(view);
      mView = view;
      mImageViewBook = (ImageView) view.findViewById(R.id.image_view_book);
      mTextViewBookTitle = (TextView) view.findViewById(R.id.text_view_book_title);
      mTextViewAuthorsYear = (TextView) view.findViewById(R.id.text_view_book_authors_year);
      mTextViewTypeBook = (TextView) view.findViewById(R.id.text_view_book_type);
      mTextViewBookPrice = (TextView) view.findViewById(R.id.text_view_book_price);
      mTextViewBookRating = (TextView) view.findViewById(R.id.text_view_book_rating);
    }
  }
}
