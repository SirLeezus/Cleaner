package lee.code.cleaner;

import lee.code.cleaner.lists.EntityPlaceLimit;
import lombok.Getter;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class Data {

    @Getter private final List<Material> entityPlaceLimitedItems = new ArrayList<>();

    public void load() {
        //limited entity items
        entityPlaceLimitedItems.addAll(EnumSet.allOf(EntityPlaceLimit.class).stream().map(EntityPlaceLimit::getMaterial).toList());
    }

}
