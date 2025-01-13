package com.smit_test_task.backend.interfaces;

import java.util.List;

import com.smit_test_task.backend.model.Slot;

public interface JsonProcessorInterface {
    public List<Slot<?>> processSlotsJson(String jsonPayload);
}
