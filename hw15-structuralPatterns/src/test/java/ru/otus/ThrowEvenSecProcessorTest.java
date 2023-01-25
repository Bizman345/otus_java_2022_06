package ru.otus;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.model.Message;
import ru.otus.processor.ThrowEvenSecProcessor;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ThrowEvenSecProcessorTest {

    @Test
    @DisplayName("Тест на четную секунду")
    void exceptionTest() throws InterruptedException {
        var message = new Message.Builder(1L).field8("field8").build();

        ThrowEvenSecProcessor processor = new ThrowEvenSecProcessor();

        LocalDateTime localDateTime = LocalDateTime.now();
        if (localDateTime.getSecond() % 2 != 0) {
            Thread.sleep(1000);
        }
        assertThrows(UnsupportedOperationException.class, () -> {processor.process(message);});
    }
}
