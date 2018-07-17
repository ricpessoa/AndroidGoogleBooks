package com.example.ricardopessoa.androidgooglebooks;

import static com.example.ricardopessoa.androidgooglebooks.BookDetailActivity.VAR_BOOK_ID;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.ricardopessoa.androidgooglebooks.ViewModel.BookViewModel;
import com.example.ricardopessoa.androidgooglebooks.ViewModel.BookViewModelFactory;
import com.example.ricardopessoa.androidgooglebooks.model.Book;

/**
 * BookDetailFragment shows the details of a book
 */
public class BookDetailFragment extends Fragment {

  View rootView;
  ImageView imageViewBook;
  TextView textViewTitle, textViewSubtitle, textViewDescription;
  LinearLayout linearLayoutRating;
  ProgressBar progressBar;
  private String bookID;

  public BookDetailFragment() {
  }


  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
  }

  @Override
  public void onDetach() {
    super.onDetach();
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Bundle b = getActivity().getIntent().getExtras();
    if (b != null) {
      bookID = b.getString(VAR_BOOK_ID);
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    rootView = inflater.inflate(R.layout.fragment_book_detail, container, false);
    imageViewBook = rootView.findViewById(R.id.imageView);
    textViewTitle = rootView.findViewById(R.id.textViewTitle);
    textViewSubtitle = rootView.findViewById(R.id.textViewSubtitle);
    textViewDescription = rootView.findViewById(R.id.textViewDescription);

    linearLayoutRating = rootView.findViewById(R.id.LinearLayoutRating);
    linearLayoutRating.setVisibility(View.GONE);

    progressBar = rootView.findViewById(R.id.progress_bar);
    progressBar.setVisibility(View.VISIBLE);
    return rootView;
  }


  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    BookViewModel myViewModel = ViewModelProviders
        .of(this, new BookViewModelFactory(getActivity().getApplication(), bookID))
        .get(BookViewModel.class);
    observeViewModel(myViewModel);

  }

  //Observer the Book changes
  private void observeViewModel(final BookViewModel viewModel) {
    viewModel.getObservableProject().observe(this, new Observer<Book>() {
      @Override
      public void onChanged(@Nullable final Book book) {
        if (book != null) {
          getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
              updateBookDetails(book);
              progressBar.setVisibility(View.INVISIBLE);
            }
          });
        }
      }
    });
  }

  private void updateBookDetails(Book book) {
    if (book == null) {
      return;
    }

    if (book.getVolumeInfo().getImageLinks() != null
        && book.getVolumeInfo().getImageLinks().getSmallThumbnail() != null) {
      Glide.with(getActivity())
          .load(book.getVolumeInfo().getImageLinks().getSmallThumbnail())
          .into(imageViewBook);
    }

    textViewTitle.setText(book.getVolumeInfo().getTitle());
    if (book.getVolumeInfo().getSubtitle() != null && !book.getVolumeInfo().getSubtitle()
        .isEmpty()) {
      textViewDescription.setText(book.getVolumeInfo().getSubtitle());
    } else {
      textViewDescription.setVisibility(View.GONE);
    }

    if (book.getVolumeInfo().getRatingsCount() > 0) {
      linearLayoutRating.setVisibility(View.VISIBLE);
      ((TextView) rootView.findViewById(R.id.textViewAverageRatingValue))
          .setText(book.getVolumeInfo().getAverageRating().toString());
      ((TextView) rootView.findViewById(R.id.textVIewNumberRatingsValue))
          .setText(book.getVolumeInfo().getRatingsCount() + "");
    }

    if (book.getVolumeInfo().getDescription() != null) {
      textViewSubtitle.setText(book.getVolumeInfo().getDescription());
    } else {
      textViewDescription.setVisibility(View.GONE);
    }

  }
}

