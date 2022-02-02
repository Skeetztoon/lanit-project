/*
 * Copyright (c) 2008-2014
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

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.OutputStream;

@RequiredArgsConstructor
@Getter
public class DownloadableFileDto implements StreamedDto {
    private final String utf8Name;
    private final String asciiName;
    private final StreamedDto content;
    private final String contentType;

    public DownloadableFileDto(String utf8Name, String asciiName, StreamedDto content) {
        this(utf8Name, asciiName, content, "application/octet-stream");
    }

    @Override
    public void write(OutputStream outputStream) throws IOException {
        content.write(outputStream);
    }
}
