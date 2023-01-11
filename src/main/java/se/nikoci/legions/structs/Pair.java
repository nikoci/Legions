package se.nikoci.legions.structs;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Pair<F, S> {
    F first;
    S second;
}
