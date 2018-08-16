package lordsomen.android.com.workwithgradle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import lordsomen.android.com.jokeandroidlibrary.JokeShowingActivity;
import lordsomen.android.com.jokeslib.JokeClass;

public class MainActivity extends AppCompatActivity {

    public static final String JOKE_KEY = "joke";
    private InterstitialAd mInterstitialAd;
    private String testUnitAd = "ca-app-pub-3940256099942544/1033173712";
    private String commercialUnitAd = "ca-app-pub-8322504610625626/2985790999";
    private ProgressBar mProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.main_button);
        mProgressBar = findViewById(R.id.progressBar);
        JokeClass jokeClass = new JokeClass();
        // add configurations
        MobileAds.initialize(this, "ca-app-pub-8322504610625626~6382218326");

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(testUnitAd);
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressBar.setVisibility(View.VISIBLE);
                new RetrieveJokes(new RetrieveJokes.Listener() {
                    @Override
                    public void onJokeLoaded(final String joke) {
                        showTheAd();

                        mInterstitialAd.setAdListener(new AdListener() {
                            @Override
                            public void onAdLoaded() {
                                // Code to be executed when an ad finishes loading.
                            }

                            @Override
                            public void onAdFailedToLoad(int errorCode) {
                                // Code to be executed when an ad request fails.
                                moveJokeToLib(joke);
                            }

                            @Override
                            public void onAdOpened() {
                                // Code to be executed when the ad is displayed.
                            }

                            @Override
                            public void onAdLeftApplication() {
                                // Code to be executed when the user has left the app.
                            }

                            @Override
                            public void onAdClosed() {
                                // Code to be executed when when the interstitial ad is closed.
                                moveJokeToLib(joke);
                            }
                        });
                    }
                }).execute();
            }
        });
    }

    private void showTheAd() {
        if (mInterstitialAd.isLoaded()) {
            mProgressBar.setVisibility(View.INVISIBLE);
            mInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }
    }


    private void moveJokeToLib(String joke) {
        Intent intent = new Intent(MainActivity.this, JokeShowingActivity.class);
        intent.putExtra(JOKE_KEY, joke);
        startActivity(intent);
    }

}
