package org.allenfulmer.ptuviewer.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Capability {

    public static final List<String> MOVEMENT_CAPABILITIES = List.of("Sky", "Levitate", "Burrow", "Teleporter");
    @Id
    String name;
    @Column(length = 2047)
    String description;

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "capability")
    Set<BaseCapability> baseCapabilities = new HashSet<>();

    public Capability(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Capability{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Capability that = (Capability) o;
        return getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
