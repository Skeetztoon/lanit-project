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
package ru.lanit.bpm.jedu.hrjedi.domain.security;

import lombok.*;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "ROLE")
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Role implements Serializable {
    @Serial
    private static final long serialVersionUID = -5449326074498337967L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_id_generator")
    @SequenceGenerator(name = "role_id_generator", sequenceName = "sq_role_id", allocationSize = 1)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NaturalId
    private RoleName name;
}
