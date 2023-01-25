package ru.otus.processor;

import ru.otus.model.Message;

import java.time.LocalDateTime;

public class ThrowEvenSecProcessor implements Processor{
    @Override
    public Message process(Message message) {
        LocalDateTime localDate = LocalDateTime.now();
        if (localDate.getSecond() % 2 == 0) {
            throw new UnsupportedOperationException();
        }

        return message;
    }
}
