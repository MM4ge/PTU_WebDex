package org.allenfulmer.ptuviewer.fileExport;

import lombok.extern.slf4j.Slf4j;
import org.allenfulmer.ptuviewer.fileLoading.PojoToDBConverter;
import org.allenfulmer.ptuviewer.models.Ability;
import org.allenfulmer.ptuviewer.models.Capability;
import org.allenfulmer.ptuviewer.models.Move;
import org.allenfulmer.ptuviewer.models.Type;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.allenfulmer.ptuviewer.fileLoading.PojoToDBConverter.getConvertedAbilitiesMap;
import static org.allenfulmer.ptuviewer.fileLoading.PojoToDBConverter.getConvertedMovesMap;

@Slf4j
public class DBToTextConverter {

    public static void main(String[] args) {
//        writeMoves("PokeMovesForPDF.txt");
//        writeAbilities("PokeAbilitiesForPDF.txt");
        writeCapabilities("PokeCapabilitiesForPDF.txt");
    }

    /* Move Examples:

    Move: Spider Web
    Type: Bug
    Frequency: Scene x2
    AC: None
    Class: Status
    Range: 5
    Effect: Spider Web cannot miss. The target is Stuck and Trapped. If the user is freed of the Stuck condition, they are freed of Trapped as well.
    Contest Type: Smart
    Contest Effect: Sabotage
    Special: Grants Threaded

    Move: Fly
    Type: Flying
    Frequency: At-Will
    AC: 3
    Damage Base 8: 2d8+10 / 19
    Class: Physical
    Range: Melee, Dash, Set-Up
    Set-Up Effect: The user is moved up 25 meters into the air.
    Resolution Effect: The user may shift twice while in the air, using their overland or sky speed, and then comes down next to a legal target, and attacks with Fly.
    Contest Type: Smart
    Contest Effect: Special Attention
    Special: Grants Sky +3
     */

    /*
    Type Order: Alphabetical
    Bug, Dark, Dragon, Electric, Fairy, Fighting, Fire, Fly, Ghost, Grass,
    Ground, Ice, Norm, Poison, Psy, Rock, Steel, Water

    Take out special types like Typeless (for Struggle / Weapon Moves) first
     */
    public static void writeMoves(String filename) {
        Map<Type, List<Move>> movesMap = getConvertedMovesMap().values().stream().collect(
                Collectors.groupingBy(Move::getType, TreeMap::new, Collectors.toCollection(ArrayList::new)));
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for(Map.Entry<Type, List<Move>> currEntry : movesMap.entrySet()) {
//                writer.append(currEntry.getKey().getDisplayName()).append(" Moves").append("\n\n");
                /*
                Move required fields for PDF:
                    Name
                    Type
                    Frequency
                    Class
                    Effect

                These two are required as well, but may not be tracked currently:
                    Contest Type
                    Contest Effect
                 */
                for(Move currMove : currEntry.getValue()) {
                    writer.append("Move: ").append(currMove.getName()).append("\n");
                    writer.append("Type: ").append(currMove.getType().getDisplayName()).append("\n");
                    writer.append("Frequency: ").append(currMove.getFullFreq()).append("\n");
                    if(currMove.getAc() != null && !currMove.getAc().equalsIgnoreCase("0"))
                        writer.append("AC: ").append(currMove.getAc()).append("\n");
                    if(currMove.getDb() != null)
                        writer.append("DB: ").append(currMove.getDb()).append("\n");
                    writer.append("Class: ").append(currMove.getMoveClass().getDisplayName()).append("\n");
                    if(currMove.getRange() != null)
                        writer.append("Range: ").append(currMove.getRange()).append("\n");
                    if(!currMove.isSetupMove())
                        writer.append("Effect: ");
                    if(currMove.getEffect() == null)
                        writer.append("None").append("\n");
                    else
                        writer.append(currMove.getEffect()).append("\n");

                    if(currMove.getContestType() != null) {
                        writer.append("Contest Type: ").append(currMove.getContestType().getDisplayName()).append("\n");
                        if(currMove.getContestEffect() == null)
                            log.info(currMove.getName());
                        writer.append("Contest Effect: ").append(currMove.getContestEffect().getDisplayName()).append("\n");
                    }
                    writer.append("\n");
                }
                writer.append((char) 12);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    Ability Examples:

    Ability: Full Guard
    Scene - Swift Action
    Trigger:You take Damage while having Temporary
    Hit Points
    Effect: You resist the triggering Damage one step further.
    Bonus: Whenever you take Super-Effective Damage and you have no Temporary Hit Points, you gain a tick of Temporary Hit Points. Defensive.

    Ability: Healer
    Scene – Free Action
    Target: An Adjacent Pokémon or Trainer
    Effect: The target is cured of all Status conditions.

    Ability: Power Construct
    Daily - Swift Action
    Effect: Special: The user still uses the HP total and HP Maximum of the Forme that it was in (10% or 50% Forme) before entering Complete Forme. Both Formes must still follow BSR.Special: The user can only use Power Construct while below 50% HP
    Effect: The user changes to Complete Forme until the end of the Scene, and gains Temporary Hit Points equal to half of the maximum hit points that Complete Forme would have. The user cannot gain Temporary Hit Points from other sources while in Complete Forme.
     */
    public static void writeAbilities(String filename) {
        Map<String, Ability> abilityMap = getConvertedAbilitiesMap();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for(Ability currAbility : abilityMap.values()) {
                writer.append("@Ability: ").append(currAbility.getDisplayName()).append("@\n");
                writer.append(currAbility.getFullFreq()).append("\n");
                if(currAbility.getTarget() != null && !currAbility.getTarget().isBlank())
                    writer.append("Target: ").append(currAbility.getTarget()).append("\n");
                if(currAbility.getTrigger() != null && !currAbility.getTrigger().isBlank())
                    writer.append("Trigger: ").append(currAbility.getTrigger()).append("\n");
                if(!currAbility.getEffect().startsWith("Special:"))
                    writer.append("Effect: ");
                writer.append(currAbility.getEffect()).append("\n");
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeCapabilities(String filename) {
        Map<String, Capability> capabilityMap = PojoToDBConverter.getConvertedCapabilities();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            Set<Capability> sortedCaps = new TreeSet<>(capabilityMap.values());
            for(Capability currCap : sortedCaps) {
                writer.append("@").append(currCap.getName()).append("@: ");
                writer.append(currCap.getDescription()).append("\n");
                writer.append("\n");
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
