package lee.code.cleaner.listeners;

import com.destroystokyo.paper.event.entity.PreCreatureSpawnEvent;
import lee.code.cleaner.Cleaner;
import lee.code.cleaner.PU;
import lee.code.cleaner.lists.EntityPlaceLimit;
import lee.code.cleaner.lists.Lang;
import lee.code.cleaner.lists.Setting;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.inventory.ItemStack;

public class ChunkEntityListener implements Listener {

    @EventHandler
    public void onEntityPreSpawn(PreCreatureSpawnEvent e) {
        Cleaner plugin = Cleaner.getPlugin();
        Chunk chunk = e.getSpawnLocation().getChunk();
        if (!e.getReason().equals(CreatureSpawnEvent.SpawnReason.CUSTOM)) {
            if (plugin.getPU().countEntitiesInChunk(chunk, e.getType()) >= Setting.MAX_ENTITY_PER_CHUNK.getValue()) e.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntitySpawn(CreatureSpawnEvent e) {
        Cleaner plugin = Cleaner.getPlugin();
        Chunk chunk = e.getLocation().getChunk();
        Entity entity = e.getEntity();

        if (!e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.CUSTOM)) {
            if (plugin.getPU().countEntitiesInChunk(chunk, entity.getType()) >= Setting.MAX_ENTITY_PER_CHUNK.getValue()) e.setCancelled(true);
        }
    }

    @EventHandler
    public void onChunkUnloadEntity(ChunkUnloadEvent e) {
        Cleaner plugin = Cleaner.getPlugin();
        Chunk chunk = e.getChunk();

        for (Entity entity : chunk.getEntities()) {
            if (!(entity instanceof Item)) if (plugin.getPU().countEntitiesInChunk(chunk, entity.getType()) >= Setting.MAX_ENTITY_PER_CHUNK.getValue()) entity.remove();
        }
    }

    @EventHandler
    public void onEntityPlace(PlayerInteractEvent e) {
        Cleaner plugin = Cleaner.getPlugin();
        PU pu = plugin.getPU();
        if (e.hasBlock()) {
            ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
            Material type = item.getType();
            Chunk chunk = e.getPlayer().getChunk();
            Player player =  e.getPlayer();

            if (plugin.getData().getEntityPlaceLimitedItems().contains(type)) {
                if (pu.countEntitiesInChunk(chunk, EntityPlaceLimit.valueOf(type.name()).getEntityType()) >= Setting.MAX_ENTITY_PER_CHUNK.getValue()) {
                    e.setCancelled(true);
                    player.sendActionBar(Lang.ERROR_CHUNK_MAX_ENTITIES.getComponent(new String[] { String.valueOf(Setting.MAX_ENTITY_PER_CHUNK.getValue()) }));
                }
            }
        }
    }
}
