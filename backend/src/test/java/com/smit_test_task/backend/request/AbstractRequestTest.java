package com.smit_test_task.backend.request;

import java.util.Map;

import org.mockito.Mock;
import org.springframework.web.client.RestTemplate;

import com.smit_test_task.backend.model.Path;
import com.smit_test_task.backend.model.Workshop;

public abstract class AbstractRequestTest {

    public Path slotsPath;
    public Map<String, Path> paths;
    public Workshop workshop;

    @Mock
    public RestTemplate restTemplate;

}
