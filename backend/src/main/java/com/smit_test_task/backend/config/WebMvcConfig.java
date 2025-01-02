package com.smit_test_task.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  public static final String API_V1 = "/v1";
  public static final String API_V1_PREFIX = "/api" + API_V1;

}
