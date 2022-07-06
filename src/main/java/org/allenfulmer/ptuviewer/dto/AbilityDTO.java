package org.allenfulmer.ptuviewer.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.allenfulmer.ptuviewer.models.ActionType;
import org.allenfulmer.ptuviewer.models.Frequency;
import org.allenfulmer.ptuviewer.models.Move;

@Setter
@Getter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AbilityDTO {
    String name;
    Frequency frequency;
    ActionType actionType;
    String trigger;
    String target;
    String effect;
    Move connection = null;
}
