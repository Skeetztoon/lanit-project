/*
 * Copyright (c) 2008-2020
 * LANIT
 * All rights reserved.
 *
 * This product and related documentation are protected by copyright and
 * distributed under licenses restricting its use, copying, distribution, and
 * decompilation. No part of this product or related documentation may be
 * reproduced in any form by any means without prior written authorization of
 * LANIT and its licensors, if any.
 *
 * $
 */
package ru.lanit.bpm.jedu.hrjedi.fw.spring;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@ComponentScan(basePackages = "ru.lanit.bpm.jedu.hrjedi")
@EnableJpaRepositories(basePackages = "ru.lanit.bpm.jedu.hrjedi.adapter.persistence")
@EntityScan(basePackages = "ru.lanit.bpm.jedu.hrjedi.domain")
@EnableProcessApplication("hrJedi")
@EnableJms
public class HrJediApplication {
    public static void main(String[] args) {
        SpringApplication.run(HrJediApplication.class, args);
    }
}
