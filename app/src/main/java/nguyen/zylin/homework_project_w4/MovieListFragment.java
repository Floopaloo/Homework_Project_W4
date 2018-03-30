package nguyen.zylin.homework_project_w4;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import nguyen.zylin.homework_project_w4.Model.MovieModel;
import nguyen.zylin.homework_project_w4.Model.ResultModel;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MovieListFragment extends Fragment{

    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;
    private static String url;
    private SwipeRefreshLayout swipeContainer;


    Gson gson = new Gson();
    List<ResultModel> resultModelList = new ArrayList<>();

    RecyclerViewAdapter recyclerViewAdapter;
    RecyclerView recyclerView;

    public static MovieListFragment newInstance(int page) {

        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        MovieListFragment fragment = new MovieListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
        Log.e("Page", String.valueOf(mPage));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_now_playing, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), resultModelList);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // Lookup the swipe container view
        swipeContainer = view.findViewById(R.id.swipeContainer);

        if (mPage == 1) {
            url = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
        } else if (mPage == 2) {
            url = "https://api.themoviedb.org/3/movie/top_rated?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getData();


        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                getData();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    public void getData() {
        swipeContainer.setRefreshing(true);
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                swipeContainer.setRefreshing(false);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    Log.e("TAG", "Get data");
                    String data = response.body().string();

                    MovieModel movieModel = (MovieModel) gson.fromJson(data, MovieModel.class);
                    Log.e("DATA: ", data);
                    resultModelList.addAll(movieModel.getResults());

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            recyclerViewAdapter.notifyDataSetChanged();
                            swipeContainer.setRefreshing(false);
                        }
                    });
                }
            }
        });
    }

}
