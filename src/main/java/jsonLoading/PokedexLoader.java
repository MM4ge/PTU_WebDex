package jsonLoading;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import jsonLoading.db.ability.AbilityPojo;
import jsonLoading.db.move.MovePojo;
import jsonLoading.db.pokemon.PokemonSpeciesPojo;
import models.Move;
import models.TypeAdapter;


public class PokedexLoader
{
	private static final String POKEDEX_FILEPATH = "src/main/resources/pokedex.json";
	private static final String MOVES_FILEPATH = "src/main/resources/moves.json";
	private static final String TYPES_FILEPATH = "src/main/resources/types.json";
	private static final String ABILITIES_FILEPATH = "src/main/resources/abilities.json";

	private static Gson gson = new GsonBuilder().setLenient().create();

	public static void main(String[] args)
	{
		Map<String, PokemonSpeciesPojo> pokes = parsePojoPokemon();
		pokes.forEach((key, val) -> System.out.println(key + ": " + val));
		Map<String, MovePojo> moves = parsePojoMoves();
		moves.forEach((key, val) -> System.out.println(key + ": " + val));
		Map<String, AbilityPojo> abilities = parsePojoAbilities();
		abilities.forEach((key, val) -> System.out.println(key + ": " + val));

		abilities.values().stream().map(AbilityPojo::getFreq).distinct().forEach(System.out::println);
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
