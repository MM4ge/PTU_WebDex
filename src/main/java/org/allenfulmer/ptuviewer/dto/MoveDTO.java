package org.allenfulmer.ptuviewer.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.allenfulmer.ptuviewer.models.Frequency;
import org.allenfulmer.ptuviewer.models.Move;
import org.allenfulmer.ptuviewer.models.Type;

@Setter
@Getter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MoveDTO {
    String name;
    Type type;
    Frequency frequency;
    String ac;
    String db;
    Move.MoveClass moveClass;
    String range;
    String effect;
}
