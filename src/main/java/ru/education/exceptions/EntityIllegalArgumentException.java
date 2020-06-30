package ru.education.exceptions;

public class EntityIllegalArgumentException extends BaseException{

    /*
     * Исключение пробрасыается при вызове метода сервиса с некорректными параметрами
     */
    public EntityIllegalArgumentException(String message) {
        super(message);
    }
}
