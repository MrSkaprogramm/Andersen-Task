package by.andersen.tr.bean;

import java.math.BigDecimal;

public enum StadiumSector {
    A(new BigDecimal("1000")),
    B(new BigDecimal("100")),
    C(new BigDecimal("10"));

    private BigDecimal price;

    StadiumSector(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
