package com.capsule.booklister;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView3)
    RecyclerView recyclerView;

    @BindView(R.id.search_button)
    Button searchButton;

    @BindView(R.id.query_text)
    EditText queryText;

    private String searchQuery = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchQuery = queryText.getText().toString();
                if (searchQuery != null) {
                    BookAsyncTask asyncTask = new BookAsyncTask();
                    asyncTask.execute(searchQuery);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Enter search text", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void updateUI(List<Book> books) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        BookAdapter bookAdapter = new BookAdapter(books);
        recyclerView.setAdapter(bookAdapter);
    }

    private class BookAsyncTask extends AsyncTask<String, Void, List<Book>> {

        @Override
        protected List<Book> doInBackground(String... url) {
            return BookUtils.fetchBooksByQuery(url[0]);
        }

        @Override
        protected void onPostExecute(List<Book> books) {
            updateUI(books);
        }
    }
}
