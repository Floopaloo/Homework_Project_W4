package nguyen.zylin.homework_project_w4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;

import org.parceler.Parcels;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import nguyen.zylin.homework_project_w4.Model.ItemClickListener;
import nguyen.zylin.homework_project_w4.Model.MovieModel;
import nguyen.zylin.homework_project_w4.Model.ResultModel;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MovieListActivity extends AppCompatActivity implements ItemClickListener {

    Gson gson = new Gson();
    List<ResultModel> resultModelList = new ArrayList<>();

    RecyclerViewAdapter recyclerViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        final RecyclerView recyclerView = findViewById(R.id.recycler_view);

        OkHttpClient okHttpClient = new OkHttpClient();
        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
        final Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    Log.e("TAG", "Get data");
                    String data = response.body().string();

                    MovieModel movieModel = (MovieModel) gson.fromJson(data, MovieModel.class);
                    Log.e("DATA: ", data);
                    resultModelList.addAll(movieModel.getResults());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            recyclerViewAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });

        recyclerViewAdapter = new RecyclerViewAdapter(this, resultModelList);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerViewAdapter.setClickListener(this);
    }

    @Override
    public void onItemClick(View v, int position) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("result", Parcels.wrap(resultModelList.get(position)));
        startActivity(intent);
    }
}
