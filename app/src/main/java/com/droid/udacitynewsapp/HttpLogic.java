package com.droid.udacitynewsapp;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.util.List;
import java.util.Locale;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;


/**
 * Created by nickkrause on 5/10/17.
 */

public class HttpLogic {

    static String createStringUrl() {
        Uri.Builder uriBuildr = new Uri.Builder();
        uriBuildr.scheme("http")
                .encodedAuthority("content.guardianapis.com")
                .appendPath("search")
                .appendQueryParameter("order-by", "newest")
                .appendQueryParameter("show-references", "author")
                .appendQueryParameter("show-tags", "contributor")
                .appendQueryParameter("q", "Technology")
                .appendQueryParameter("api-key", "test");
        String url = uriBuildr.build().toString();
        return url;
    }

    static URL createUrl() {
        String strURL = createStringUrl();
        try {
            return new URL(strURL);
        } catch (MalformedURLException e) {
            return null;
        }
    }

    private static String formatDate(String rawDate) {
        SimpleDateFormat jsonFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
        try {
            Date formatJSONDate = jsonFormatter.parse(rawDate);
            String finalDatePattern = "MMM d, yyy";
            SimpleDateFormat finalDateFormatter = new SimpleDateFormat(finalDatePattern, Locale.US);
            return finalDateFormatter.format(formatJSONDate);
        } catch (ParseException e) {
            return "";
        }
    }

    static String makeHttpRequest(URL url) throws IOException {
        String httpResponse = "";

        if (url == null) {
            return httpResponse;
        }
        HttpURLConnection urlConnect = null;
        InputStream inputStream = null;

        try {
            urlConnect = (HttpURLConnection) url.openConnection();
            urlConnect.setConnectTimeout(20000);
            urlConnect.setRequestMethod("GET");
            urlConnect.setReadTimeout(18000);
            urlConnect.connect();
            if (urlConnect.getResponseCode() == 200) {
                inputStream = urlConnect.getInputStream();
                httpResponse = readFromStream(inputStream);
            } else {
            }
        } catch (IOException e) {
        } finally {
            if (urlConnect != null) {
                urlConnect.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return httpResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder resultString = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                resultString.append(line);
                line = reader.readLine();
            }
        }
        return resultString.toString();
    }

    static List<Story> parseJson(String response) {
        ArrayList<Story> listStories = new ArrayList<>();
        try {
            JSONObject jsonResponse = new JSONObject(response);
            JSONObject jsonResults = jsonResponse.getJSONObject("response");
            JSONArray resultsArray = jsonResults.getJSONArray("results");

            for (int i = 0; i < resultsArray.length(); i++) {
                JSONObject oneResult = resultsArray.getJSONObject(i);
                String webTitle = oneResult.getString("webTitle");
                String url = oneResult.getString("webUrl");
                String date = oneResult.getString("webPublicationDate");
                date = formatDate(date);
                String section = oneResult.getString("sectionName");
                JSONArray aTags = oneResult.getJSONArray("tags");
                String author = "";

                if (aTags.length() == 0) {
                    author = null;
                } else {
                    for (int j = 0; j < aTags.length(); j++) {
                        JSONObject firstObject = aTags.getJSONObject(j);
                        author += firstObject.getString("webTitle") + ". ";
                    }
                }
                listStories.add(new Story(webTitle, author, url, date, section));
            }
        } catch (JSONException e) {
        }
        return listStories;
    }
}
