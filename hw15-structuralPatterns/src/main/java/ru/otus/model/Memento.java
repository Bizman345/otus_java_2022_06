package ru.otus.model;

import java.util.stream.Collectors;

public class Memento {
    private Message message;

    public Memento(Message message) {
        ObjectForMessage objectForMessage = new ObjectForMessage();
        objectForMessage.setData(message.getField13().getData().stream().collect(Collectors.toList()));
        this.message = new Message.Builder(message.getId())
                .field1(message.getField1())
                .field2(message.getField2())
                .field3(message.getField3())
                .field4(message.getField4())
                .field5(message.getField5())
                .field6(message.getField6())
                .field7(message.getField7())
                .field8(message.getField8())
                .field9(message.getField9())
                .field10(message.getField10())
                .field11(message.getField11())
                .field12(message.getField12())
                .field13(objectForMessage)
                .build();
    }

    public Message getMessage() {
        return message;
    }
}
