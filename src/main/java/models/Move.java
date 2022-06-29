package models;

import controllers.JsonRead;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "moves")
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
    @Id
    @NonNull
    String name;
    @NonNull
    @Column(name = "moveType")
    Type type;
    @NonNull
    Frequency frequency;
    int uses = 0;
    String ac;
    String db;
    @NonNull
    MoveClass moveClass;
    @Column(name = "moveRange")
    String range;
    @Column(length = 1023)
    String effect;
    ContestType contestType;
    ContestEffect contestEffect;
    String critsOn;

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "move")
    Set<LevelMove> levelMoves;
    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "connection")
    //TODO: figure out cascades
    Set<Ability> connections;
    @ToString.Exclude
    @ManyToMany(mappedBy = "tmHmMoves")
    Set<PokemonSpecies> tmHmMoves;
    @ToString.Exclude
    @ManyToMany(mappedBy = "tutorMoves")
    Set<PokemonSpecies> tutorMoves;
    @ToString.Exclude
    @ManyToMany(mappedBy = "eggMoves")
    Set<PokemonSpecies> eggMoves;

    public Move(@NonNull String name, @NonNull Type type, @NonNull Frequency frequency, int uses, String ac, String db, @NonNull MoveClass moveClass, String range, String effect, ContestType contestType, ContestEffect contestEffect, String critsOn) {
        this.name = name;
        this.type = type;
        this.frequency = frequency;
        this.uses = uses;
        this.ac = ac;
        this.db = db;
        this.moveClass = moveClass;
        this.range = range;
        this.effect = effect;
        this.contestType = contestType;
        this.contestEffect = contestEffect;
        this.critsOn = critsOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return getName().equals(move.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
