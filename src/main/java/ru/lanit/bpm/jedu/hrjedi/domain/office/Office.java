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
package ru.lanit.bpm.jedu.hrjedi.domain.office;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "OFFICE")
@Getter
@Setter
public class Office {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "office_id_generator")
    @SequenceGenerator(name = "office_id_generator", sequenceName = "sq_office_id", allocationSize = 1)
    private Long id;
    private String name;
}
