package com.smit_test_task.backend;

// import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
// import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;

import com.smit_test_task.backend.controller.WorkshopsController;
import com.smit_test_task.backend.model.Workshop;

import static com.smit_test_task.backend.config.WebMvcConfig.API_V1_PREFIX;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

@WebMvcTest(WorkshopsController.class)
public class TestWorkshopsController {

    @Autowired
    private MockMvc MockMvc;

    @MockitoBean
    private WorkshopsController workshopsController;

    @Test
    public void testGetWorkshopsReturnsDataResponse() throws Exception {

        List<Workshop> mockWorkshops = List.of(new Workshop(1, "name", "address", new String[] { "Car", "Truck" }));
        when(workshopsController.getWorkshops()).thenReturn(ResponseEntity.ok(mockWorkshops));

        MockMvc.perform(get(API_V1_PREFIX + "/workshops"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("name"))
                .andExpect(jsonPath("$[0].address").value("address"))
                .andExpect(jsonPath("$[0].vehicleTypes.length()").value(2))
                .andExpect(jsonPath("$[0].vehicleTypes[0]").value("Car"))
                .andExpect(jsonPath("$[0].vehicleTypes[1]").value("Truck"));
    }

}
