package lee.code.cleaner.lists;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Setting {
    MAX_ENTITY_PER_CHUNK(25),
    ;

    @Getter private final int value;
}
