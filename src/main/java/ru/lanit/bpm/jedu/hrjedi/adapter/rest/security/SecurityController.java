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
package ru.lanit.bpm.jedu.hrjedi.adapter.rest.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lanit.bpm.jedu.hrjedi.adapter.rest.security.dto.JwtResponse;
import ru.lanit.bpm.jedu.hrjedi.adapter.rest.security.dto.LoginFormDto;
import ru.lanit.bpm.jedu.hrjedi.app.api.security.LoginInbound;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/hr-rest/security")
@RequiredArgsConstructor
public class SecurityController {
    private final LoginInbound loginInbound;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticateEmployee(@RequestBody LoginFormDto loginFormDto) {
        String jwtToken = loginInbound.execute(loginFormDto.getLogin(), loginFormDto.getPassword());

        return ResponseEntity.ok(new JwtResponse(jwtToken));
    }
}
