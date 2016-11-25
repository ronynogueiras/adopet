package br.ufg.inf.adopet.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import br.ufg.inf.adopet.fragments.AccountFragment;
import br.ufg.inf.adopet.fragments.AffiliationsFragment;
import br.ufg.inf.adopet.fragments.DefaultAccountFragment;
import br.ufg.inf.adopet.fragments.PostFeedFragment;

/**
 * Created by rony on 24/11/16.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    private int numberOfTabs;
    private boolean auth;

    public PagerAdapter(FragmentManager fm, int numberOfTabs, boolean auth) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
        this.auth = auth;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new PostFeedFragment();
            case 1:
                return new AffiliationsFragment();
            case 2:
                //Check for user login
                return auth ? new AccountFragment() : new DefaultAccountFragment();
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
