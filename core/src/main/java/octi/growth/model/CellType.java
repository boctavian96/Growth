package octi.growth.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CellType {
    SMALL_CELL(15, 1),
    MEDIUM_CELL(25, 2),
    LARGE_CELL(35, 3);

    public final int size;
    public final int growth;
}
