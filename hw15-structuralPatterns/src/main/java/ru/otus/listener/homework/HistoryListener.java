package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.model.Memento;
import ru.otus.model.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HistoryListener implements Listener, HistoryReader {
    Map<Long, Message> map = new HashMap<>();


    @Override
    public void onUpdated(Message msg) {
        map.put(msg.getId(), (new Memento(msg)).getMessage());
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return Optional.of(map.get(id));
    }
}
