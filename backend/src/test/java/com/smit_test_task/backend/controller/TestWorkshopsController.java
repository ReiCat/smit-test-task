package com.smit_test_task.backend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.smit_test_task.backend.controller.WorkshopsController;
import com.smit_test_task.backend.model.Path;
import com.smit_test_task.backend.model.Workshop;

import static com.smit_test_task.backend.config.WebMvcConfig.API_V1_PREFIX;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebMvcTest(WorkshopsController.class)
public class TestWorkshopsController {

    @Autowired
    private MockMvc MockMvc;

    @MockitoBean
    private WorkshopsController workshopsController;

    @Test
    public void testGetWorkshopsReturnsDataResponse() throws Exception {
        String[] vehicleTypes = new String[] { "Car", "Truck" };
        Path slotsPath = new Path();
        slotsPath.path = "path";
        slotsPath.method = "GET";
        Map<String, Path> paths = new HashMap<String, Path>() {
        };
        paths.put("slotsPath", slotsPath);
        Workshop workshop = new Workshop(
                1,
                "name",
                "address",
                vehicleTypes,
                "url",
                "apiPrefix",
                "contentType",
                paths);
        List<Workshop> mockWorkshops = List.of(workshop);
        when(workshopsController.getWorkshops()).thenReturn(ResponseEntity.ok(mockWorkshops));

        MockMvc.perform(get(API_V1_PREFIX + "/workshops"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("name"))
                .andExpect(jsonPath("$[0].address").value("address"))
                .andExpect(jsonPath("$[0].vehicleTypes.length()").value(2))
                .andExpect(jsonPath("$[0].vehicleTypes[0]").value(vehicleTypes[0]))
                .andExpect(jsonPath("$[0].vehicleTypes[1]").value(vehicleTypes[1]))
                .andExpect(jsonPath("$[0].url").value("url"))
                .andExpect(jsonPath("$[0].apiPrefix").value("apiPrefix"))
                .andExpect(jsonPath("$[0].contentType").value("contentType"))
                .andExpect(jsonPath("$[0].paths.slotsPath.path").value("path"))
                .andExpect(jsonPath("$[0].paths.slotsPath.method").value("GET"));
    }

}
