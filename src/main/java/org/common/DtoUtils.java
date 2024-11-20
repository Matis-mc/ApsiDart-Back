package org.common;

import java.util.Map;
import java.util.Optional;

public class DtoUtils {
    
    // todo : definir une exception spéciale
    public static String extractStringProperty(String key, Map<String, Object> properties){
        return (String) Optional.ofNullable(properties.get(key)).orElse("");
    }
}
