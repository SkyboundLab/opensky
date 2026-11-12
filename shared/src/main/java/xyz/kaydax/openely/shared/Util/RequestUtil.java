package xyz.kaydax.openely.shared.Util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class RequestUtil
{
  public static String readUrl(String urlString) throws Exception
  {
    HttpURLConnection connection = (HttpURLConnection) new URL(urlString).openConnection();
    connection.setRequestMethod("GET");
    connection.setConnectTimeout(5000);
    connection.setReadTimeout(5000);
    connection.setRequestProperty("Accept", "application/json");

    int status = connection.getResponseCode();
    InputStream stream = (status >= 200 && status < 300) ? connection.getInputStream() : connection.getErrorStream();
    if (stream == null) {
      throw new Exception("HTTP " + status + " with empty response from " + urlString);
    }

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
      StringBuilder buffer = new StringBuilder();
      int read;
      char[] chars = new char[1024];
      while((read = reader.read(chars)) != -1)
      {
        buffer.append(chars, 0, read);
      }

      if (status < 200 || status >= 300) {
        throw new Exception("HTTP " + status + " from " + urlString + ": " + buffer);
      }

      return buffer.toString();
    } finally {
      connection.disconnect();
    }
  }
}
