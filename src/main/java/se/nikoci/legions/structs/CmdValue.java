package se.nikoci.legions.structs;

import lombok.*;

import java.lang.constant.Constable;

@AllArgsConstructor(staticName = "of")
@RequiredArgsConstructor(staticName = "of")
@Getter
public class CmdValue<T extends Constable> {
    @NonNull private String identifier;
    @NonNull private T value;
    private boolean required = true;
}
