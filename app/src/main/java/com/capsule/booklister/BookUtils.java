package com.capsule.booklister;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class BookUtils {

    private static final String googleBooksUrl = "https://www.googleapis.com/books/v1/volumes?q=";

    private static final String TAG = BookUtils.class.getSimpleName();;

    public static List<Book> fetchBooksByQuery(String searchQuery){
        String bookURL = googleBooksUrl + searchQuery;
        URL url = createUrl(bookURL);
        String jsonResponse = "";

        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return extractBooksFromJson(jsonResponse);
    }

    private static List<Book> extractBooksFromJson(String jsonResponse) {
        List<Book> books = new ArrayList<>();
        try {
            JSONObject baseJsonObject = new JSONObject(jsonResponse);
            JSONArray jsonArray = baseJsonObject.getJSONArray("items");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject bookJson = jsonArray.getJSONObject(i);
                JSONObject bookVolumeInfoJson = bookJson.getJSONObject("volumeInfo");
                String bookTitle = bookVolumeInfoJson.getString("title");
                JSONArray authors = bookVolumeInfoJson.getJSONArray("authors");
                String author = authors.getString(0);

                JSONObject bookImageJson = bookVolumeInfoJson.getJSONObject("imageLinks");
                String imageUrl = bookImageJson.getString("smallThumbnail");

                books.add(new Book(bookTitle, author, imageUrl));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return books;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(1000 /* milliseconds */);
            urlConnection.setConnectTimeout(1500 /* milliseconds */);
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readInputStream(inputStream);
            } else {
                Log.e(TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return jsonResponse;

    }

    private static String readInputStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();

        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static URL createUrl(String bookURL)  {
        URL url = null;
        try {
            url = new URL(bookURL);
        } catch (MalformedURLException e) {
            Log.e(TAG, "Malformed Url", e);
        }
        return url;
    }


}
