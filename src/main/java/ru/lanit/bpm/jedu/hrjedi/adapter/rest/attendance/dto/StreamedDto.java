/*
 * Copyright (c) 2008-2013
 * LANIT
 * All rights reserved.
 *
 * This product and related documentation are protected by copyright and
 * distributed under licenses restricting its use, copying, distribution, and
 * decompilation. No part of this product or related documentation may be
 * reproduced in any form by any means without prior written authorization of
 * LANIT and its licensors, if any.
 *
 * $Id$
 */
package ru.lanit.bpm.jedu.hrjedi.adapter.rest.attendance.dto;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Интерфейс для "отложенного" получения объектов в поток вывода.
 */
@FunctionalInterface
public interface StreamedDto {
    /**
     * Записать объект.
     *
     * @param outputStream поток вывода
     * @throws IOException ошибка вывода
     */
    void write(OutputStream outputStream) throws IOException;
}
