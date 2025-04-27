package hexlet.code.app.util;

import org.openapitools.jackson.nullable.JsonNullable;

import java.util.function.Consumer;

public class JsonNullableUtils {

    public static <T> void setIfPresent(JsonNullable<T> jsonNullable, Consumer<T> setter) {
        if (jsonNullable != null && jsonNullable.isPresent()) {
            setter.accept(jsonNullable.get());
        }
    }
}