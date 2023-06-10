package com.jmsoftware.objectdiff.samplemodel.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * <h1>IdType</h1>
 * <p>
 * Change description here.
 *
 * @author Johnny Miller (鍾俊), email: johnnysviva@outlook.com, 6/9/23 10:49 PM
 **/
@Getter
@ToString
@RequiredArgsConstructor
public enum IdType {
    PASSPORT(1),
    DRIVERS_LICENSE(2),
    STATE_ISSUED_IDENTIFICATION_CARD(2),
    MILITARY_ID(4),
    ;

    private final int value;
}
