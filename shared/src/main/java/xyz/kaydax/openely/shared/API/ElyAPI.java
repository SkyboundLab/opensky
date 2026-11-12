package xyz.kaydax.openely.shared.API;

import com.google.gson.Gson;
import xyz.kaydax.openely.shared.Player.Texture;
import xyz.kaydax.openely.shared.Player.Texture.Properties;
import static xyz.kaydax.openely.shared.Util.RequestUtil.readUrl;

public class ElyAPI
{
  public static Properties getSkinByUUID(String uuid) throws Exception
  {
    String json = readUrl("https://skyskin.twint.pp.ua/textures/signed/" + uuid);
    if (json == null || json.isBlank()) {
      throw new Exception("Empty API response for UUID: " + uuid);
    }

    Gson gson = new Gson();
    Texture texture = gson.fromJson(json, Texture.class);

    if (texture == null || texture.properties == null) {
      throw new Exception("No texture data found in API response for UUID: " + uuid);
    }

    Properties texturesProperty = null;
    for (Properties prop : texture.properties) {
      if (prop != null && "textures".equals(prop.name)) {
        texturesProperty = prop;
        break;
      }
    }

    if (texturesProperty == null) {
      throw new Exception("No textures property found in API response for UUID: " + uuid);
    }

    return new Properties("textures", texturesProperty.signature, texturesProperty.value);
  }
}
