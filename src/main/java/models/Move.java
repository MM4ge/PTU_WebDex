package models;

import controllers.JsonRead;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Move {
    public enum MoveClass {
        PHYSICAL, SPECIAL, STATUS, STATIC;
    }
    public enum ContestType {
        BEAUTY, COOL, CUTE, SMART, TOUGH;
    }
    public enum ContestEffect {
        ATTENTION_GRABBER("Attention Grabber"), BIG_SHOW("Big Show"), CATCHING_UP("Catching Up"),
        DESPERATION("Desperation"), DOUBLE_TIME("Double Time"), EXCITEMENT("Excitement"),
        EXHAUSTING_ACT("Exhausting Act"), GAMBLE("Gamble"), GET_READY("Get Ready!"),
        GOOD_SHOW("Good Show!"), INCENTIVES("Incentives"), INVERSED_APPEAL("Inversed Appeal"),
        REFLECTIVE_APPEAL("Reflective Appeal"), RELIABLE("Reliable"), SABOTAGE("Sabotage"),
        SAFE_OPTION("Safe Option"), SAVING_GRACE("Saving Grace"), SEEN_NOTHING_YET("Seen Nothing Yet"),
        SPECIAL_ATTENTION("Special Attention"), STEADY_PERFORMANCE("Steady Performance"),
        TEASE("Tease"), UNSETTLING("Unsettling");

        String name;

        private static Map<String, ContestEffect> contestEffectMap = null;

        private ContestEffect(String name)
        {
            this.name = name;
        }

        public String getName()
        {
            return name;
        }

        public static ContestEffect getContestEffect(String name)
        {
            try {
                return ContestEffect.valueOf(name.toUpperCase());
            }
            catch (IllegalArgumentException ignored) {}
            catch (NullPointerException e) {return null;}

            if(contestEffectMap == null)
            {
                contestEffectMap = Arrays.stream(values()).collect(Collectors.toMap(
                        ContestEffect::getName, Function.identity()));
            }
            return contestEffectMap.get(name);
        }
    }

    // TODO: Periods can't be allowed in move names, have a check for that somewhere
    @NonNull
    String name;
    @NonNull
    Type type;
    Frequency frequency;
    int uses = 0;
    String ac;
    String db;
    MoveClass moveClass;
    String range;
    String effect;
    ContestType contestType;
    ContestEffect contestEffect;
    String critsOn;
}
