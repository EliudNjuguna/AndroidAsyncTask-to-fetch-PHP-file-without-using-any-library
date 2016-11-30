package rhynix.eliud.androidasynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    TextView textPHP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textPHP = (TextView)findViewById(R.id.textPHP);

        new AsyncRetrive().execute();
    }

    private class AsyncRetrive extends AsyncTask<String,String,String> {

        ProgressDialog pdLoading = new ProgressDialog(MainActivity.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();
        }

        // This method does not interact with UI, You need to pass result to onPostExecute to display
        @Override
        protected String doInBackground(String... params) {
            try {
                url = new URL("http://192.168.137.183:8888/fetchphp/index.php");
            }catch (MalformedURLException e){
                e.printStackTrace();
                return e.toString();
            }
            try {
                // Setup HttpURLConnection class to send and receive data from php
                conn = (HttpURLConnection)url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("GET");

                // setDoOutput to true as we recieve data from json file
                conn.setDoOutput(true);

            }catch (IOException e1){
                e1.printStackTrace();
                return e1.toString();
            }

            try {
                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK){
                    //READ DATA SENT FROM THE SERVER

                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();

                    String line;
                    while ((line = reader.readLine())!=null){
                        result.append(line);
                    }
                    //Pass data to onPostExecute Method
                    return (result.toString());


                }else {
                    return ("Unsuccessful");
                }

            }catch (IOException e){
                e.printStackTrace();
                return e.toString();
            }finally {
                conn.disconnect();
            }
        }

        // this method will interact with UI, display result sent from doInBackground method
        @Override
        protected void onPostExecute(String result) {

            pdLoading.dismiss();
            if (result.equals("Hello your example is complete")){
                textPHP.setText(result.toString());
            }else {

                // a Toast understand error returned from doInBackground method

                Toast.makeText(MainActivity.this,result.toString(),Toast.LENGTH_LONG).show();

            }
        }
    }
}
