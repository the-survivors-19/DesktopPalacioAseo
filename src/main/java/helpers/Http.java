package helpers;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Http {
    public static HttpURLConnection request(String path, String data, String method) throws Exception {
        method = method != null ? method : "GET";
        String ep = Constants.endPoint + path;
        URL url = new URL(ep);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.setRequestMethod(method);
        request.setRequestProperty("User-Agent", Constants.userAgent);
        request.setRequestProperty("Content-Type", Constants.contentTypeJson);
        request.setAllowUserInteraction(true);
        if (States.session != null) {
            request.setRequestProperty("Authorization", "Bearer " + States.session.token);
        }
        if (!data.equals("")) {
            request.setDoOutput(true);

            OutputStream os = request.getOutputStream();
            os.write(data.getBytes(StandardCharsets.UTF_8));
            os.flush();
            os.close();
        }
        return request;
    }

    public static String getResponse(HttpURLConnection request) throws Exception {
        InputStream data = request.getResponseCode() < 400 ? request.getInputStream() : request.getErrorStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(
                data));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

}
