package com.droid.udacitynewsapp;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import java.util.List;
import java.io.IOException;
import java.net.URL;


/**
 * Created by nickkrause on 5/10/17.
 */

public class StoryLoader extends AsyncTaskLoader<List<Story>> {

    public StoryLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public List<Story> loadInBackground() {
        List<Story> listOfStories = null;
        try {
            //URL stringURL = HttpLogic.createUrl();
            String jsonResponse = HttpLogic.makeHttpRequest(HttpLogic.createUrl());
            listOfStories = HttpLogic.parseJson(jsonResponse);
        } catch (IOException e) {
        }
        return listOfStories;
    }
}
