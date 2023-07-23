package org.allenfulmer.ptuviewer.models;

import java.util.*;

public class PokeConstants {
    public static final int STAB = 2;
    public static final int MAX_MOVES = 6;
    public static final int HP_NATURE_CHANGE_VALUE = 1;
    public static final int NORMAL_NATURE_CHANGE_VALUE = 2;
    public static final int ADVANCED_ABILITY_LEVEL = 20;
    public static final int HIGH_ABILITY_LEVEL = 40;
    public static final int EVO_LEVEL_MOVE_VALUE = -1;
    public static final int NATURE_HP_CHANGE = 1;
    public static final int NATURE_OTHER_CHANGE = 2;
    public static final int EVASION_FROM_STATS_MAX = 6;
    public static final String NON_REGIONAL_FORM = "Standard";
    public static final String SEE_EFFECT_DB = "See Effect";
    public static final List<String> STAB_HTML_HIGHLIGHT = Collections.unmodifiableList((List.of("b")));
    public static final List<String> NATURE_HTML_RAISE = STAB_HTML_HIGHLIGHT;
    public static final List<String> NATURE_HTML_LOWER = Collections.unmodifiableList((List.of("u")));
    public static final String ROLL_20_DEFAULT_TYPE = "Ability";

    // TODO: Put a gson in here, use that in each object instead of replicating it

    public static final Random RANDOM_GEN = new Random();

    private PokeConstants() {
    }
}
