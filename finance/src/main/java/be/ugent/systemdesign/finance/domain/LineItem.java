package be.ugent.systemdesign.finance.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LineItem {
    private String name;
    private Integer cost;

    public LineItem(String name, Integer cost) {
        this.name = name;
        if (cost < 0) {
            throw new InvalidCostException();
        }
        this.cost = cost;
    }
}
