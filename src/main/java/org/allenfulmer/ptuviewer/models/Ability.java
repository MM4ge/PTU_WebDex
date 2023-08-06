package org.allenfulmer.ptuviewer.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.allenfulmer.ptuviewer.dto.AbilityDTO;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Ability implements Comparable<Ability>, Displayable {
    //TODO: Abilities that have uses become Moves- similarly to how I do [- Type, Status, Free, 1AP: When Burned] stuff
    @Id
    @NonNull
    String name;
    Frequency frequency;
    int uses = 0;
    ActionType actionType;
    @Transient
    ActionType.Priority priority = null;
    @Column(name = "abilityTrigger")
    String trigger = "";
    String target = "";
    @Column(length = 1023)
    String effect = "";
    @ManyToOne(fetch = FetchType.EAGER)//(cascade = CascadeType.ALL)
    @JoinColumn(name = "connection_move")
    Move connection = null;

    @OneToMany(mappedBy = "ability")//, fetch = FetchType.EAGER)
    Set<BaseAbility> baseAbilities = new HashSet<>();

    public Ability(@NonNull String name, @NonNull Frequency frequency, int uses, ActionType actionType, ActionType.Priority priority, String trigger, String target, @NonNull String effect, Move connection) {
        this.name = name;
        this.frequency = frequency;
        this.uses = uses;
        this.actionType = actionType;
        this.priority = priority;
        this.trigger = trigger;
        this.target = target;
        this.effect = effect;
        this.connection = connection;
    }

    public Ability(@NonNull String name, Frequency frequency, String effect) {
        this.name = name;
        this.frequency = frequency;
        this.effect = effect;
    }

    public Ability(AbilityDTO abilityDTO) {
        this.name = abilityDTO.getName();
        this.frequency = abilityDTO.getFrequency();
        this.actionType = abilityDTO.getActionType();
        this.trigger = abilityDTO.getTrigger();
        this.target = abilityDTO.getTarget();
        this.effect = abilityDTO.getEffect();
        this.connection = abilityDTO.getConnection();
    }

    public String getFullFreq() {
        StringBuilder ret = new StringBuilder(frequency.getDisplayName());
        if (getUses() > 1) {
            ret.append(" x");
            ret.append(getUses());
        }
        if (!Frequency.STATIC.equals(getFrequency())) {
            ret.append(" - ");
            ret.append(getActionType().getDisplayName());
        }
        return ret.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ability ability = (Ability) o;
        return getName().equals(ability.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public String toString() {
        return "Ability{" +
                "name='" + name + '\'' +
                ", frequency=" + frequency +
                ", uses=" + uses +
                ", actionType=" + actionType +
                ", priority=" + priority +
                ", connection='" + ((connection == null) ? null : connection.getName()) + '\'' +
                ", trigger='" + trigger + '\'' +
                ", target='" + target + '\'' +
                ", effect='" + effect + '\'' +
                '}';
    }

    @Override
    public int compareTo(@NotNull Ability o) {
        return getName().compareTo(o.getName());
    }

    @Override
    public String getDisplayName() {
        return name;
    }
}
