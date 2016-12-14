package br.ufg.inf.adopet.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import br.ufg.inf.adopet.R;

/**
 * Created by rony on 24/11/16.
 */

public class AccountPostViewHolder extends RecyclerView.ViewHolder {

    private ImageView picture;
    private TextView title;

    public AccountPostViewHolder(View itemView) {
        super(itemView);
        this.picture = (ImageView) itemView.findViewById(R.id.post_account_image);
        this.title = (TextView) itemView.findViewById(R.id.post_account_title);
    }

    public ImageView getPicture() {
        return picture;
    }

    public TextView getTitle() {
        return title;
    }
}
