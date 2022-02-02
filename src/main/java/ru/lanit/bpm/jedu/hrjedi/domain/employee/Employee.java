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
package ru.lanit.bpm.jedu.hrjedi.domain.employee;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;
import ru.lanit.bpm.jedu.hrjedi.domain.security.Role;
import ru.lanit.bpm.jedu.hrjedi.domain.security.State;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "EMPLOYEE", uniqueConstraints = {
    @UniqueConstraint(columnNames = {
        "LOGIN"
    })
})
@RequiredArgsConstructor
@Getter
@Setter
public class Employee implements Serializable {
    @Serial
    private static final long serialVersionUID = -5449326074498337967L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_id_generator")
    @SequenceGenerator(name = "employee_id_generator", sequenceName = "sq_employee_id", allocationSize = 1)
    private Long id;

    @NaturalId
    private String login;

    private String firstName;
    private String patronymic;
    private String lastName;

    private String hashPassword;

    private String email;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "EMPLOYEE_ROLE",
        joinColumns = @JoinColumn(name = "EMPLOYEE_ID"),
        inverseJoinColumns = @JoinColumn(name = "ROLE_ID")
    )
    private Set<Role> roles = new HashSet<>();

    @Enumerated(value = EnumType.STRING)
    private State state;

    public Employee(String login, String firstName, String patronymic, String lastName, String hashPassword, String email) {
        this.login = login;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.lastName = lastName;
        this.hashPassword = hashPassword;
        this.email = email;
    }
}
