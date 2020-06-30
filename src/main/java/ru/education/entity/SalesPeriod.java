package ru.education.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sales_period")
@Getter
@Setter
@NoArgsConstructor
public class SalesPeriod {

    @org.springframework.data.annotation.Transient
    public static String TYPE_NAME = "Торговый период";

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy =  GenerationType.SEQUENCE, generator = "SALES_PERIOD_ID_SEQ")
    @SequenceGenerator(name = "SALES_PERIOD_ID_SEQ", sequenceName = "SALES_PERIOD_ID_SEQ", allocationSize = 1, initialValue = 9)
    private Long id;

    @Column(name = "price")
    private Long price;

    @Column(name = "date_from")
    private Date dateFrom;

    @Column(name = "date_to")
    private Date dateTo;

    @OneToOne
    @JoinColumn(name = "product", referencedColumnName ="id", nullable = false)
    private Product product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
