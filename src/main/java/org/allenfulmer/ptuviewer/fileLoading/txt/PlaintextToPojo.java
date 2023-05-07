package org.allenfulmer.ptuviewer.fileLoading.txt;

import org.allenfulmer.ptuviewer.fileLoading.PojoToDBConverter;
import org.allenfulmer.ptuviewer.fileLoading.pojo.ability.AbilityPojo;
import org.allenfulmer.ptuviewer.fileLoading.pojo.move.MovePojo;
import org.allenfulmer.ptuviewer.models.Frequency;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlaintextToPojo {

    private PlaintextToPojo() {
    }

    public static Map<String, MovePojo> parseMoves() {
        Map<String, MovePojo> moves = new HashMap<>();
        String str = TxtUtils.readAllFiles(TxtUtils.MOVES_DIRECTORY);
        str = str.replaceAll("Damage Base ([0-9]+):[^\n]*\n", "Damage Base: $1\n");

        MovePojo move = new MovePojo();
        String key = null;
        StringBuilder effect = new StringBuilder();

        Matcher m = Pattern.compile("^([^:]+):([^:]*$)", Pattern.MULTILINE).matcher(str);
        while (m.find()) {
            String lineType = m.group(1);
            String content = m.group(2).replaceAll("\\s+", " ").trim();

            switch (lineType) {
                case ("Move"):
                    if(effect.length() != 0)
                        move.setEffect(effect.toString());
                    effect.setLength(0);

                    moves.put(key, move);
                    move = new MovePojo();
                    key = content;
                    break;
                case ("Type"):
                    move.setType(content);
                    break;
                case ("Frequency"):
                    move.setFreq(content);
                    break;
                case ("AC"):
                    move.setAc(content);
                    break;
                case ("Damage Base"):
                    move.setDb(content);
                    break;
                case ("Class"):
                    move.setDamageClass(content);
                    break;
                case ("Range"):
                    move.setRange(content);
                    break;
                case ("Set-Up Effect"):
                    effect.insert(0, lineType + ": " + content);
                    break;
                case "Effect", "Note", "Special", "Resolution Effect":
                    if (effect.length() > 0) {
                        effect.append("\n");
                        effect.append(lineType);
                        effect.append(": ");
                    }
                    effect.append(content);
                    break;
                case ("Contest Type"):
                    move.setContestType(content);
                    break;
                case ("Contest Effect"):
                    move.setContestEffect(content);
                    break;
                default:
                    throw new TxtUtils.InvalidInputException("Invalid line received, did something get out of sync? ("
                            + lineType + "---" + content + ")");
            }
        }

        move.setEffect(effect.toString());
        moves.put(key, move);
        moves.remove(null);

        return moves;
    }

    public static Map<String, AbilityPojo> parseAbilities() {
        Map<String, AbilityPojo> abilities = new HashMap<>();
        String str = TxtUtils.readAllFiles(TxtUtils.ABILITIES_DIRECTORY);

        AbilityPojo ability = new AbilityPojo();
        String key = null;
        StringBuilder effect = new StringBuilder();

        // Add escape Freq: to Static and Scene x2 - Free Action type lines
        for (Frequency curr : Frequency.values())
            str = str.replaceAll("\n(" + curr.getDisplayName() + ")(\r\n| x| -| –)", "\nFrequency: $1$2");

        str = str.replaceAll("  +", " ").
                replace(" – ", PojoToDBConverter.ABILITY_DIVIDER).
                replace(" - ", PojoToDBConverter.ABILITY_DIVIDER).
                replaceAll("(\\d+)- ", "$1" + PojoToDBConverter.ABILITY_DIVIDER);

        Matcher m = Pattern.compile("^([^:]+):([^:]*$)", Pattern.MULTILINE).matcher(str);
        while (m.find()) {
            String lineType = m.group(1);
            String content = m.group(2).replaceAll("\\s+", " ").trim();

            switch (lineType) {
                case ("Ability"):
                    ability.setEffect(effect.toString());
                    effect.setLength(0);

                    abilities.put(key, ability);
                    ability = new AbilityPojo();
                    key = content;
                    break;
                case ("Frequency"):
                    ability.setFreq(content);
                    break;
                case ("Trigger"):
                    ability.setTrigger(content);
                    break;
                case ("Target"):
                    ability.setTarget(content);
                    break;
                case ("Special"):
                    effect.insert(0, lineType + ": " + content);
                    break;
                case "Effect", "Bonus":
                    if (effect.length() > 0) {
                        effect.append("\n");
                        effect.append(lineType);
                        effect.append(": ");
                    }
                    effect.append(content);
                    break;
                case "Note", "Replaces": // Meta tag, shouldn't be put into the ability
                    break;
                default:
                    throw new TxtUtils.InvalidInputException("Invalid line received, did something get out of sync? ("
                            + lineType + "---" + content + ")");
            }
        }

        ability.setEffect(effect.toString());
        abilities.put(key, ability);
        abilities.remove(null);

        return abilities;
    }

}
