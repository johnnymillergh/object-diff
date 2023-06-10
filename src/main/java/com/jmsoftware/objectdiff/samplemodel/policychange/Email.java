package com.jmsoftware.objectdiff.samplemodel.policychange;

import com.jmsoftware.objectdiff.ClassMapping;
import com.jmsoftware.objectdiff.FieldMapping;
import com.jmsoftware.objectdiff.samplemodel.customer.CustomerEmail;
import lombok.Data;

/**
 * <h1>Email</h1>
 * <p>
 * Change description here.
 *
 * @author Johnny Miller (鍾俊), email: johnnysviva@outlook.com, 6/9/23 10:53 PM
 **/
@Data
@ClassMapping(source = CustomerEmail.class)
public class Email {
    @FieldMapping(source = "email")
    private String email;
}
