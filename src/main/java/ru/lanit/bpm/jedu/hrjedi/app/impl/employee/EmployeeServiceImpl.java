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
package ru.lanit.bpm.jedu.hrjedi.app.impl.employee;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lanit.bpm.jedu.hrjedi.adapter.hibernate.employee.EmployeeRepository;
import ru.lanit.bpm.jedu.hrjedi.adapter.hibernate.employee.RoleRepository;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.EmployeeRegistrationException;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.EmployeeService;
import ru.lanit.bpm.jedu.hrjedi.domain.Employee;
import ru.lanit.bpm.jedu.hrjedi.domain.security.Role;
import ru.lanit.bpm.jedu.hrjedi.domain.security.RoleName;
import ru.lanit.bpm.jedu.hrjedi.domain.security.State;

import javax.persistence.EntityNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;
import static ru.lanit.bpm.jedu.hrjedi.domain.security.RoleName.ROLE_ADMIN;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final String headOfHrLogin;
    private final RoleRepository roleRepository;

    public EmployeeServiceImpl(
        EmployeeRepository employeeRepository,
        @Value("${ru.lanit.bpm.jedu.hrjedi.headOfHrLogin}") String headOfHrLogin,
        PasswordEncoder passwordEncoder,
        RoleRepository roleRepository
    ) {
        this.employeeRepository = employeeRepository;
        this.headOfHrLogin = headOfHrLogin;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public Employee findByLogin(String login) {
        return employeeRepository.findByLoginIgnoreCase(login.trim())
            .orElseThrow(() -> new EntityNotFoundException("User Not Found with -> username: " + login));
    }

    @Transactional(readOnly = true)
    @Override
    public Employee findWellKnownEmployeeHeadOfHr() {
        return findByLogin(headOfHrLogin);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public long getNumberOfAdmins() {
        return employeeRepository.findAll().stream()
            .filter(user ->
                user.getRoles().stream()
                    .anyMatch(role -> ROLE_ADMIN.equals(role.getName())))
            .count();
    }

    @Transactional
    @Override
    public void createEmployee(String login, String firstName, String patronymic, String lastName, String password, String email, Set<String> rolesStrings) {
        String trimmedLoginInLowerCase = login.trim().toLowerCase();

        validateRegisteredLogin(trimmedLoginInLowerCase);
        validateRegisteredEmail(email);

        Employee user = new Employee(trimmedLoginInLowerCase, firstName, patronymic, lastName, passwordEncoder.encode(password), email);
        user.setRoles(validateAndGetRegisteredRoles(rolesStrings));
        user.setState(State.ACTIVE);
        employeeRepository.save(user);
    }

    @Transactional
    @Override
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Transactional(readOnly = true)
    @Override
    public String getEmployeeFullNameByLogin(String login) {
        return findByLogin(login)
            .getFullName();
    }

    /**
     * Legacy code used to load classes by reflection
     *
     * @return secure password
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public String generateSecurePassword() {
        try {
            Class digitsRuleClass = getClass().getClassLoader().loadClass("org.passay.DigitCharacterRule");
            Class loweCharsRuleClass = getClass().getClassLoader().loadClass("org.passay.LowercaseCharacterRule");
            Class upperCharsRuleClass = getClass().getClassLoader().loadClass("org.passay.UppercaseCharacterRule");
            Class passwordGeneratorClass = getClass().getClassLoader().loadClass("org.passay.PasswordGenerator");
            Object digits = digitsRuleClass.getConstructor(int.class).newInstance(2);
            Object lowerChars = loweCharsRuleClass.getConstructor(int.class).newInstance(4);
            Object upperChars = upperCharsRuleClass.getConstructor(int.class).newInstance(2);
            Object passwordGenerator = passwordGeneratorClass.newInstance();
            return (String) MethodUtils.invokeMethod(passwordGenerator, "generatePassword", 8, asList(digits, lowerChars, upperChars));
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalStateException("Unable to load library", e);
        }
    }

    // ===================================================================================================================
    // = Implementation
    // ===================================================================================================================

    private void validateRegisteredLogin(String login) {
        if (employeeRepository.existsByLogin(login)) {
            throw new EmployeeRegistrationException("Employee with this login already exists!");
        }
    }

    private void validateRegisteredEmail(String email) {
        if (employeeRepository.existsByEmail(email)) {
            throw new EmployeeRegistrationException("Employee with this email already exists!");
        }
    }

    private Set<Role> validateAndGetRegisteredRoles(Set<String> rolesStrings) {
        Set<Role> registeredRoles = new HashSet<>();

        rolesStrings.forEach(roleString -> {
            RoleName registeredRoleName = extractRoleNameFromRoleString(roleString);
            Role registeredRole = roleRepository.findByName(registeredRoleName)
                .orElseThrow(() -> new EmployeeRegistrationException("Could not find provided role by role name in the database"));
            registeredRoles.add(registeredRole);
        });

        return registeredRoles;
    }

    private RoleName extractRoleNameFromRoleString(String roleString) {
        switch (roleString.trim().toLowerCase()) {
            case "admin":
                return RoleName.ROLE_ADMIN;
            case "omni":
                return RoleName.ROLE_OMNI;
            case "hr":
                return RoleName.ROLE_HR;
            case "user":
                return RoleName.ROLE_USER;
            default:
                throw new EmployeeRegistrationException("Invalid role was given for registration");
        }
    }
}
