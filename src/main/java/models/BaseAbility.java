package models;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class BaseAbility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Ability.AbilityType abilityType;
    // OneToMany Pokemon->LevelMove, OneToMany move -> levelMove
    @ManyToOne
    Ability ability;
    @ManyToOne
    PokemonSpecies pokemon;
}
