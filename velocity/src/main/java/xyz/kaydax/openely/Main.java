package xyz.kaydax.openely;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.GameProfileRequestEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.util.GameProfile;
import lombok.Getter;
import org.slf4j.Logger;
import static xyz.kaydax.openely.shared.OpenEly.modifySkin;

@Plugin(
        id = "openely", name = "openely", version = "1.0-SNAPSHOT", description = "A open source alterative to the Ely.by skin system plugin.", url = "https://git.kaydax.xyz/Kaydax/openely", authors = {"Kaydax"}
)
public class Main
{
  @Getter
  private Logger logger;

  @Getter
  private final ProxyServer proxy;

  @Inject
  public Main(ProxyServer proxy, Logger logger)
  {
    this.logger = logger;
    this.proxy = proxy;

    logger.info("Plugin loaded!");
  }

  @Subscribe
  public void onGameProfileRequestEvent(GameProfileRequestEvent event) throws Exception
  {
    logger.info("[OpenEly] Processing profile for player: " + event.getGameProfile().getName() + " (UUID: " + event.getGameProfile().getId() + ")");
    logger.info("[OpenEly] Initial properties count: " + event.getGameProfile().getProperties().size());
    
    event.setGameProfile(new GameProfile(event.getGameProfile().getId(), event.getGameProfile().getName(), modifySkin(event.getGameProfile().getId().toString(), event.getGameProfile().getProperties(), logger::info)));

    logger.info("[OpenEly] Skin processing complete for " + event.getGameProfile().getName());
  }
}
