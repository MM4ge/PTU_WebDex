package models;

import controllers.JsonRead;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Ability {
    public static final Map<String, Ability> allAbilities = Collections.unmodifiableMap(JsonRead.deserializeAbilities());
    public enum AbilityType
    {
        BASE(0), ADVANCED(20), HIGH(40);
        public final int level;

        AbilityType(int level)
        {
            this.level = level;
        }

        public int getLevel()
        {
            return this.level;
        }

        public static AbilityType getAbilityType(String str)
        {
            if(str.equalsIgnoreCase("Base"))
                return AbilityType.BASE;
            else if(str.equalsIgnoreCase("Advanced"))
                return AbilityType.ADVANCED;
            else if(str.equalsIgnoreCase("High"))
                return AbilityType.HIGH;
            else return null;
        }
    }

    @NonNull
    String freq;
    String trigger = "";
    String target = "";
    @NonNull
    String effect;
    Move connection =  null;
}
