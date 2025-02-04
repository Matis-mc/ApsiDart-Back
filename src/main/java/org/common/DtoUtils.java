package org.common;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.common.exceptions.UnparsableProperty;

public class DtoUtils {
    
    public static String extractStringProperty(String key, Map<String, Object> properties){
        return (String) Optional.ofNullable(properties.get(key)).orElseThrow(() -> new UnparsableProperty(key));
    }

    public static Integer extractIntProperty(String key, Map<String, Object> properties){
        return (Integer) Optional.ofNullable(properties.get(key)).orElseThrow(() -> new UnparsableProperty(key));
    }

    public static List<Integer> extractListProperty(String key, Map<String, Object> properties){
        return (List<Integer>) Optional.ofNullable(properties.get(key)).orElseThrow(() -> new UnparsableProperty(key));
    }
}
