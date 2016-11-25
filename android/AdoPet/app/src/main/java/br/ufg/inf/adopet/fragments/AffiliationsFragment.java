package br.ufg.inf.adopet.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import br.ufg.inf.adopet.R;
import br.ufg.inf.adopet.adapters.PostFeedListAdapter;
import br.ufg.inf.adopet.model.Post;


/**
 * A simple {@link Fragment} subclass.
 */
public class AffiliationsFragment extends Fragment {

    private View mView;
    private RecyclerView mPostFeedList;
    private RecyclerView.Adapter mPostFeedListAdapter;
    private RecyclerView.LayoutManager mPostFeedLayoutManager;

    public AffiliationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        this.mView = inflater.inflate(R.layout.fragment_affiliations, container, false);

        this.setUp();

        return mView;
    }

    private void setUp(){

        mPostFeedList = (RecyclerView) mView.findViewById(R.id.recyclerview_list);
        mPostFeedList.setHasFixedSize(true);

        mPostFeedLayoutManager = new LinearLayoutManager(getActivity());
        mPostFeedList.setLayoutManager(mPostFeedLayoutManager);

        mPostFeedListAdapter = new PostFeedListAdapter(getActivity(),this.fakeData());
        mPostFeedList.setAdapter(mPostFeedListAdapter);

        mPostFeedListAdapter.notifyDataSetChanged();

    }

    private ArrayList<Post> fakeData(){

        ArrayList<Post> posts = new ArrayList<>();

        for (int i=0;i<10;i++){
            Post post = new Post(i, "Titulo "+i, "Author"+i, "Description" + i, new String[]{"https://unsplash.it/200/300/?random"}, "https://github.com/ronynogueiras/adopet", i);
            posts.add(post);
        }

        return posts;
    }

}
