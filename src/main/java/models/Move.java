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
    public static final Map<String, Move> allMoves = Collections.unmodifiableMap(JsonRead.deserializeMoves());
    @NonNull
    String type;
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
