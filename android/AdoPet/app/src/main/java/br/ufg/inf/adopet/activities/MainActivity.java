package br.ufg.inf.adopet.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import br.ufg.inf.adopet.R;
import br.ufg.inf.adopet.adapters.PagerAdapter;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener{

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        toolbar.setNavigationIcon(R.mipmap.ic_launcher);

        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        this.setUpTabs();
        mTabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);

        mViewPager = (ViewPager) findViewById(R.id.pager);


        PagerAdapter pager = new PagerAdapter(getSupportFragmentManager(),mTabLayout.getTabCount(),false);
        mViewPager.setAdapter(pager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabLayout.getTabAt(position).select();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mTabLayout.addOnTabSelectedListener(this);

        setSupportActionBar(toolbar);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    private void setUpTabs(){

        String[] tabs = getResources().getStringArray(R.array.tabs);

        for (String tab : tabs){
            mTabLayout.addTab(mTabLayout.newTab().setText(tab));
        }

    }
}
