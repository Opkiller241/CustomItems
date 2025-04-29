package itzkiller;

import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.instance.LightingChunk;
import net.minestom.server.instance.anvil.AnvilLoader;

public class Main{
    public static void main(String args[]){

        MinecraftServer ms=MinecraftServer.init();

        InstanceManager manager=MinecraftServer.getInstanceManager();
        InstanceContainer container=manager.createInstanceContainer();

        container.setChunkLoader(new AnvilLoader("resources/world/hub"));

        container.saveChunksToStorage();
        container.saveInstance();

        GlobalEventHandler globalEventHandler=MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addListener(AsyncPlayerConfigurationEvent.class, event -> {
            final Player player=event.getPlayer();
            event.setSpawningInstance(container);
            player.setRespawnPoint(new Pos(-3,70,-68));
        });

        //lighting
        container.setChunkSupplier(LightingChunk::new);

        //server starts
        ms.start("0.0.0.0",25565);
    }
}