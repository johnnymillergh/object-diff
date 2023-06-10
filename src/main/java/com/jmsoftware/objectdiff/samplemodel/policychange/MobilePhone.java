package com.jmsoftware.objectdiff.samplemodel.policychange;

import com.jmsoftware.objectdiff.ClassMapping;
import com.jmsoftware.objectdiff.FieldMapping;
import com.jmsoftware.objectdiff.samplemodel.customer.CustomerMobilePhone;
import lombok.Data;

/**
 * <h1>MobilePhone</h1>
 * <p>
 * Change description here.
 *
 * @author Johnny Miller (鍾俊), email: johnnysviva@outlook.com, 6/9/23 10:54 PM
 **/
@Data
@ClassMapping(source = CustomerMobilePhone.class)
public class MobilePhone {
    @FieldMapping(source = "countryCode")
    private String countryCode;
    @FieldMapping(source = "phoneNumber")
    private String phoneNumber;
}
