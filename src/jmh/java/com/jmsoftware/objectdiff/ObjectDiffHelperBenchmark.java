package com.jmsoftware.objectdiff;

import com.jmsoftware.objectdiff.samplemodel.policy.Policy;
import com.jmsoftware.objectdiff.samplemodel.policychange.PolicyChangeApplication;
import lombok.val;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

import static com.jmsoftware.objectdiff.ObjectDiffHelper.diffObjects;
import static com.jmsoftware.objectdiff.UnitTestHelper.parse;

/**
 * <h1>ObjectDiffHelperBenchmark</h1>
 * <p>
 * Change description here.
 *
 * @author Johnny Miller (鍾俊), email: johnnysviva@outlook.com, 6/10/23 10:19 AM
 **/
@State(Scope.Benchmark)
public class ObjectDiffHelperBenchmark {
    @Benchmark
    public void diffObjects_whenOnlyOneFieldIsDifferent_thenOnlyOneFieldInDiffedObjectIsNonNull() {
        val policyChangeApplication = parse("/test-datasets/policy-change-application-01.json",
                PolicyChangeApplication.class);
        val policy = parse("/test-datasets/policy-01.json", Policy.class);
        diffObjects(policy, policyChangeApplication);
    }

    @Benchmark
    public void diffObjects_whenAllFieldsAreTheSame_thenAllFieldsInDiffedShouldBeNull() {
        val policyChangeApplication = parse("/test-datasets/policy-change-application-02.json",
                PolicyChangeApplication.class);
        val policy = parse("/test-datasets/policy-02.json", Policy.class);
        diffObjects(policy, policyChangeApplication);
    }

    @Benchmark
    public void diffObjects_whenIdTypeIsNotTheSame_thenIdTypeShouldBeReturned() {
        val policyChangeApplication = parse("/test-datasets/policy-change-application-03.json",
                PolicyChangeApplication.class);
        val policy = parse("/test-datasets/policy-03.json", Policy.class);
        diffObjects(policy, policyChangeApplication);
    }
}
