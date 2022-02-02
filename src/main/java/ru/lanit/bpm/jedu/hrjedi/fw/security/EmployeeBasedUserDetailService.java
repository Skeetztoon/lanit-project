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
package ru.lanit.bpm.jedu.hrjedi.fw.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.FindEmployeeByLoginInbound;
import ru.lanit.bpm.jedu.hrjedi.app.api.employee.GetEmployeeFullNameInbound;
import ru.lanit.bpm.jedu.hrjedi.domain.employee.Employee;
import ru.lanit.bpm.jedu.hrjedi.domain.security.UserPrinciple;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class EmployeeBasedUserDetailService implements UserDetailsService {
    private final FindEmployeeByLoginInbound findEmployeeByLoginInbound;
    private final GetEmployeeFullNameInbound getEmployeeFullNameInbound;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {
        try {
            Employee user = findEmployeeByLoginInbound.execute(username);
            return buildUserDetails(user);
        } catch (EntityNotFoundException e) {
            throw new UsernameNotFoundException("User not found", e);
        }
    }

    // ===================================================================================================================
    // = Implementation
    // ===================================================================================================================

    private UserDetails buildUserDetails(Employee user) {
        var authorities = user.getRoles().stream().map(role ->
            new SimpleGrantedAuthority(role.getName().name())
        ).toList();

        return new UserPrinciple(
            user.getId(),
            user.getLogin(),
            user.getEmail(),
            user.getHashPassword(),
            getEmployeeFullNameInbound.execute(user),
            authorities
        );
    }
}
