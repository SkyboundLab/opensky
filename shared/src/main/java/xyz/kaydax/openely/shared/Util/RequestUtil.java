package xyz.kaydax.openely.shared.Util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RequestUtil
{
  public static String readUrl(String urlString) throws Exception
  {
    BufferedReader reader = null;
    try
    {
      URL url = new URL(urlString);
      reader = new BufferedReader(new InputStreamReader(url.openStream()));
      StringBuilder buffer = new StringBuilder();
      int read;
      char[] chars = new char[1024];
      while((read = reader.read(chars)) != -1)
      {
        buffer.append(chars, 0, read);
      }

      return buffer.toString();
    } finally
    {
      if(reader != null)
        reader.close();
    }
  }
}
