package nguyen.zylin.homework_project_w4;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.List;

import nguyen.zylin.homework_project_w4.Model.ResultModel;

/**
 * Created by nmdli on 14-Mar-18.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private Context context;
    private List<ResultModel> resultModelList;

    public RecyclerViewAdapter(Context context, List<ResultModel> resultModelList) {
        this.context = context;
        this.resultModelList = resultModelList;
    }

    public Context getContext() {
        return context;
    }

//    public void setClickListener(ItemClickListener itemClickListener) {
//
//        this.clickListener = itemClickListener;
//    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View movieView = inflater.inflate(R.layout.recyclerview_layout, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(movieView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final ResultModel resultModel = resultModelList.get(position);

        Picasso.with(context).load("https://image.tmdb.org/t/p/w500/"+resultModel.getPosterPath()).into(holder.moviePoster);
        holder.movieName.setText(resultModel.getTitle());
        holder.movieDescription.setText(resultModel.getOverview());
        if (resultModel.getVideo() == true) {
            holder.btnPlay.setVisibility(View.VISIBLE);
        } else {
            holder.btnPlay.setVisibility(View.GONE);
        }

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("result", Parcels.wrap(resultModel));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return resultModelList == null ? 0 : resultModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView movieName, movieDescription;
        ImageView moviePoster, btnPlay;
        RelativeLayout container;

        public ViewHolder(View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.recycler_view_layout);
            movieName = itemView.findViewById(R.id.tv_movie_name);
            movieDescription = itemView.findViewById(R.id.tv_movie_description);
            moviePoster = itemView.findViewById(R.id.imgv_movie_poster);
            btnPlay = itemView.findViewById(R.id.play_btn);
        }

//        @Override
//        public void onClick(View v) {
//            clickListener.onItemClick(v,getAdapterPosition());
//        }
    }

    // Clean all elements of the recycler
    public void clear() {
        resultModelList.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<ResultModel> list) {
        resultModelList.addAll(list);
        notifyDataSetChanged();
    }
}
