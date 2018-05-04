package com.demo.serverremote;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.serverremote.utils.Constants;
import com.demo.serverremote.utils.LoginCredencials;
import com.demo.serverremote.utils.LoginObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.InputStream;

import static com.demo.serverremote.utils.Token.token;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    ImageView img;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.imageView);
        img = findViewById(R.id.imageView2);
        checkLogin();

    }


    private void checkLogin() {
        if (LoginCredencials.username == "" || LoginCredencials.username == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    public void logOut(View v) {
        LoginCredencials.username = "";
        checkLogin();
    }

    public void lock(View v) {
        new HttpGetAsyncTask().execute(Constants.LOCK_URL);
    }

    public void sleep(View v) {
        new HttpGetAsyncTask().execute(Constants.SLEEP_URL);
    }

    public void shutDown(View v) {
        new HttpGetAsyncTask().execute(Constants.DOWN_URL);
    }

    public void screenShot(View v) {
        new HttpGetAsyncTask().execute(Constants.SCREEN_URL);
    }


    public String getFromServer(String url) {

        InputStream inputStream = null;
        String result = "";

        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            HttpGet httpGet = new HttpGet(url);

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(new LoginObject());
            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);

            // 7. Set some headers to inform server about the type of the content
            httpGet.setHeader("Accept", "application/json");
            httpGet.setHeader("Content-type", "application/json");
            httpGet.setHeader("Authorization", "Bearer " + token);

            // 8. Execute POST request to the given LOGIN_URL
            HttpResponse httpResponse = httpclient.execute(httpGet);

            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if (inputStream != null) {

                bitmap = BitmapFactory.decodeStream(inputStream);
                String dfsdf = "sdfsdsss";
            }

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return "Captured";
    }


    private class HttpGetAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return getFromServer(urls[0]);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            textView.setText(result);
            try {
                img.setImageBitmap(bitmap);

            } catch (Exception e) {
                Log.e("Error", e.getMessage());
            }
            Toast.makeText(getBaseContext(), result, Toast.LENGTH_LONG).show();
        }
    }


}
