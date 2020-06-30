package ru.education.service.Impl;

import org.springframework.stereotype.Service;
import ru.education.entity.Product;
import ru.education.entity.SalesPeriod;
import ru.education.exceptions.*;
import ru.education.jpa.SalesPeriodRepository;
import ru.education.service.Impl.DefaultProductService;

import java.util.List;

@Service
public class SalesPeriodService {

    private final SalesPeriodRepository salesPeriodRepository;
    private final DefaultProductService defaultProductService;

    public SalesPeriodService(SalesPeriodRepository salesPeriodRepository, DefaultProductService defaultProductService) {
        this.salesPeriodRepository = salesPeriodRepository;
        this.defaultProductService = defaultProductService;
    }

    public List<SalesPeriod> findAll(){
        return salesPeriodRepository.findAll();
    }

    public SalesPeriod findById(Object id){
        SalesPeriod salesPeriod;
        if(id == null){
            throw new EntityIllegalArgumentException("Идентификатор объекта не может быть null");
        }
        Long parsedId;
        try {
            parsedId = Long.valueOf(id.toString());
        }catch (NumberFormatException ex){
            throw new EntityIllegalArgumentException(String.format("Не удалось преобразовать идентификатор к нужному типу, текст ошибки: %s", ex));
        }

        salesPeriod = salesPeriodRepository.findOne(parsedId);

        if(salesPeriod == null){
            throw new EntityNotFoundException(Product.TYPE_NAME, parsedId);
        }
        return salesPeriod;
    }

    public SalesPeriod create(SalesPeriod salesPeriod){
        if (salesPeriod == null) {
            throw new EntityIllegalArgumentException("Создаваемый объект не может быть null");
        }
        if(salesPeriod.getId() == null){
            throw new EntityIllegalArgumentException("Идентификатор объекта не может быть null");
        }
        if(salesPeriod.getProduct() == null){
            throw new EntityIllegalArgumentException("Продукт не может быть null");
        }
        if(salesPeriod.getProduct().getId() == null){
            throw new EntityIllegalArgumentException("Идентификатор продукта не может быть null");
        }
        if(salesPeriod.getDateFrom() == null){
            throw new EntityIllegalArgumentException("Дата начала не может быть null");
        }
        Product existedProduct = defaultProductService.findById(salesPeriod.getProduct().getId());//проверка на существование в продукта в базе. все проверки внутри метода

        SalesPeriod existedSalesPeriod = salesPeriodRepository.findOne(salesPeriod.getId());
        if(existedSalesPeriod != null){
            throw new EntityAlreadeExistsException(Product.TYPE_NAME, existedSalesPeriod.getId());
        }
        List<SalesPeriod> lastSalesPeriods = salesPeriodRepository.findByDateToIsNullAndProductId(salesPeriod.getProduct().getId());
        if(lastSalesPeriods.size() > 0){
            throw new EntityConflictException(String.format("В системе имеются открытые торговые периоды для продкукта с id %s", salesPeriod.getProduct().getId()));

        }
        return salesPeriodRepository.save(salesPeriod);
    }

    public void delete(Object id){
        SalesPeriod salesPeriod = findById(id);
        salesPeriodRepository.delete(salesPeriod);
    }

    public Long getNextValMySequence(){
        return salesPeriodRepository.getNextValMySequence();
    }
}
