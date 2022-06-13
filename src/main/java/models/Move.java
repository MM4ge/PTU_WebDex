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
    // TODO: Periods can't be allowed in move names, have a check for that somewhere
    public static final Map<String, Move> allMoves = Collections.unmodifiableMap(JsonRead.deserializeMoves());
    @NonNull
    Type type;
    @NonNull
    String freq;
    String ac;
    String db;
    @NonNull
    String damageClass;
    @NonNull
    String range;
    @NonNull
    String effect;
    @NonNull
    String contestType;
    @NonNull
    String contestEffect;
    String critsOn;
    transient Map<String, String> triggers;
}
