package ntu.csie.selab.inventorysystem.service;

import ntu.csie.selab.inventorysystem.exception.NotFoundException;
import ntu.csie.selab.inventorysystem.exception.UnprocessableEntityException;
import ntu.csie.selab.inventorysystem.model.Acquisition;
import ntu.csie.selab.inventorysystem.model.AcquisitionStatus;
import ntu.csie.selab.inventorysystem.model.AcquisitionType;
import ntu.csie.selab.inventorysystem.repository.AcquisitionRepository;
import ntu.csie.selab.inventorysystem.repository.AcquisitionStatusRepository;
import ntu.csie.selab.inventorysystem.repository.AcquisitionTypeRepository;
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
