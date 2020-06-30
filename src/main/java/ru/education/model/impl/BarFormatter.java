package ru.education.model.impl;

import ru.education.model.Formatter;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component("barFormatter")
public class BarFormatter implements Formatter {
    @Override
    public String format() {
        return "Bar";
    }
}
