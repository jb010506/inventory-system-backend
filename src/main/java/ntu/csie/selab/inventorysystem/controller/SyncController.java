package ntu.csie.selab.inventorysystem.controller;

import ntu.csie.selab.inventorysystem.model.Acquisition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ntu.csie.selab.inventorysystem.service.AcquisitionService;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/sync")
@CrossOrigin("http://localhost")
public class SyncController {
    @Autowired
    AcquisitionService acquisitionService;

    @PostMapping(value = "/acquisition", produces = "application/json")
    public void newAcquisition(@RequestBody Map<String, Object> map, HttpServletResponse response) {
        Acquisition acquisition = acquisitionService.newAcquisition(
                (String) map.getOrDefault("type", ""),
                (String) map.getOrDefault("donor", ""),
                (String) map.getOrDefault("contact", ""),
                (String) map.getOrDefault("phone", ""),
                (String) map.getOrDefault("date", "")
        );
    }
}
