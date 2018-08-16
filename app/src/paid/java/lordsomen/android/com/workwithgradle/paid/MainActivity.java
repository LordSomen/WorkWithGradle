package lordsomen.android.com.workwithgradle.paid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import lordsomen.android.com.jokeandroidlibrary.JokeShowingActivity;
import lordsomen.android.com.jokeslib.JokeClass;
import lordsomen.android.com.workwithgradle.R;
import lordsomen.android.com.workwithgradle.RetrieveJokes;

public class MainActivity extends AppCompatActivity {
    public static final String JOKE_KEY = "joke";
    private ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.main_button);
        JokeClass jokeClass = new JokeClass();
        mProgressBar = findViewById(R.id.progressBar);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressBar.setVisibility(View.VISIBLE);
                new RetrieveJokes(new RetrieveJokes.Listener() {
                    @Override
                    public void onJokeLoaded(final String joke) {
                        mProgressBar.setVisibility(View.INVISIBLE);
                        moveJokeToLib(joke);
                    }
                }).execute();
            }
        });
    }


    private void moveJokeToLib(String joke) {
        Intent intent = new Intent(MainActivity.this, JokeShowingActivity.class);
        intent.putExtra(JOKE_KEY, joke);
        startActivity(intent);
    }

}
