package org.allenfulmer.ptuviewer.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.allenfulmer.ptuviewer.dto.MoveDTO;

import javax.persistence.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "moves")
public class Move {
    @Id
    @NonNull
    String name;
    //@NonNull
    @Column(name = "moveType")
    Type type;
    //@NonNull
    Frequency frequency;
    @Transient
    int uses = 0;
    @Column(length = 63)
    String ac;
    @Column(length = 63)
    String db;
    //@NonNull
    MoveClass moveClass;
    @Column(name = "moveRange", length = 63)
    String range;
    @Column(length = 1023)
    String effect;
    @Transient
    ContestType contestType;
    @Transient
    ContestEffect contestEffect;
    @Transient
    String critsOn;
    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "move")
    Set<LevelMove> levelMoves = new HashSet<>();
    @ToString.Exclude
    @OneToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER, mappedBy = "connection")
    Set<Ability> connections = new HashSet<>();
    @ToString.Exclude
    @Transient
    //@ManyToMany(mappedBy = "tmHmMoves")
    Set<PokemonSpecies> tmHmMoves = new HashSet<>();
    @ToString.Exclude
    @Transient
    //@ManyToMany(mappedBy = "tutorMoves")
    Set<PokemonSpecies> tutorMoves = new HashSet<>();
    @ToString.Exclude
    @Transient
    //@ManyToMany(mappedBy = "eggMoves")
    Set<PokemonSpecies> eggMoves = new HashSet<>();
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
    public Move(@NonNull String name, @NonNull Type type, @NonNull Frequency frequency, String ac, String db, @NonNull MoveClass moveClass, String range, String effect) {
        this.name = name;
        this.type = type;
        this.frequency = frequency;
        this.ac = ac;
        this.db = db;
        this.moveClass = moveClass;
        this.range = range;
        this.effect = effect;
    }
    public Move(@NonNull String name, Type type, Frequency frequency, MoveClass moveClass) {
        this.name = name;
        this.type = type;
        this.frequency = frequency;
        this.moveClass = moveClass;
    }

    public Move(MoveDTO moveDTO) {
        this.name = moveDTO.getName();
        this.type = moveDTO.getType();
        this.frequency = moveDTO.getFrequency();
        this.ac = moveDTO.getAc();
        this.db = moveDTO.getDb();
        this.moveClass = moveDTO.getMoveClass();
        this.range = moveDTO.getRange();
        this.effect = moveDTO.getEffect();
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

    @Override
    public String toString() {
        return "Move{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", frequency=" + frequency +
                ", uses=" + uses +
                ", ac='" + ac + '\'' +
                ", db='" + db + '\'' +
                ", moveClass=" + moveClass +
                ", range='" + range + '\'' +
                ", effect='" + effect + '\'' +
                ", contestType=" + contestType +
                ", contestEffect=" + contestEffect +
                ", critsOn='" + critsOn + '\'' +
                '}';
    }

    public enum MoveClass {
        MOVE_CLASSES("Move Class"),
        PHYSICAL("Physical"), SPECIAL("Special"),
        STATUS("Status"), STATIC("Static");

        private final String displayName;

        private MoveClass(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return this.displayName;
        }
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

        private static Map<String, ContestEffect> contestEffectMap = null;
        String name;

        private ContestEffect(String name) {
            this.name = name;
        }

        public static ContestEffect getContestEffect(String name) {
            try {
                return ContestEffect.valueOf(name.toUpperCase());
            } catch (IllegalArgumentException ignored) { // Ignored
            } catch (NullPointerException e) {
                return null;
            }

            if (contestEffectMap == null) {
                contestEffectMap = Arrays.stream(values()).collect(Collectors.toMap(
                        ContestEffect::getName, Function.identity()));
            }
            return contestEffectMap.get(name);
        }

        public String getName() {
            return name;
        }
    }
}
