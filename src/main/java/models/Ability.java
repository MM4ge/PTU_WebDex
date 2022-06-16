package models;

import com.google.gson.annotations.SerializedName;
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
    public enum AbilityType
    {
        BASIC(0), ADVANCED(20), HIGH(40);

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
                return AbilityType.BASIC;
            else if(str.equalsIgnoreCase("Advanced"))
                return AbilityType.ADVANCED;
            else if(str.equalsIgnoreCase("High"))
                return AbilityType.HIGH;
            else
                return null;
        }
    }

    String name;
    @NonNull
    Frequency frequency;
    int uses = 0;
    ActionType actionType;
    ActionType.Priority priority = null;
    String trigger = "";
    String target = "";
    @NonNull
    String effect;
    Move connection =  null;
}
