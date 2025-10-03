package xyz.kaydax.openely.shared.Player;

import java.util.List;

public class Texture {
  public static class Properties
  {
    public String name;
    public String signature;
    public String value;

    public Properties(String name, String signature, String value) 
    {
      this.name = name;
      this.signature = signature;
      this.value = value;
    }
  }

  public String id;
  public String name;
  public List<Properties> properties;
}
