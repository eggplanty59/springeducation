package ru.education.exceptions;

import org.springframework.util.Assert;

public class EntityAlreadeExistsException extends BaseException {

    /*Исключение выбрасывается при создании объекта с уже существующим ключом
     */
    public EntityAlreadeExistsException(String message){
        super(message);
    }

    public EntityAlreadeExistsException(String type, Object id){
        this(formatMessage(type, id));
    }

    private static String formatMessage(String type, Object id){
        Assert.hasText(type, "Тип не может быть пустым");
        Assert.notNull(id, "Идентификатор не может быть null");
        Assert.hasText(id.toString(), "Идентификатор не может быть пустым");
        return String.format("%s с ключом %s уже существует", type, id);
    }
}
