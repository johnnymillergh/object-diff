package com.jmsoftware.objectdiff.samplemodel.policy;

import com.jmsoftware.objectdiff.samplemodel.customer.CustomerEmail;
import com.jmsoftware.objectdiff.samplemodel.customer.CustomerMobilePhone;
import com.jmsoftware.objectdiff.samplemodel.enumeration.Gender;
import com.neovisionaries.i18n.CountryCode;
import lombok.Data;

import java.time.LocalDate;

/**
 * <h1>PolicyCustomer</h1>
 * <p>
 * Change description here.
 *
 * @author Johnny Miller (鍾俊), email: johnnysviva@outlook.com, 6/9/23 11:15 PM
 **/
@Data
public class PolicyCustomer {
    private String fullName;
    private LocalDate dob;
    private Gender gender;
    private CountryCode nationality;
    private Integer idType;
    private String idNumber;
    private CustomerEmail email;
    private CustomerMobilePhone mobilePhone;
}
