package ru.education.service.Impl;

import org.springframework.stereotype.Service;
import ru.education.annotation.AfterLoggable;
import ru.education.annotation.BeforeLoggable;
import ru.education.annotation.Loggable;
import ru.education.entity.Product;
import ru.education.entity.SalesPeriod;
import ru.education.exceptions.EntityAlreadeExistsException;
import ru.education.exceptions.EntityHasDetailsException;
import ru.education.exceptions.EntityIllegalArgumentException;
import ru.education.exceptions.EntityNotFoundException;
import ru.education.jpa.ProductRepository;
import ru.education.jpa.SalesPeriodRepository;
import ru.education.service.ProductService;

import java.util.List;

@Service
public class DefaultProductService implements ProductService {

    private final ProductRepository productRepository;

    private final SalesPeriodRepository salesPeriodRepository;

    public DefaultProductService(ProductRepository productRepository, SalesPeriodRepository salesPeriodRepository) {
        this.productRepository = productRepository;
        this.salesPeriodRepository = salesPeriodRepository;
    }

    @Loggable
    public List<Product> findAll(){
        return productRepository.findAll();
    }

    @Loggable
    public Product findById(Object id){
        Product product;
        if(id == null){
            throw new EntityIllegalArgumentException("Идентификатор объекта не может быть null");
        }
        Integer parsedId;
        try {
            parsedId = Integer.valueOf(id.toString());
        }catch (NumberFormatException ex){
            throw new EntityIllegalArgumentException(String.format("Не удалось преобразовать идентификатор к нужному типу, текст ошибки: %s", ex));
        }
        product = productRepository.findOne(parsedId);

        if(product == null){
            throw new EntityNotFoundException(Product.TYPE_NAME, parsedId);
        }
        return product;
    }

    public Product create(Product product){
        if (product == null) {
            throw new EntityIllegalArgumentException("Создаваемый объект не может быть null");
        }
        if(product.getId() == null){
            throw new EntityIllegalArgumentException("Идентификатор объекта не может быть null");
        }
        Product existedProduct = productRepository.findOne(product.getId());
        if(existedProduct != null){
            throw new EntityAlreadeExistsException(Product.TYPE_NAME, existedProduct.getId());
        }
        return productRepository.save(product);
    }

    @BeforeLoggable
    public Product update(Product product) {
        if (product == null) {
            throw new EntityIllegalArgumentException("Создаваемый объект не может быть null");
        }
        if(product.getId() == null){
            throw new EntityIllegalArgumentException("Идентификатор объекта не может быть null");
        }
        Product existedProduct = productRepository.findOne(product.getId());
        if(existedProduct == null){
            throw new EntityNotFoundException(Product.TYPE_NAME, product.getId());
        }
        return productRepository.save(product);
    }

    @AfterLoggable
    public void delete(Object id){
        Product product = findById(id);
        List<SalesPeriod> salesPeriods = salesPeriodRepository.findByProduct(product);
        if(salesPeriods.size() > 0){
            throw new EntityHasDetailsException(SalesPeriod.TYPE_NAME, product.getId());
        }
        productRepository.delete(product);
    }
}
