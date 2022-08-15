package lee.code.cleaner;

import lee.code.cleaner.listeners.ChunkEntityListener;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class Cleaner extends JavaPlugin {

    @Getter private PU pU;
    @Getter private Data data;

    @Override
    public void onEnable() {
        this.pU = new PU();
        this.data = new Data();

        data.load();
        pU.scheduleEntityChunkCleaner();

        registerListeners();
    }

    @Override
    public void onDisable() {

    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new ChunkEntityListener(), this);
    }

    public static Cleaner getPlugin() {
        return Cleaner.getPlugin(Cleaner.class);
    }
}
