package quitto.FinaceSysthen.Enums;

import java.util.Arrays;

public enum Category {
    FOOD,
    TRANSPORT,
    HOUSING,
    HEALTH,
    EDUCATION,
    LEISURE,
    BILLS,
    OTHER;
    
    public static boolean exists(String value) {
        return Arrays.stream(values())
            .anyMatch(c -> c.name().equalsIgnoreCase(value));
    }
}
