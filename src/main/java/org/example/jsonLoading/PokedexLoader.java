package org.example.jsonLoading;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.example.jsonLoading.db.ability.AbilityPojo;
import org.example.jsonLoading.db.move.MovePojo;
import org.example.jsonLoading.db.pokemon.PokemonSpeciesPojo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;


public class PokedexLoader
{
	private static final String POKEDEX_FILEPATH = "src/main/resources/static/json/pokedex.json";
	private static final String MOVES_FILEPATH = "src/main/resources/static/json/moves.json";
	private static final String TYPES_FILEPATH = "src/main/resources/static/json/types.json";
	private static final String ABILITIES_FILEPATH = "src/main/resources/static/json/abilities.json";

	private static Gson gson = new GsonBuilder().setLenient().create();

	public static void main(String[] args)
	{
//		Map<String, PokemonSpeciesPojo> pokes = parsePojoPokemon();
//		pokes.forEach((key, val) -> System.out.println(key + ": " + val));
//
//		Set<String> eggGroups = pokes.values().stream().filter(p -> {
//			ImperialHeight min = p.getHeight().getImperial().getMinimum();
//			ImperialHeight max = p.getHeight().getImperial().getMaximum();
//			return !(min.getFeet() == max.getFeet() && min.getInches() == max.getInches());
//		}).map(p -> p.getSpecies()).collect(Collectors.toCollection(TreeSet<String>::new));
//		eggGroups.forEach(System.out::println);

//		Set<String> skills = pokes.values().stream().flatMap(p -> p.getSkills().stream().map(Skill::getSkillName)).collect(Collectors.toSet());
//		skills.forEach(System.out::println);

//		Map<String, MovePojo> moves = parsePojoMoves();
//		moves.values().stream().map(m -> m.getEffect()).filter(Objects::nonNull).sorted((s1, s2) -> Integer.compare(s1.length(), s2.length()))
//				.forEach(e -> System.out.println(e.length() + " - " + e));
		Map<String, AbilityPojo> abilities = parsePojoAbilities();
		abilities.values().stream().map(m -> m.getEffect()).filter(Objects::nonNull).sorted((s1, s2) -> Integer.compare(s1.length(), s2.length()))
				.forEach(e -> System.out.println(e.length() + " - " + e));
//		Set<String> freqs = new TreeSet<>(abilities.values().stream().map(AbilityPojo::getFreq).distinct().collect(Collectors.toSet()));
//		freqs.forEach(System.out::println);

//		System.out.println("---------------");

//		moves.values().stream().map(MovePojo::getFreq).distinct().forEach(System.out::println);
//		System.out.println("---------------");
//		moves.entrySet().stream().filter(e -> e.getValue().getEffect() == null).map(Map.Entry::getKey).forEach(System.out::println);
//		System.out.println("---------------");
//		moves.values().stream().map(MovePojo::getDb).distinct().forEach(System.out::println);
//		System.out.println("---------------");
//		moves.values().stream().map(MovePojo::getRange).distinct().forEach(System.out::println);
//		System.out.println("---------------");
//		moves.values().stream().map(MovePojo::getContestType).distinct().forEach(System.out::println);
//		System.out.println("---------------");
//		moves.values().stream().map(MovePojo::getContestEffect).distinct().forEach(System.out::println);
		System.out.println();

	}

	public static Map<String, PokemonSpeciesPojo> parsePojoPokemon()
	{
		return gson.fromJson(readFromFile(POKEDEX_FILEPATH), new TypeToken<Map<String, PokemonSpeciesPojo>>() {}.getType());
	}

	public static Map<String, MovePojo> parsePojoMoves()
	{
		return gson.fromJson(readFromFile(MOVES_FILEPATH), new TypeToken<Map<String, MovePojo>>() {}.getType());
	}

	public static Map<String, AbilityPojo> parsePojoAbilities()
	{
		return gson.fromJson(readFromFile(ABILITIES_FILEPATH), new TypeToken<Map<String, AbilityPojo>>() {}.getType());
	}

	public static String readFromFile(String path)
	{
		try {
			return new String(Files.readAllBytes(Paths.get(path)));
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}
}
