package com.jmsoftware.objectdiff.samplemodel.policychange;

import com.jmsoftware.objectdiff.ClassMapping;
import com.jmsoftware.objectdiff.FieldMapping;
import com.jmsoftware.objectdiff.samplemodel.policy.Policy;
import lombok.Data;

/**
 * <h1>PolicyChangeApplication</h1>
 * <p>
 * Change description here.
 *
 * @author Johnny Miller (鍾俊), email: johnnysviva@outlook.com, 6/9/23 10:36 PM
 **/
@Data
@ClassMapping(source = Policy.class)
public class PolicyChangeApplication {
    private String applicationNumber;
    private String policyNumber;
    @FieldMapping(sourceClass = Policy.class, source = "policyHolder", isSourceNested = true)
    private Policyholder policyholder;
}
