package br.ufg.inf.adopet.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import br.ufg.inf.adopet.R;

/**
 * Created by rony on 24/11/16.
 */

public class PostFeedViewHolder extends RecyclerView.ViewHolder {

    private ImageView image;
    private TextView title, author;

    public PostFeedViewHolder(View view) {
        super(view);

        this.image = (ImageView) view.findViewById(R.id.post_feed_image);
        this.title = (TextView) view.findViewById(R.id.post_feed_title);
        this.author = (TextView) view.findViewById(R.id.post_feed_author);

    }

    public ImageView getImage() {
        return image;
    }

    public TextView getTitle() {
        return title;
    }

    public TextView getAuthor() {
        return author;
    }
}
