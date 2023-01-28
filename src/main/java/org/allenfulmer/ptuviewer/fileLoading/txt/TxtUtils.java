package org.allenfulmer.ptuviewer.fileLoading.txt;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
public class TxtUtils {

    public static final String POKEMON_DIRECTORY = "src/main/resources/static/txt/pokemon";
    public static final String DEX_ID_DIRECTORY = "src/main/resources/static/txt/dexIds";
    protected static final List<String> genFiles = Collections.unmodifiableList(Arrays.asList(
            "alola.txt", "galar.txt", "hisui.txt"));
    protected static final String ABILITIES_DIRECTORY = "src/main/resources/static/txt/abilities";
    protected static final String MOVES_DIRECTORY = "src/main/resources/static/txt/moves";

    private TxtUtils() {
    }

    protected static String readAllFiles(String directory) {
        StringBuilder ret = new StringBuilder();
        for (String currFilename : genFiles) {
            try {
                String file = new String(Files.readAllBytes(Paths.get(directory, "/", currFilename)));
                ret.append(file);
                ret.append("\r\n");
            } catch (IOException ex) {
                log.error("File " + currFilename + " not found in directory " + directory + "! Skipping file.");
                log.error(ex.toString());
            }
        }
        return ret.toString();
    }

    protected static class InvalidInputException extends RuntimeException {
        public InvalidInputException() {
        }

        public InvalidInputException(String message) {
            super(message);
        }

        public InvalidInputException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
