package ru.education.jdbc;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class SalesPeriodJdbcDemo {
    public SalesPeriodJdbcDemo(long id, long price, Date date_from, Date date_to, Integer product) {
        this.id = id;
        this.price = price;
        this.date_from = date_from;
        this.date_to = date_to;
        this.product = product;
    }

    private long id;

    private long price;

    private Date date_from;

    private Date date_to;

    private Integer product;
}
