package lordsomen.android.com.jokeandroidlibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class JokeShowingActivity extends AppCompatActivity {

    public static final String JOKE_KEY = "joke";
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_showing);
        Intent intent = getIntent();
        mTextView = findViewById(R.id.joke_show_textView);
        if(intent.hasExtra(JOKE_KEY)){
            String joke = intent.getStringExtra(JOKE_KEY);
            mTextView.setText(joke);
        }
    }

}
