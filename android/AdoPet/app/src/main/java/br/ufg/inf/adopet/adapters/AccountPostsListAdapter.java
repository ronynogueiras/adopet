package br.ufg.inf.adopet.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.ufg.inf.adopet.R;
import br.ufg.inf.adopet.holder.AccountPostViewHolder;
import br.ufg.inf.adopet.model.Account;
import br.ufg.inf.adopet.model.Post;

/**
 * Created by rony on 24/11/16.
 */

public class AccountPostsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Post> posts;
    private Context context;

    public AccountPostsListAdapter(Context context, List<Post> posts){
        this.posts = posts;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post_account,parent,false);

        return new AccountPostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        AccountPostViewHolder accountPostViewHolder = (AccountPostViewHolder) holder;
        Picasso.with(context).load(posts.get(position).getImage()).fit().centerCrop().into(accountPostViewHolder.getPicture());
        accountPostViewHolder.getTitle().setText(posts.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
