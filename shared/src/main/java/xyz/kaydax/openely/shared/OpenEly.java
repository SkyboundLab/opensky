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
    logger.accept("[DEBUG] Starting modifySkin for UUID: " + uuid);
    logger.accept("[DEBUG] Property list size: " + property.size());
    logger.accept("[DEBUG] Forwarding all skins to custom API");
    
    List<T> properties = new ArrayList<>(property);

    // Store the original property list to get the class type before removing
    List<T> originalProperties = new ArrayList<>(properties);
    
    // Remove existing textures property
    properties.removeIf(p -> {
      try
      {
        //For my own sanity, I'm checking for both getName and name methods
        try
        {
          Method method = p.getClass().getMethod("getName");
          String pName = (String) method.invoke(p);
          if(pName.equals("textures")) {
            logger.accept("[DEBUG] Removing existing textures property");
          }
          return pName.equals("textures");
        } catch(NoSuchMethodException | IllegalAccessException | InvocationTargetException e)
        {
          Method method = p.getClass().getMethod("name");
          String pName = (String) method.invoke(p);
          if(pName.equals("textures")) {
            logger.accept("[DEBUG] Removing existing textures property");
          }
          return pName.equals("textures");
        }
      } catch(NoSuchMethodException | IllegalAccessException | InvocationTargetException e)
      {
        return false;
      }
    });

    if(!originalProperties.isEmpty())
    {
      // Get the class of the generic type T from original properties
      Class<?> clazz = originalProperties.get(0).getClass();
      logger.accept("[DEBUG] Property class: " + clazz.getName());

      // Get the constructor that takes three String parameters
      Constructor<?> constructor = clazz.getConstructor(String.class, String.class, String.class);

      // Create a new instance of T
      logger.accept("[DEBUG] Fetching skin from API for UUID: " + uuid);
      Properties skin = getSkinByUUID(uuid);
      logger.accept("[DEBUG] Received skin data - name: " + skin.name + ", value length: " + (skin.value != null ? skin.value.length() : "null") + ", signature length: " + (skin.signature != null ? skin.signature.length() : "null"));

      @SuppressWarnings("unchecked") T instance = (T) constructor.newInstance(skin.name, skin.value, skin.signature);

      // Add the new instance to the properties list
      properties.add(instance);
      logger.accept("[DEBUG] Successfully added new textures property");
    }

    logger.accept("[DEBUG] Skin modification complete, returning " + properties.size() + " properties");
    return properties;
  }
}
