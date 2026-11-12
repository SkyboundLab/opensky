package xyz.kaydax.openely.shared;

import xyz.kaydax.openely.shared.Player.Texture.Properties;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import static xyz.kaydax.openely.shared.API.ElyAPI.getSkinByUUID;

public class OpenEly
{
  //This assumes that every single api's GameProfile properties are the same, which they should be...
  public static <T> List<T> modifySkin(String uuid, List<T> property, Consumer<String> logger) throws Exception
  {
    List<T> originalProperties = property == null ? new ArrayList<>() : new ArrayList<>(property);
    List<T> properties = new ArrayList<>(originalProperties);

    // Remove existing textures property
    properties.removeIf(p -> {
      if (p == null) {
        return false;
      }
      try
      {
        //For my own sanity, I'm checking for both getName and name methods
        try
        {
          Method method = p.getClass().getMethod("getName");
          String pName = (String) method.invoke(p);
          return "textures".equals(pName);
        } catch(NoSuchMethodException | IllegalAccessException | InvocationTargetException e)
        {
          Method method = p.getClass().getMethod("name");
          String pName = (String) method.invoke(p);
          return "textures".equals(pName);
        }
      } catch(NoSuchMethodException | IllegalAccessException | InvocationTargetException e)
      {
        return false;
      }
    });

    if(originalProperties.isEmpty())
    {
      logger.accept("[OpenEly] No existing profile properties for UUID " + uuid + "; skipping skin override");
      return properties;
    }

    Properties skin;
    try
    {
      skin = getSkinByUUID(uuid);
    } catch(Exception e)
    {
      logger.accept("[OpenEly] Failed to fetch skin for UUID " + uuid + ": " + e.getMessage());
      return originalProperties;
    }

    try
    {
      Class<?> clazz = originalProperties.get(0).getClass();
      Constructor<?> constructor = clazz.getConstructor(String.class, String.class, String.class);
      @SuppressWarnings("unchecked") T instance = (T) constructor.newInstance(skin.name, skin.value, skin.signature);
      properties.add(instance);
    } catch(Exception e)
    {
      logger.accept("[OpenEly] Failed to apply skin for UUID " + uuid + ": " + e.getMessage());
      return originalProperties;
    }

    return properties;
  }
}
