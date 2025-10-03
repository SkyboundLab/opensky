package xyz.kaydax.openely;

import com.google.common.collect.Multimap;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.profile.property.ProfileProperty;

import java.util.ArrayList;
import java.util.List;

import static xyz.kaydax.openely.shared.OpenEly.modifySkin;

@Plugin(
        id = "openely", name = "openely", version = "1.0-SNAPSHOT", description = "A open source alterative to the Ely.by skin system plugin.", url = "https://git.kaydax.xyz/Kaydax/openely", authors = {"Kaydax"}
)
public class Main
{
  @Inject
  private Logger logger;

  @Listener
  public void onServerStart(GameStartedServerEvent event)
  {
    logger.info("Plugin loaded!");
  }

  @Listener
  public void onAuth(ClientConnectionEvent.Auth event) throws Exception
  {
    // Why the fuck does SpongeAPI7 use Maps instead of List for properties? It's just redundant data
    Multimap<String, ProfileProperty> propMap = event.getProfile().getPropertyMap();
    String name = event.getProfile().getName().orElseThrow(() -> new RuntimeException("Profile name is missing"));
    String uuid = event.getProfile().getUniqueId().toString();
    
    logger.info("[OpenEly] Processing profile for player: " + name + " (UUID: " + uuid + ")");
    logger.info("[OpenEly] Property map keys: " + propMap.keySet());
    logger.info("[OpenEly] Forwarding skin to custom API");
    
    List<ProfileProperty> properties = new ArrayList<>(propMap.values());
    
    propMap.removeAll("textures");
    propMap.put("textures", modifySkin(uuid, properties, logger::info).get(0));
    logger.info("[OpenEly] Skin processing complete for " + name);
  }
}