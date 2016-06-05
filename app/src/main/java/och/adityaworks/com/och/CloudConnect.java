package och.adityaworks.com.och;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Aditya Pandey on 05-06-2016.
 */
public class CloudConnect {
    private static final String LOG_TAG = CloudConnect.class.getSimpleName();
    final static String TIMETABLE_BASE_URI =
            "http://192.168.43.181:8080";
    final static String API_PATH = "api";
    final static String DEVICE_PATH = "devices";

    public static String urlToString(URL url) throws IOException {

        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        try {

            // Create the request to server, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder buffer = new StringBuilder();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            return buffer.toString();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
    }

    public static URL getURL(String targtePath) {
        Uri builtUri = Uri.parse(TIMETABLE_BASE_URI).buildUpon()
                .appendPath(API_PATH)
                .appendPath(targtePath)
                .build();

        // Construct the URL for the private API at AdityaWorks at
        // http://collegetools.adityaworks.com/
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

}
