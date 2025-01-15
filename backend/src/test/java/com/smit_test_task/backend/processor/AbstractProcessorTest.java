package com.smit_test_task.backend.processor;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;

public abstract class AbstractProcessorTest {

    public JsonProcessor jsonProcessor = new JsonProcessor();

    public XmlProcessor xmlProcessor = new XmlProcessor();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

}
