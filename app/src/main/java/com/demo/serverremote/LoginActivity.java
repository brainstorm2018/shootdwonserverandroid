package com.demo.serverremote;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.demo.serverremote.utils.Constants;
import com.demo.serverremote.utils.LoginCredencials;
import com.demo.serverremote.utils.LoginObject;
import com.demo.serverremote.utils.Token;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

//import org.codehaus.jackson.map.ObjectMapper;

public class LoginActivity extends AppCompatActivity {
    private EditText mUsername, mPassword;
    public EditText ip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ip = findViewById(R.id.ip);
        ip.setText(Constants.URL);
        mUsername = (EditText) findViewById(R.id.usernameEdit);
        mPassword = (EditText) findViewById(R.id.passwordEdit);
    }

    public void joinChat(View view) {
        LoginCredencials.username = mUsername.getText().toString();
        LoginCredencials.password = mPassword.getText().toString();
//        post(Constants.LOGIN_URL);
        new HttpAsyncTask().execute(Constants.LOGIN_URL);


    }

    public void toMainPage() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    public String post(String url) {

        InputStream inputStream = null;
        String result = "";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            HttpPost httpPost = new HttpPost(url);

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(new LoginObject());
            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);

            // 6. set httpPost Entity
            httpPost.setEntity(se);
            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given LOGIN_URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if (inputStream != null) {

//                result = inputStream.toString();
                result = convertInputStreamToString(inputStream);
                String dfsdf = "sdfsd";
            }

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        // 11. return result
        Token.token = result;
        return result;
    }

    public void changeIp(View v){
        Constants.refactor(ip.getText().toString());
        Toast.makeText(getBaseContext(),"Ip is changed!",Toast.LENGTH_SHORT).show();
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    private static boolean isValid(String username) {
        return username.matches("^[a-zA-Z0-9_]+$");
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return post(urls[0]);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            if (result.contains("\"error\"")) {
                Toast.makeText(getBaseContext(), result, Toast.LENGTH_LONG).show();
            } else {
                Token.token=result;
                toMainPage();
            }
        }
    }

}
