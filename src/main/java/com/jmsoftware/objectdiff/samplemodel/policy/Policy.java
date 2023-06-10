package com.jmsoftware.objectdiff.samplemodel.policy;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <h1>Policy</h1>
 * <p>
 * Change description here.
 *
 * @author Johnny Miller (鍾俊), email: johnnysviva@outlook.com, 6/9/23 11:01 PM
 **/
@Data
public class Policy {
    private String policyNo;
    private String policyName;
    private LocalDateTime issuedAt;
    private BigDecimal premium;
    private PolicyCustomer policyHolder;
}
