package com.smit_test_task.backend.model;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@Getter
@Setter
@JacksonXmlRootElement
public class ContactInformation {

    public String contactInformation;

}
