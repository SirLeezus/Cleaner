package lee.code.cleaner;

import lee.code.cleaner.lists.Setting;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class PU {
    public int countEntitiesInChunk(Chunk chunk, EntityType type) {
        int count = 0;
        for (Entity e : chunk.getEntities()) if (type.equals(e.getType()) && !e.isDead() && !(e instanceof Player)) count++;
        return count;
    }

    public void scheduleEntityChunkCleaner() {
        Cleaner plugin = Cleaner.getPlugin();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            if (!Bukkit.getOnlinePlayers().isEmpty()) {
                for (World world : Bukkit.getWorlds()) {
                    for (Chunk chunk : world.getLoadedChunks()) {
                        Bukkit.getScheduler().runTask(plugin, () -> {
                            for (Entity entity : chunk.getEntities()) {
                                if (!entity.getType().equals(EntityType.DROPPED_ITEM)) {
                                    if (entity.customName() == null && countEntitiesInChunk(chunk, entity.getType()) > Setting.MAX_ENTITY_PER_CHUNK.getValue()) entity.remove();
                                }
                            }
                        });
                    }
                }
            }
        }), 0L, 20L * 30);
    }
}
