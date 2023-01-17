package se.nikoci.legions.structs;


import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@AllArgsConstructor(staticName = "of")
@RequiredArgsConstructor(staticName = "of")
@Getter
@SuppressWarnings("unused")
public class ParsablePair<T> {

    private final Map<Class<?>, Function<String, ?>> mapper = Map.of(
            String.class, str -> str,
            Boolean.class, Boolean::parseBoolean,
            Integer.class, Integer::parseInt,
            Double.class, Double::parseDouble,
            Float.class, Float::parseFloat
    );

    @NonNull private String key;
    @NonNull private T value;
    private boolean required = true;
    private boolean single = false;

    public ParsablePair<T> setRequired(boolean required){
        this.required = required;
        return this;
    }

    public ParsablePair<T> setSingle(boolean single) {
        this.single = single;
        return this;
    }

    @SneakyThrows
    public ParsablePair<T> parseSetSneaky(String victim){
        this.value = (T) mapper.get(value.getClass()).apply(victim);
        return this;
    }

    public ParsablePair<T> parseSet(String victim){
        this.value = (T) mapper.get(value.getClass()).apply(victim);
        return this;
    }

    @SneakyThrows
    public T parseSneaky(String victim){
        return (T) mapper.get(value.getClass()).apply(victim);
    }

    public T parse(String victim){
        return (T) mapper.get(value.getClass()).apply(victim);
    }

    //TODO check for single
    //requirements: must be on end of list (last arg), must be of type String
    public static List<ParsablePair<?>> checkList(List<ParsablePair<?>> list, String[] victims) {
        var newList = new ArrayList<ParsablePair<?>>();
        for (int i = 0; i < victims.length || i < list.size(); i++) {
            var cvp = list.get(i).parseSetSneaky(victims[i]);
            if (cvp == null) return null;
            newList.add(cvp);
        }
        return newList;
    }
}
