package ntu.csie.selab.inventorysystem.service;

import ntu.csie.selab.inventorysystem.exception.UnprocessableEntityException;
import ntu.csie.selab.inventorysystem.model.Acquisition;
import ntu.csie.selab.inventorysystem.model.AcquisitionStatus;
import ntu.csie.selab.inventorysystem.model.AcquisitionType;
import ntu.csie.selab.inventorysystem.repository.AcquisitionRepository;
import ntu.csie.selab.inventorysystem.repository.AcquisitionStatusRepository;
import ntu.csie.selab.inventorysystem.repository.AcquisitionTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public Acquisition newAcquisition(String type, String donor, String contact, String phone, String dateString) {
        Acquisition acquisition = new Acquisition();
        if (typeMap == null)
            typeMap = readAllTypes();
        if (!typeMap.containsKey(type))
            throw new UnprocessableEntityException("Invalid value in field: 'type'.");
        acquisition.setType(typeMap.get(type));
        acquisition.setDonor(donor);
        acquisition.setContact(contact);
        acquisition.setPhone(phone);
        try {
            acquisition.setDate(new SimpleDateFormat("yyyy/MM/dd").parse(dateString));
        } catch (ParseException e) {
            throw new UnprocessableEntityException("Invalid value in field: 'date'.");
        }
        if (statusMap == null)
            statusMap = readAllStatus();
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
        return result.orElse(null);
    }

    private Map<String, AcquisitionType> readAllTypes() {
        Map<String, AcquisitionType> map = new HashMap<>();
        for (AcquisitionType type : acquisitionTypeRepository.findAll())
            map.put(type.getType(), type);
        return map;
    }
    private Map<String, AcquisitionStatus> readAllStatus() {
        Map<String, AcquisitionStatus> map = new HashMap<>();
        for (AcquisitionStatus status : acquisitionStatusRepository.findAll())
            map.put(status.getStatus(), status);
        return map;
    }
}
