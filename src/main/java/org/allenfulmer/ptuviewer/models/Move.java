package org.allenfulmer.ptuviewer.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.allenfulmer.ptuviewer.dto.MoveDTO;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.StringUtils;

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
public class Move implements Displayable, Comparable<Move> {
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
    ContestType contestType;
    ContestEffect contestEffect;
    @Transient
    String critsOn;
    @JsonIgnore
    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "move")
    Set<LevelMove> levelMoves = new HashSet<>();
    @JsonIgnore
    @ToString.Exclude
    @OneToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER, mappedBy = "connection")
    Set<Ability> connections = new HashSet<>();
    @JsonIgnore
    @ToString.Exclude
//    @Transient
    @ManyToMany(mappedBy = "tmHmMoves", fetch = FetchType.LAZY)
    Set<PokemonSpecies> tmHmMoves = new HashSet<>();
    @JsonIgnore
    @ToString.Exclude
//    @Transient
    @ManyToMany(mappedBy = "tutorMoves", fetch = FetchType.LAZY)
    Set<PokemonSpecies> tutorMoves = new HashSet<>();
    @JsonIgnore
    @ToString.Exclude
//    @Transient
    @ManyToMany(mappedBy = "eggMoves", fetch = FetchType.LAZY)
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

    public Move(@NonNull String name, @NonNull Type type, @NonNull Frequency frequency, int uses, String ac, String db, @NonNull MoveClass moveClass, String range, String effect) {
        this.name = name;
        this.type = type;
        this.frequency = frequency;
        this.uses = uses;
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

    public boolean hasEffect() {
        return getEffect() != null && !getEffect().isBlank();
    }

    public String getFullFreq() {
        String ret = frequency.getDisplayName();
        if (getUses() > 0) {
            ret += " x" + this.uses;
        }
        return ret;
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

    @Override
    public String getDisplayName() {
        return name;
    }

    @Override
    public int compareTo(@NotNull Move o) {
        return getName().compareTo(o.getName());
    }

    // should prob consolidate them out of the converter if that's my convention
    public enum MoveClass implements Displayable {
        MOVE_CLASSES("Move Class"),
        PHYSICAL("Physical"), SPECIAL("Special"),
        STATUS("Status"), STATIC("Static");

        private final String displayName;

        private MoveClass(String displayName) {
            this.displayName = displayName;
        }

        public static MoveClass getWithName(String str) {
            return MoveClass.valueOf(str.toUpperCase());
        }

        @JsonValue
        public String getDisplayName() {
            return this.displayName;
        }
    }

    public enum ContestType implements Displayable {
        BEAUTY, COOL, CUTE, SMART, TOUGH;

        @JsonValue
        public String getDisplayName() {
            return StringUtils.capitalize(this.name().toLowerCase());
        }
    }

    @Getter
    public enum ContestEffect implements Displayable {
        ATTENTION_GRABBER("Attention Grabber"), BIG_SHOW("Big Show"), CATCHING_UP("Catching Up"),
        DESPERATION("Desperation"), DOUBLE_TIME("Double Time"), EXCITEMENT("Excitement"),
        EXHAUSTING_ACT("Exhausting Act"), GAMBLE("Gamble"), GET_READY("Get Ready!"),
        GOOD_SHOW("Good Show!"), INCENTIVES("Incentives"), INVERSED_APPEAL("Inversed Appeal"),
        REFLECTIVE_APPEAL("Reflective Appeal"), RELIABLE("Reliable"), SABOTAGE("Sabotage"),
        SAFE_OPTION("Safe Option"), SAVING_GRACE("Saving Grace"), SEEN_NOTHING_YET("Seen Nothing Yet"),
        SPECIAL_ATTENTION("Special Attention"), STEADY_PERFORMANCE("Steady Performance"),
        TEASE("Tease"), UNSETTLING("Unsettling");

        private static Map<String, ContestEffect> contestEffectMap = null;
        final String name;

        private ContestEffect(String name) {
            this.name = name;
        }

        public static ContestEffect getContestEffect(String name) {
            if (contestEffectMap == null) {
                contestEffectMap = Arrays.stream(values()).collect(Collectors.toMap(
                        ContestEffect::getName, Function.identity()));
            }
            return contestEffectMap.get(name);
        }

        @JsonValue
        public String getDisplayName() {
            return this.name;
        }
    }
}
