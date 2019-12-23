package ntu.csie.selab.inventorysystem.service;

import ntu.csie.selab.inventorysystem.exception.NotFoundException;
import ntu.csie.selab.inventorysystem.exception.UnprocessableEntityException;
import ntu.csie.selab.inventorysystem.model.Acquisition;
import ntu.csie.selab.inventorysystem.model.AcquisitionStatus;
import ntu.csie.selab.inventorysystem.model.AcquisitionType;
import ntu.csie.selab.inventorysystem.model.Item;
import ntu.csie.selab.inventorysystem.repository.AcquisitionRepository;
import ntu.csie.selab.inventorysystem.repository.AcquisitionStatusRepository;
import ntu.csie.selab.inventorysystem.repository.AcquisitionTypeRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AcquisitionService {
    @Autowired
    AcquisitionRepository acquisitionRepository;
    @Autowired
    AcquisitionTypeRepository acquisitionTypeRepository;
    @Autowired
    AcquisitionStatusRepository acquisitionStatusRepository;
    private static Map<String, AcquisitionType> typeMap = null;
    private static Map<String, AcquisitionStatus> statusMap = null;

    public Acquisition newAcquisition(Acquisition.AcquisitionValidation acquisitionValidation) {
        readAllTypes();
        readAllStatus();
        Acquisition acquisition = new Acquisition();
        if (!typeMap.containsKey(acquisitionValidation.type))
            throw new UnprocessableEntityException("Invalid value in field: 'type'.");
        acquisition.setType(typeMap.get(acquisitionValidation.type));
        acquisition.setDonor(acquisitionValidation.donor);
        acquisition.setContact(acquisitionValidation.contact);
        acquisition.setPhone(acquisitionValidation.phone);
        acquisition.setDate(acquisitionValidation.date);
        acquisition.setStatus(statusMap.get("Expected"));
        return acquisitionRepository.save(acquisition);
    }

    public List<Acquisition> acquisitionList() {
        List<Acquisition> list = new ArrayList<>();
        for (Acquisition acquisition : acquisitionRepository.findAll())
            list.add(acquisition);
        return list;
    }

    public Acquisition acquisitionById(Integer id) {
        Optional<Acquisition> result = acquisitionRepository.findById(id);
        if (!result.isPresent())
            throw new NotFoundException(String.format("No acquisition found with id: %d.", id));
        return result.get();
    }
    public String getAcquisitionItemsById(Integer id) throws JSONException {

        List<Item> list = acquisitionRepository.getItemIdWithAcqusition(id);
        /*System.out.println(list.size());
        for(int j=0;j<list.size();j++){
            System.out.println(list.get(j));
        }*/
        JSONArray jsonA = new JSONArray();
        for(int i=0;i<list.size();i++){
            JSONObject jsonO = new JSONObject();
            jsonO.put("id",list.get(i).getId());
            jsonO.put("description",list.get(i).getDescription());
            jsonO.put("quantity",list.get(i).getQuantity());
            jsonO.put("price",list.get(i).getPrice());
            jsonO.put("condition",list.get(i).getCondition());
            jsonO.put("aid",id);
            jsonA.put(i,jsonO);
        }
        /*System.out.println(jsonA.length());
        System.out.println(jsonA);*/

        return jsonA.toString();
    }

    private void readAllTypes() {
        if (typeMap != null)
            return;
        typeMap = new HashMap<>();
        for (AcquisitionType type : acquisitionTypeRepository.findAll())
            typeMap.put(type.getType(), type);
    }
    private void readAllStatus() {
        if (statusMap != null)
            return;
        statusMap = new HashMap<>();
        for (AcquisitionStatus status : acquisitionStatusRepository.findAll())
            statusMap.put(status.getStatus(), status);
    }
}
