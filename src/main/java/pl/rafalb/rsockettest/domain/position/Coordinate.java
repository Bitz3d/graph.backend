package pl.rafalb.rsockettest.domain.position;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Coordinate {
    private int x;
    private int y;
    private int z;
}
