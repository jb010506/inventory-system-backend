package ntu.csie.selab.inventorysystem.service;

import ntu.csie.selab.inventorysystem.exception.UnprocessableEntityException;
import ntu.csie.selab.inventorysystem.model.*;
import ntu.csie.selab.inventorysystem.repository.ItemHistoryAdjustReasonRepository;
import ntu.csie.selab.inventorysystem.repository.ItemHistoryEventRepository;
import ntu.csie.selab.inventorysystem.repository.ItemHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ItemHistoryService {
    @Autowired ItemHistoryRepository itemHistoryRepository;
    @Autowired ItemHistoryAdjustReasonRepository itemHistoryAdjustReasonRepository;
    @Autowired ItemHistoryEventRepository itemHistoryEventRepository;
    private static Map<String, ItemHistoryEvent> eventMap = null;
    private static Map<String, ItemHistoryAdjustReason> reasonMap = null;
    private void readAllEvent() {
        if (eventMap != null)
            return;
        eventMap = new HashMap<>();
        for (ItemHistoryEvent event : itemHistoryEventRepository.findAll())
            eventMap.put(event.getEvent(), event);
    }
    private void readAllReason() {
        if (reasonMap != null)
            return;
        reasonMap = new HashMap<>();
        for (ItemHistoryAdjustReason reason : itemHistoryAdjustReasonRepository.findAll())
            reasonMap.put(reason.getReason(), reason);
    }

    public void itemHistoryAdd(Item item, User user) {
        readAllEvent();
        readAllReason();
        ItemHistory itemHistory = new ItemHistory(item, user);
        itemHistory.setEvent(eventMap.get("Add to inventory"));
        itemHistory.setAdjust(item.getQuantity());
        itemHistoryRepository.save(itemHistory);
    }

    public void itemHistoryPriceSet(Item item, User user) {
        readAllEvent();
        readAllReason();
        ItemHistory itemHistory = new ItemHistory(item, user);
        itemHistory.setEvent(eventMap.get("Price set"));
        itemHistory.setPrice(item.getPrice());
        itemHistoryRepository.save(itemHistory);
    }

    public void itemHistoryPriceChanged(Item item, User user) {
        readAllEvent();
        readAllReason();
        ItemHistory itemHistory = new ItemHistory(item, user);
        itemHistory.setEvent(eventMap.get("Price changed"));
        itemHistory.setPrice(item.getPrice());
        itemHistoryRepository.save(itemHistory);
    }

    public void itemHistorySplit(Item item, User user, Integer split) {
        readAllEvent();
        readAllReason();
        ItemHistory itemHistory = new ItemHistory(item, user);
        itemHistory.setEvent(eventMap.get("Item split"));
        itemHistory.setAdjust(split);
        itemHistoryRepository.save(itemHistory);
    }

    public void itemHistorySold(Item item, Integer sold, Double price) {
        readAllEvent();
        readAllReason();
        ItemHistory itemHistory = new ItemHistory(item, null);
        itemHistory.setEvent(eventMap.get("Item sold"));
        itemHistory.setAdjust(sold);
        itemHistory.setPrice(price);
        itemHistoryRepository.save(itemHistory);
    }

    public void itemHistoryAdjust(Item item, User user, Integer adjust, String reason, String comment) {
        readAllEvent();
        readAllReason();
        ItemHistory itemHistory = new ItemHistory(item, user);
        itemHistory.setEvent(eventMap.get("Quantity adjusted"));
        itemHistory.setAdjust(adjust);
        if (!reasonMap.containsKey(reason))
            throw new UnprocessableEntityException("Invalid value in field: 'reason'.");
        itemHistory.setReason(reasonMap.get(reason));
        itemHistory.setComment(comment);
        itemHistoryRepository.save(itemHistory);
    }
}
