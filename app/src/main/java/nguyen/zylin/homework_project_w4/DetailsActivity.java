package nguyen.zylin.homework_project_w4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import nguyen.zylin.homework_project_w4.Model.ResultModel;

public class DetailsActivity extends AppCompatActivity {
    ResultModel resultModel;
    ImageView moviePoster;
    TextView title, releaseDate, overview;
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        moviePoster = findViewById(R.id.imgv_movie_review);
        title = findViewById(R.id.tv_movie_title);
        releaseDate = findViewById(R.id.tv_release_date);
        overview = findViewById(R.id.tv_overview);
        ratingBar = findViewById(R.id.ratingBar);

        Intent intent = getIntent();
        if (intent != null) {
            resultModel = (ResultModel) Parcels.unwrap(intent.getParcelableExtra("result"));
            Picasso.with(this).load("https://image.tmdb.org/t/p/w500/"+ resultModel.getBackdropPath()).into(moviePoster);
            title.setText(resultModel.getTitle());
            releaseDate.setText("Release Date: " + resultModel.getReleaseDate());
            overview.setText(resultModel.getOverview());
            ratingBar.setRating(resultModel.getVoteAverage()/2);
        }
    }
}
