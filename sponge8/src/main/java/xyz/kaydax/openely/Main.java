package xyz.kaydax.openely;

import com.google.inject.Inject;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import org.apache.logging.log4j.Logger;
import org.spongepowered.api.Server;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.lifecycle.StartedEngineEvent;
import org.spongepowered.api.event.network.ServerSideConnectionEvent;
import org.spongepowered.plugin.builtin.jvm.Plugin;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static xyz.kaydax.openely.shared.OpenEly.modifySkin;

@Plugin("openely")
public class Main
{

  @Inject
  private Logger logger;

  @Listener
  public void onServerStart(final StartedEngineEvent<Server> event)
  {
    logger.info("Plugin loaded!");
  }

  @Listener
  public void onAuth(ServerSideConnectionEvent.Auth event) throws Exception
  {
    /**
     * All of this is fucking stupid. SpongeAPI 8 for some reason has no possible way of modifying a users GameProfile directly,
     * and you can't find any documentation on how to actually do this properly. So this is just code taken from SkinRestorer, but modified
     * to not use their ReflectionUtil code.
     */
    try
    {
      Field gameProfileField = null;
      for(Field field : event.connection().getClass().getDeclaredFields())
      {
        if(field.getType().equals(GameProfile.class))
        {
          gameProfileField = field;
          break;
        }
      }

      if(gameProfileField == null)
      {
        throw new RuntimeException("GameProfile field not found");
      }

      gameProfileField.setAccessible(true);
      GameProfile gameProfile = (GameProfile) gameProfileField.get(event.connection());

      logger.info("[OpenEly] Processing profile for player: " + gameProfile.getName() + " (UUID: " + gameProfile.getId() + ")");
      
      PropertyMap propMap = gameProfile.getProperties();
      logger.info("[OpenEly] Property map keys: " + propMap.keySet());
      logger.info("[OpenEly] Forwarding skin to custom API");
      
      List<Property> properties = new ArrayList<>(propMap.values());

      gameProfile.getProperties().removeAll("textures");
      gameProfile.getProperties().put("textures", modifySkin(gameProfile.getId().toString(), properties, logger::info).get(0));
      logger.info("[OpenEly] Skin processing complete for " + gameProfile.getName());
    } catch(ReflectiveOperationException e)
    {
      throw new RuntimeException(e);
    }
  }
}