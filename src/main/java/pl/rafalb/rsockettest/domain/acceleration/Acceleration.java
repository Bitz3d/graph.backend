package pl.rafalb.rsockettest.domain.acceleration;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Acceleration {
    private int increaseX;
    private int increaseY;
    private int increaseZ;
}
