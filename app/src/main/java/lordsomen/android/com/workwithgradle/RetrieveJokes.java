package lordsomen.android.com.workwithgradle;

import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.lordsomen.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

public class RetrieveJokes extends AsyncTask<Void, Void, String> {

    public static String LOG_TAG = RetrieveJokes.class.getSimpleName();
    private final Listener mListener;
    private static MyApi myApiService = null;

    /**
     * Interface definition for a callback to be invoked when reviews are loaded.
     */
   public interface Listener {
        void onJokeLoaded(String joke);
    }

    public RetrieveJokes(Listener listener) {
        mListener = listener;
    }

    @Override
    protected String doInBackground(Void... params) {
        if (myApiService == null) {
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?>
                                                       abstractGoogleClientRequest)
                                throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            myApiService = builder.build();
        }

        try {
            return myApiService.getJoke().execute().getData();
        } catch (IOException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            return "Failed To retrieve";
        }
    }

    @Override
    protected void onPostExecute(String joke) {
        mListener.onJokeLoaded(joke);
    }
}
