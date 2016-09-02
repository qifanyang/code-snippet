package utils;

import org.springframework.util.PropertyPlaceholderHelper;

import java.util.Properties;

/**
 * @author yangqf
 * @version 1.0 2016/8/3
 */
public class PropertyPlaceHoderTest{

    /** Prefix for system property placeholders: "${" */
    public static final String PLACEHOLDER_PREFIX = "${";

    /** Suffix for system property placeholders: "}" */
    public static final String PLACEHOLDER_SUFFIX = "}";

    /** Value separator for system property placeholders: ":" */
    public static final String VALUE_SEPARATOR = ":";


    private static final PropertyPlaceholderHelper nonStrictHelper =
            new PropertyPlaceholderHelper(PLACEHOLDER_PREFIX, PLACEHOLDER_SUFFIX, VALUE_SEPARATOR, true);

    public static void main(String[] args){
        String s = "hello ${x} ${ggg}";

        Properties properties = new Properties();
        properties.setProperty("x", "world");
        String s1 = nonStrictHelper.replacePlaceholders(s, properties);
        System.out.println(s1);
    }
}
