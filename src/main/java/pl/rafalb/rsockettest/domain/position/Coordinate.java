package pl.rafalb.rsockettest.domain.position;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Coordinate {
    private float x;
    private float y;
    private float z;
}
