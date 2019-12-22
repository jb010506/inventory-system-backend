package ntu.csie.selab.inventorysystem.service;

import ntu.csie.selab.inventorysystem.exception.NotFoundException;
import ntu.csie.selab.inventorysystem.exception.UnprocessableEntityException;
import ntu.csie.selab.inventorysystem.model.*;
import ntu.csie.selab.inventorysystem.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ItemService {
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ItemConditionRepository itemConditionRepository;
    @Autowired
    InventoryRepository inventoryRepository;
    @Autowired
    ItemHistoryService itemHistoryService;
    private static Map<String, ItemCondition> conditionMap = null;

    public List<Map<String, Object>> itemList() {
        List<Map<String, Object>> list = new ArrayList<>();
        for (Item item : itemRepository.findAll())
            list.add(itemReturnMap(inventoryRepository.findByItem(item)));
        return list;
    }

    public List<Map<String, Object>> itemHierarchyLeafList(Integer did, Integer cid, Integer scid) {
        List<Map<String, Object>> list = new ArrayList<>();
        List<Inventory> query;
        if (scid == null && cid == null)
            query = inventoryRepository.findLeafByHierarchy(new Department(did));
        else if (scid == null)
            query = inventoryRepository.findLeafByHierarchy(new Department(did), new Category(cid));
        else
            query = inventoryRepository.findLeafByHierarchy(new Department(did), new Category(cid), new Category(scid));
        for (Inventory inventory : query)
            list.add(itemReturnMap(inventory));
        return list;
    }

    public List<Map<String, Object>> itemHierarchySubtreeList(Integer did, Integer cid, Integer scid) {
        List<Map<String, Object>> list = new ArrayList<>();
        List<Inventory> query;
        if (scid == null && cid == null)
            query = inventoryRepository.findSubtreeByHierarchy(new Department(did));
        else if (scid == null)
            query = inventoryRepository.findSubtreeByHierarchy(new Department(did), new Category(cid));
        else
            query = inventoryRepository.findLeafByHierarchy(new Department(did), new Category(cid), new Category(scid));
        for (Inventory inventory : query)
            list.add(itemReturnMap(inventory));
        return list;
    }

    public Item itemById(Integer id) {
        Optional<Item> result = itemRepository.findById(id);
        if (!result.isPresent())
            throw new NotFoundException(String.format("No item found with id: %d.", id));
        return result.get();
    }

    public Item itemInsert(Item.ItemValidation itemValidation, Integer uid) {
        readAllCondition();
        Item item = itemValidation.toItem();
        if (!conditionMap.containsKey(itemValidation.condition))
            throw new UnprocessableEntityException("Invalid value in field: 'condition'.");
        item.setCondition(conditionMap.get(itemValidation.condition));
        item = itemRepository.save(item);

        Inventory inventory = new Inventory();
        inventory.setItem(item);
        inventory.setDepartment(itemValidation.did == null ? null : new Department(itemValidation.did));
        inventory.setCategory(itemValidation.cid == null ? null : new Category(itemValidation.cid));
        inventory.setSubcategory(itemValidation.scid == null ? null : new Category(itemValidation.scid));
        inventoryRepository.save(inventory);

        itemHistoryService.itemHistoryAdd(item, new User(uid));
        if (item.getPrice() > 0)
            itemHistoryService.itemHistoryPriceSet(item, new User(uid));
        return item;
    }

    public Item itemUpdate(Item.ItemValidation itemValidation, Integer id, Integer uid) {
        readAllCondition();
        Item origin = itemById(id);
        Item item = itemValidation.toItem();
        item.setId(id);
        if (!conditionMap.containsKey(itemValidation.condition))
            throw new UnprocessableEntityException("Invalid value in field: 'condition'.");
        item.setCondition(conditionMap.get(itemValidation.condition));
        if (!origin.getQuantity().equals(item.getQuantity()))
            itemHistoryService.itemHistoryAdjust(item, new User(uid),
                    item.getQuantity() - origin.getQuantity(), itemValidation.reason, itemValidation.comment);
        if (!origin.getPrice().equals(item.getPrice()))
            itemHistoryService.itemHistoryPriceChanged(item, new User(uid));
        itemRepository.save(item);
        return item;
    }

    private Map<String, Object> itemReturnMap(Inventory inventory) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", inventory.getItem().getId());
        map.put("description", inventory.getItem().getDescription());
        map.put("quantity", inventory.getItem().getQuantity());
        map.put("price", inventory.getItem().getPrice());
        map.put("condition", inventory.getItem().getCondition().toString());
        map.put("department", inventory.getDepartment().getName());
        map.put("category", String.format("%s%s",
                inventory.getCategory() == null ? "" : inventory.getCategory().getName(),
                inventory.getSubcategory() == null ? "" : ", " + inventory.getSubcategory().getName()));
        map.put("donor", inventory.getItem().getAcquisition() == null ? "" : inventory.getItem().getAcquisition().getDonor());
        return map;
    }

    private void readAllCondition() {
        if (conditionMap != null)
            return;
        conditionMap = new HashMap<>();
        for (ItemCondition condition : itemConditionRepository.findAll())
            conditionMap.put(condition.getCondition(), condition);
    }
}
