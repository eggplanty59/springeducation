package ru.education.model.impl;

import ru.education.model.Formatter;
import org.springframework.stereotype.Component;

@Component("fooFormatter")
public class FooFormatter implements Formatter {
    @Override
    public String format() {
        return "Foo";
    }
}
