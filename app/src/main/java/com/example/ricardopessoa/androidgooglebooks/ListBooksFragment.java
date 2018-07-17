package com.example.ricardopessoa.androidgooglebooks;

import android.app.SearchManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.ricardopessoa.androidgooglebooks.ViewModel.ListBooksViewModel;
import com.example.ricardopessoa.androidgooglebooks.model.Book;
import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p />
 * Activities containing this fragment MUST implement the {@link OnBookClickLister}
 * interface.
 */
public class ListBooksFragment extends Fragment {

  private MyBookRecyclerViewAdapter mAdapter;
  private LinearLayoutManager layoutManager;
  private OnBookClickLister mListener;
  public static final int PAGE_SIZE = 20;

  private boolean isLastPage = false;
  private boolean isLoading = false;
  SwipeRefreshLayout swipeRefreshLayout;
  private ProgressBar progressBar;

  public ListBooksFragment() {
  }


  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_book_list, container, false);

    Context context = view.getContext();
    RecyclerView recyclerView = view.findViewById(R.id.list);
    progressBar = view.findViewById(R.id.progress_bar);

    mListener = new OnBookClickLister() {
      @Override
      public void OnBookClick(Book item) {
        Intent intent = new Intent(getActivity(), BookDetailActivity.class);
        intent.putExtra(BookDetailActivity.VAR_BOOK_ID, item.getId());
        intent.putExtra(BookDetailActivity.VAR_BOOK_NAME, item.getVolumeInfo().getTitle());
        startActivity(intent);
      }
    };
    mAdapter = new MyBookRecyclerViewAdapter(new ArrayList<Book>(), mListener);
    layoutManager = new LinearLayoutManager(context);
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(mAdapter);
    recyclerView.addOnScrollListener(recyclerViewOnScrollListener);
    swipeRefreshLayout = view.findViewById(R.id.swiperefresh);
    swipeRefreshLayout.setOnRefreshListener(
        new SwipeRefreshLayout.OnRefreshListener() {
          @Override
          public void onRefresh() {
            performSearch(null);
          }
        }
    );
    return view;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    isLoading = true;
    final ListBooksViewModel mModel = ViewModelProviders.of(this).get(ListBooksViewModel.class);
    observeViewModel(mModel);
  }


  private void observeViewModel(ListBooksViewModel mModel) {
    mModel.getProjectListObservable().observe(this, new Observer<List<Book>>() {
      @Override
      public void onChanged(@Nullable final List<Book> books) {
        getActivity().runOnUiThread(new Runnable() {
          @Override
          public void run() {
            isLoading = false;
            if (books != null) {
              mAdapter.setBookList(books);
            }
            updateProgressBar();
            swipeRefreshLayout.setRefreshing(false);

          }
        });

      }
    });

    mModel.getErrorObservable().observe(this, new Observer<Throwable>() {
      @Override
      public void onChanged(@Nullable Throwable throwable) {
        if (throwable != null) {
          getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
              isLoading = false;
              Snackbar.make(getView(), getString(R.string.error_retrieving_list_books),
                  Snackbar.LENGTH_LONG).show();
              updateProgressBar();
              swipeRefreshLayout.setRefreshing(false);
              mAdapter.setBookList(null);
            }
          });
        }
      }
    });
  }


  private void updateProgressBar() {
    if (progressBar != null) {
      progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof OnBookClickLister) {
      mListener = (OnBookClickLister) context;
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mListener = null;
  }

  /*
   * ScrollListener to calculate when we need to load the next books
   * would be nice to implement the new library Pagination, but lets keep easy
   *
   * */
  private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
      super.onScrollStateChanged(recyclerView, newState);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
      super.onScrolled(recyclerView, dx, dy);
      int visibleItemCount = layoutManager.getChildCount();
      int totalItemCount = layoutManager.getItemCount();
      int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

      if (!isLoading && !isLastPage) {
        if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
            && firstVisibleItemPosition > 0
            && totalItemCount >= PAGE_SIZE) {
          loadMoreItems();
        }
      }
    }
  };


  // region Helper Methods
  private void loadMoreItems() {
    isLoading = true;
    updateProgressBar();

    final ListBooksViewModel mModel = ViewModelProviders.of(this).get(ListBooksViewModel.class);

    mModel.loadMoreBooks();
  }

  public void performSearchQuery(Intent intent) {
    if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
      String query = intent.getStringExtra(SearchManager.QUERY);
      if (query == null || query.isEmpty()) {
        return;
      }
      performSearch(query);
    }
  }

  private void performSearch(String query) {
    isLoading = true;
    isLastPage = false;
    updateProgressBar();

    final ListBooksViewModel mModel = ViewModelProviders.of(this).get(ListBooksViewModel.class);

    mModel.startNewSearch(query);
  }

  public interface OnBookClickLister {

    void OnBookClick(Book item);
  }


  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.menu_main, menu);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
      SearchManager searchManager = (SearchManager) getActivity()
          .getSystemService(Context.SEARCH_SERVICE);
      SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
      searchView
          .setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
      searchView.setIconifiedByDefault(false);
    }
  }

}
