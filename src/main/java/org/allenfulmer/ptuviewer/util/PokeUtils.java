package org.allenfulmer.ptuviewer.util;

import org.allenfulmer.ptuviewer.models.PokeConstants;

import java.util.List;
import java.util.Objects;

public class PokeUtils {

    private PokeUtils() {
    }

    public static int convertAc(String dbStr) {
        return convertFieldStrToInt(dbStr, false);
    }

    public static int convertDb(String dbStr, boolean stab) {
        return convertFieldStrToInt(dbStr, stab);
    }

    public static int convertFieldStrToInt(String fieldStr, boolean stab) {
        int ret = 0;
        try {
            ret = Integer.parseInt(fieldStr);
            if (stab)
                ret += PokeConstants.STAB;
        } catch (NumberFormatException ignored) {
            // Ignored - 0 is default return if parsing fails
        }
        return ret;
    }

    public static String getNonEmpty(String content) {
        if (Objects.isNull(content) || content.isBlank())
            return PokeConstants.EMPTY_ATTRIBUTE_TEXT;
        return content;
    }

    public static String wrapHtml(String content, List<String> htmlTags) {
        if (Objects.isNull(content) || content.isBlank())
            return content;
        StringBuilder ret = new StringBuilder(content);
        for (String curr : htmlTags) {
            ret.insert(0, "<" + curr + ">");
            ret.append("</").append(curr).append(">");
        }
        return ret.toString();
    }
}
