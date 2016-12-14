package br.ufg.inf.adopet.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.ufg.inf.adopet.R;
import br.ufg.inf.adopet.holder.PostFeedViewHolder;
import br.ufg.inf.adopet.model.Post;

/**
 * Created by rony on 24/11/16.
 */

public class PostFeedListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<Post> mPosts;
    private Context mContext;

    public PostFeedListAdapter(Context context, List<Post> posts){
        this.mContext = context;
        this.mPosts = posts;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_feed_card_item,parent,false);
        return new PostFeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Post currentPost = this.mPosts.get(position);
        PostFeedViewHolder currentViewHolder = (PostFeedViewHolder) holder;

        currentViewHolder.getTitle().setText(currentPost.getTitle());
        currentViewHolder.getAuthor().setText(currentPost.getAuthor());
        Picasso.with(mContext)
                .load(currentPost.getImage())
//                .error(R.mipmap.ic_default_post_image)
//                .placeholder(R.mipmap.ic_load_post_image)
                .into( currentViewHolder.getImage() );

    }

    @Override
    public int getItemCount() {
        return this.mPosts.size();
    }
}
