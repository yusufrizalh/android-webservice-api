package id.inixindosurabaya.androidwebservice;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class HttpHandler {

    public String sendPostRequest(
            String requestUrl,
            HashMap<String, String> postDataParams) {
        // Membuat URL
        URL url;

        StringBuilder sb = new StringBuilder();
        try {
            url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection)
                    url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(10000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();

            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));
            writer.flush();
            writer.close();
            os.close();

            // cek http status code
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK){
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(conn.getInputStream())
                );
                sb = new StringBuilder();
                String response;
                while ((response = br.readLine()) != null) {
                    sb.append(response);
                }
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return sb.toString();
    }

    private String getPostDataString(
            HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }

    // method untuk handle send get all
    public String sendGetResponse(String responseUrl) {
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(responseUrl);
            HttpURLConnection conn = (HttpURLConnection)
                    url.openConnection();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream())
            );

            String s;
            while ((s = reader.readLine()) != null){
                sb.append(s + "\n");
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return sb.toString();
    }

    // method untuk handle send get detail
    public String sendGetResponse(String responseUrl,
                                  String id){
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(responseUrl + id);
            HttpURLConnection conn = (HttpURLConnection)
                    url.openConnection();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream())
            );

            String s;
            while ((s = reader.readLine()) != null){
                sb.append(s + "\n");
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return sb.toString();
    }
}
