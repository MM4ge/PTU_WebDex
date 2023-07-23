package org.allenfulmer.ptuviewer.util;

import org.allenfulmer.ptuviewer.models.PokeConstants;

import java.util.List;
import java.util.Objects;

public class PokeUtils {

    private PokeUtils() {}

    public static String wrapHtml(String content, List<String> htmlTags)
    {
        if(Objects.isNull(content) || content.isBlank())
            return content;
        StringBuilder ret = new StringBuilder(content);
        for(String curr : htmlTags)
        {
            ret.insert(0,"<" + curr + ">");
            ret.append("</").append(curr).append(">");
        }
        return ret.toString();
    }
}
