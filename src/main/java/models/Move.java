package models;

import controllers.JsonRead;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Collections;
import java.util.Map;

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
        ATTENTION_GRABBER, BIG_SHOW, CATCHING_UP, DESPERATION, DOUBLE_TIME, EXCITEMENT, EXHAUSTING_ACT, GAMBLE,
        GET_READY, GOOD_SHOW, INCENTIVES, INVERSED_APPEAL, REFLECTIVE_APPEAL, RELIABLE, SABOTAGE, SAFE_OPTION,
        SAVING_GRACE, SEEN_NOTHING_YET, SPECIAL_ATTENTION, STEADY_PERFORMANCE, TEASE, UNSETTLING;
    }
    // TODO: Periods can't be allowed in move names, have a check for that somewhere
    public static final Map<String, Move> allMoves = Collections.unmodifiableMap(JsonRead.deserializeMoves());
    @NonNull
    String name;
    @NonNull
    Type type;
    Frequency frequency;
    int uses = 0;
    String ac;
    String db;
    @NonNull
    MoveClass moveClass;
    @NonNull
    String range;
    String effect;
    ContestType contestType;
    ContestEffect contestEffect;
    String critsOn;

    public static Move getMove(String move)
    {
        return allMoves.get(move);
    }
}
