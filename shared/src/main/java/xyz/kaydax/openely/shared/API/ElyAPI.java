package xyz.kaydax.openely.shared.API;

import com.google.gson.Gson;
import xyz.kaydax.openely.shared.Player.Texture;
import xyz.kaydax.openely.shared.Player.Texture.Properties;
import static xyz.kaydax.openely.shared.Util.RequestUtil.readUrl;

public class ElyAPI
{
  public static Properties getSkinByUUID(String uuid) throws Exception
  {
    String json = readUrl("https://skyskin.twint.my.id/textures/signed/" + uuid);
    Gson gson = new Gson();
    Texture texture = gson.fromJson(json, Texture.class);

    Properties texturesProperty = null;
    for (Properties prop : texture.properties) {
      if ("textures".equals(prop.name)) {
        texturesProperty = prop;
        break;
      }
    }
    
    if (texturesProperty == null) {
      throw new Exception("No textures property found in API response");
    }

    return new Properties("textures", texturesProperty.signature, texturesProperty.value);
  }
}
