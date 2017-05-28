package com.droid.udacitynewsapp;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import java.util.List;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;



public class MainActivity
        extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Story>>, SwipeRefreshLayout.OnRefreshListener {
    private StoryAdapter adapter;
    private static int LOADER_ID = 0;
    SwipeRefreshLayout refreshSwipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refreshSwipe = (SwipeRefreshLayout) findViewById(R.id.refresh);
        refreshSwipe.setOnRefreshListener(this);
        refreshSwipe.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        ListView listViewAside = (ListView) findViewById(R.id.list_view_aside);
        adapter = new StoryAdapter(this);
        listViewAside.setAdapter(adapter);
        listViewAside.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Story story = adapter.getItem(i);
                String url = story.url;
                Intent intentView = new Intent(Intent.ACTION_VIEW);
                intentView.setData(Uri.parse(url));
                startActivity(intentView);
            }
        });
        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public Loader<List<Story>> onCreateLoader(int id, Bundle args) {
        return new StoryLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<Story>> loader, List<Story> data) {
        refreshSwipe.setRefreshing(false);
        if (data != null) {
            adapter.setNotifyOnChange(false);
            adapter.clear();
            adapter.setNotifyOnChange(true);
            adapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Story>> loader) {

    }

    @Override
    public void onRefresh() {
        getSupportLoaderManager().restartLoader(LOADER_ID, null, this);
    }
}
