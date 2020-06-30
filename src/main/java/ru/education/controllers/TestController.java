package ru.education.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.education.jdbc.SalesPeriodJdbcDemo;
import ru.education.entity.SalesPeriod;
import ru.education.jdbc.SalesPeriodJdbcRepository;
import ru.education.entity.Product;
import ru.education.jpa.ProductRepository;
import ru.education.jpa.SalesPeriodRepository;
import ru.education.model.Formatter;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class TestController {

    private final Formatter formatter;
    private ProductRepository productRepository;
    private final SalesPeriodJdbcRepository salesProductJdbcRepository;
    private final SalesPeriodRepository salesPeriodRepository;

    @Autowired
    public TestController(@Qualifier("fooFormatter") Formatter formatter, ProductRepository productRepository, SalesPeriodJdbcRepository salesProductJdbcRepository, SalesPeriodRepository salesPeriodRepository) {
        this.formatter = formatter;
        this.productRepository = productRepository;
        this.salesProductJdbcRepository = salesProductJdbcRepository;
        this.salesPeriodRepository = salesPeriodRepository;
    }

    @GetMapping("/hello")
    public String getHello(){
        return  "Hello, World!";
    }

    @GetMapping("/format")
    public String getFormat(){
        return formatter.format();
    }

    @GetMapping("/products")
    public List<Product> getProduct(){
        return productRepository.findAll();
    }

    @GetMapping("/sales/count")
    public Integer getSalesCount(){
        return salesProductJdbcRepository.count();
    }

    @GetMapping("/sales")
    public List<SalesPeriodJdbcDemo> getSalesPeriod(){
        return salesProductJdbcRepository.getSalesPeriods();
    }

    @GetMapping("/sales/byhigherprice")
    public List<SalesPeriodJdbcDemo> getSalesPeriodsIsHigher(){
        return salesProductJdbcRepository.getSalesPeriodsIsHigher(90);
    }

    @GetMapping("/sales/active")
    public List<Product> getProductsWithActivePeriods(){
        return salesProductJdbcRepository.getProductsWithActivePeriods();
    }

    @GetMapping("/sales/jpa")
    public List<SalesPeriod> getSalesPeriodJpa(){
        return salesPeriodRepository.findAll();
    }

    @PostMapping("/sales/jpa")
    public SalesPeriod addSalesPeriodJpa(@RequestBody SalesPeriod salesPeriod){
        return salesPeriodRepository.save(salesPeriod);
    }

    @GetMapping("/sales/jpa/max/price")
    public Integer getMaxPriceByProduct(){
        return salesPeriodRepository.getMaxPriceByProduct(1);
    }

    @GetMapping("/sales/jpa/exists/price")
    public boolean existsByPrice(){
        return salesPeriodRepository.existsByPrice(60);
    }

    @GetMapping("/sales/jpa/exists/active")
    public List<SalesPeriod> findByDateIsNull() {
        return salesPeriodRepository.findByDateToIsNull();
    }

    @GetMapping("/sales/jpa/byproductname")
    public List<SalesPeriod> findByProductNam() {
        return salesPeriodRepository.findByProductName("bike");
    }

}
