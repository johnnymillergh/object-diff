package com.jmsoftware.objectdiff;

import com.jmsoftware.objectdiff.samplemodel.policy.Policy;
import com.jmsoftware.objectdiff.samplemodel.policychange.PolicyChangeApplication;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.jmsoftware.objectdiff.ObjectDiffHelper.countNonNullFields;
import static com.jmsoftware.objectdiff.ObjectDiffHelper.diffObjects;
import static com.jmsoftware.objectdiff.UnitTestHelper.parse;
import static java.util.Collections.emptySet;
import static org.junit.jupiter.api.Assertions.*;

/**
 * <h1>ObjectDiffHelperTest</h1>
 * <p>
 * Change description here.
 *
 * @author Johnny Miller (鍾俊), email: johnnysviva@outlook.com, 6/9/23 11:33 PM
 **/
@Slf4j
@ExtendWith(MockitoExtension.class)
class ObjectDiffHelperTest {
    @Test
    void testDiffObjects_whenOnlyOneFieldIsDifferent_thenOnlyOneFieldInDiffedObjectIsNonNull() {
        val policyChangeApplication = parse("/test-datasets/policy-change-application-01.json",
                PolicyChangeApplication.class);
        val policy = parse("/test-datasets/policy-01.json", Policy.class);
        val diffed = assertDoesNotThrow(() -> diffObjects(policy, policyChangeApplication));
        val nonNullFields = countNonNullFields(diffed, emptySet());
        assertEquals(3, nonNullFields);
        assertNotNull(diffed.getPolicyholder().getIdNumber());
        assertNull(diffed.getPolicyholder().getEmail().stream().findFirst().orElseThrow().getEmail());
        assertNull(diffed.getPolicyholder().getMobilePhone().stream().findFirst().orElseThrow().getCountryCode());
        assertNull(diffed.getPolicyholder().getMobilePhone().stream().findFirst().orElseThrow().getPhoneNumber());
        log.info("Source: {}", policy);
        log.info("Before diff: {}", policyChangeApplication);
        log.info("After diff: {}", diffed);
    }

    @Test
    void testDiffObjects_whenAllFieldsAreTheSame_thenAllFieldsInDiffedShouldBeNull() {
        val policyChangeApplication = parse("/test-datasets/policy-change-application-02.json",
                PolicyChangeApplication.class);
        val policy = parse("/test-datasets/policy-02.json", Policy.class);
        val diffed = assertDoesNotThrow(() -> diffObjects(policy, policyChangeApplication));
        val nonNullFields = countNonNullFields(diffed, emptySet());
        assertEquals(3, nonNullFields);
        assertNull(diffed.getPolicyholder().getIdNumber());
        assertNull(diffed.getPolicyholder().getEmail().stream().findFirst().orElseThrow().getEmail());
        assertNull(diffed.getPolicyholder().getMobilePhone().stream().findFirst().orElseThrow().getCountryCode());
        assertNull(diffed.getPolicyholder().getMobilePhone().stream().findFirst().orElseThrow().getPhoneNumber());
        log.info("Source: {}", policy);
        log.info("Before diff: {}", policyChangeApplication);
        log.info("After diff: {}", diffed);
    }

    @Test
    void testDiffObjects_whenIdTypeIsNotTheSame_thenIdTypeShouldBeReturned() {
        val policyChangeApplication = parse("/test-datasets/policy-change-application-03.json",
                PolicyChangeApplication.class);
        val policy = parse("/test-datasets/policy-03.json", Policy.class);
        val diffed = assertDoesNotThrow(() -> diffObjects(policy, policyChangeApplication));
        val nonNullFields = countNonNullFields(diffed, emptySet());
        assertEquals(3, nonNullFields);
        assertNotNull(diffed.getPolicyholder().getIdType());
        assertNotNull(diffed.getPolicyholder().getIdNumber());
        assertNull(diffed.getPolicyholder().getEmail().stream().findFirst().orElseThrow().getEmail());
        assertNull(diffed.getPolicyholder().getMobilePhone().stream().findFirst().orElseThrow().getCountryCode());
        assertNull(diffed.getPolicyholder().getMobilePhone().stream().findFirst().orElseThrow().getPhoneNumber());
        log.info("Source: {}", policy);
        log.info("Before diff: {}", policyChangeApplication);
        log.info("After diff: {}", diffed);
    }
}
