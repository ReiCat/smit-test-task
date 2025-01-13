package com.smit_test_task.backend.interfaces;

// Java Program to Illustrate TodoService File

// Importing List class
import java.util.List;

// Interface
public interface TodoService {

    // Creating a simple method retrieveTodos()
    public List<String> retrieveTodos(String user);
}
