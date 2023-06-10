package com.jmsoftware.objectdiff.samplemodel.policychange;

import com.jmsoftware.objectdiff.ClassMapping;
import com.jmsoftware.objectdiff.FieldMapping;
import com.jmsoftware.objectdiff.samplemodel.enumeration.Gender;
import com.jmsoftware.objectdiff.samplemodel.enumeration.IdType;
import com.jmsoftware.objectdiff.samplemodel.policy.PolicyCustomer;
import com.neovisionaries.i18n.CountryCode;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

/**
 * <h1>Policyholder</h1>
 * <p>
 * Change description here.
 *
 * @author Johnny Miller (鍾俊), email: johnnysviva@outlook.com, 6/9/23 10:38 PM
 **/
@Data
@ClassMapping(source = PolicyCustomer.class)
public class Policyholder {
    @FieldMapping(source = "fullName")
    private String fullName;
    @FieldMapping(source = "dob")
    private LocalDate dateOfBirth;
    @FieldMapping(source = "gender")
    private Gender gender;
    @FieldMapping(source = "nationality")
    private CountryCode nationality;
    @FieldMapping(source = "idType", needUnwrapping = true, targetMethodName = "getValue")
    private IdType idType;
    @FieldMapping(source = "idNumber")
    private String idNumber;
    @FieldMapping(source = "email", isSourceNested = true)
    private Set<Email> email;
    @FieldMapping(source = "mobilePhone", isSourceNested = true)
    private Set<MobilePhone> mobilePhone;
}
