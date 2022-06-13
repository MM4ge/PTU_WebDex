package models;

import com.google.gson.annotations.SerializedName;
import controllers.JsonRead;

import java.util.*;

public enum Type {
    //@SerializedName("normal")
    NORMAL, FIRE, WATER, ELECTRIC, GRASS, ICE, FIGHTING, POISON, GROUND, FLYING, PSYCHIC, BUG, ROCK, GHOST, DRAGON, DARK, STEEL, FAIRY, TYPELESS;
    private static Map<Type, Map<Type, Double>> effectivenessMap = Collections.unmodifiableMap(JsonRead.deserializeTypes());

    public static double getEffectiveness(Type attacker, Type defender)
    {
        return effectivenessMap.get(attacker).get(defender);
    }

    public static Map<Type, Map<Type, Double>> getMap() {
        return effectivenessMap;
    }
}

/*
static List<List<Float>>	typeAdvs		= new ArrayList<>();
	static Map<Integer, String> types = new HashMap<>();
	static List<Set<Integer>>	uniqueMovesets	= new ArrayList<>();

	public static void main(String[] args)
	{
		// 0, .5, 1, 2
		float s = 2; // Super
		float n = 1; // Normal
		float h = 1 / 2; // Half
		float z = 0; // Zero
		// 1, 2, 3, 4, 5, 6, 7, 8, 9,10,11,12,13,14,15,16,17,18
		typeAdvs.add(Arrays.asList(n, n, n, n, n, n, n, n, n, n, n, n, h, z, n, n, h, n)); // Norm
		typeAdvs.add(Arrays.asList(n, h, h, n, s, s, n, n, n, n, n, s, h, n, h, n, n, n)); // Fire
		typeAdvs.add(Arrays.asList(n, s, h, n, h, n, n, n, s, n, n, n, s, n, h, n, n, n)); // Water
		typeAdvs.add(Arrays.asList(n, n, s, h, h, n, n, n, z, s, n, n, n, n, h, n, n, n)); // Elec
		typeAdvs.add(Arrays.asList(n, h, s, n, h, n, n, h, s, h, n, h, s, n, h, n, h, n)); // Grass
		typeAdvs.add(Arrays.asList(n, h, h, n, s, h, n, n, s, s, n, n, n, n, s, n, h, n)); // Ice
		typeAdvs.add(Arrays.asList(s, n, n, n, n, s, n, h, n, h, h, h, s, n, n, s, s, h)); // Fight
		typeAdvs.add(Arrays.asList(n, n, n, n, s, n, n, h, h, n, n, n, h, h, n, n, z, s)); // Pois
		typeAdvs.add(Arrays.asList(n, s, n, s, h, n, n, s, n, z, n, h, s, n, n, n, s, n)); // Ground
		typeAdvs.add(Arrays.asList(n, n, n, h, s, n, s, n, n, n, n, s, h, n, n, n, h, n)); // Fly
		typeAdvs.add(Arrays.asList(n, n, n, n, n, n, s, s, n, n, h, n, n, n, n, z, h, n)); // Psy
		typeAdvs.add(Arrays.asList(n, h, n, n, s, n, h, h, n, h, s, n, n, h, n, s, h, h)); // Bug
		typeAdvs.add(Arrays.asList(n, s, n, n, n, s, h, n, h, s, n, s, n, n, n, n, h, n)); // Rock
		typeAdvs.add(Arrays.asList(z, n, n, n, n, n, n, n, n, n, s, n, n, s, n, h, n, n)); // Ghost
		typeAdvs.add(Arrays.asList(n, n, n, n, n, n, n, n, n, n, n, n, n, n, s, n, h, z)); // Drag
		typeAdvs.add(Arrays.asList(n, n, n, n, n, n, h, n, n, n, s, n, n, s, n, h, n, h)); // Dark
		typeAdvs.add(Arrays.asList(n, h, h, h, n, s, n, n, n, n, n, n, s, n, n, n, h, s)); // Steel
		typeAdvs.add(Arrays.asList(n, h, n, n, n, n, s, h, n, n, n, n, n, n, s, s, h, n)); // Fairy

		types.put(0, "Normal");
		types.put(1, "Fire");
		types.put(2, "Water");
		types.put(3, "Elec");
		types.put(4, "Grass");
		types.put(5, "Ice");
		types.put(6, "Fighting");
		types.put(7, "Poison");
		types.put(8, "Ground");
		types.put(9, "Flying");
		types.put(10, "Psychic");
		types.put(11, "Bug");
		types.put(12, "Rock");
		types.put(13, "Ghost");
		types.put(14, "Dragon");
		types.put(15, "Dark");
		types.put(16, "Steel");
		types.put(17, "Fairy");

		for (int firstType = 0; firstType < 18; firstType++)
			{
				for (int secondType = 0; secondType < 18; secondType++)
				{
					boolean unmatched = true;
					for (int attack : moveTypes)
					{
						float firstDef = typeAdvs.get(attack).get(firstType);
						float secondDef = typeAdvs.get(attack).get(secondType);
						float effective = firstDef * secondDef;
 */
