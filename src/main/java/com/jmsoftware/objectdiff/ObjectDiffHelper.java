package com.jmsoftware.objectdiff;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import static java.lang.Integer.toHexString;
import static java.lang.String.format;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * <h1>ObjectDiffHelper</h1>
 * <p>
 * Change description here.
 *
 * @author Johnny Miller (鍾俊), email: johnnysviva@outlook.com, 6/9/23 10:15 PM
 **/
@Slf4j
public class ObjectDiffHelper {
    /**
     * Diff 2 objects. the {@link TARGET} object has to be annotated with {@link ClassMapping} and {@link FieldMapping}
     *
     * @param <SOURCE>     the type parameter for the Source object
     * @param <TARGET>     the type parameter for the Target object
     * @param sourceObject the source object
     * @param targetObject the target object
     * @return a new instance of target's type
     */
    @SneakyThrows
    public static <SOURCE, TARGET> TARGET diffObjects(SOURCE sourceObject, TARGET targetObject) {
        if (ObjectUtils.anyNull(sourceObject, targetObject)) {
            return targetObject;
        }
        val targetFields = FieldUtils.getAllFields(targetObject.getClass());
        @SuppressWarnings("unchecked") val newTargetInstance = (TARGET) targetObject.getClass().newInstance();
        log.warn("Created a new target instance: {}@{}", newTargetInstance.getClass().getName(),
                toHexString(newTargetInstance.hashCode()));
        // FIXME: currently we don't support primitive array, Map
        if (targetObject instanceof Collection) {
            compareAndWriteTargetCollection(sourceObject, targetObject, newTargetInstance);
            return newTargetInstance;
        }
        compareAndWrite(sourceObject, targetObject, targetFields, newTargetInstance);
        return newTargetInstance;
    }

    @SneakyThrows
    @SuppressWarnings("ConstantValue")
    private static <SOURCE, TARGET> void compareAndWrite(
            SOURCE sourceObject,
            TARGET targetObject,
            Field[] targetFields,
            TARGET newTargetInstance
    ) {
        for (val targetField : targetFields) {
            val targetFieldValue = FieldUtils.readField(targetField, targetObject, true);
            val mapping = targetField.getAnnotation(FieldMapping.class);
            if (isNull(mapping)) {
                handleMissingAnnotation(targetField, newTargetInstance, targetFieldValue);
                continue;
            }
            val sourceFieldName = mapping.source();
            val sourceFieldValue = readField(sourceObject, sourceFieldName);
            if (!mapping.isSourceNested() && !mapping.needUnwrapping()) {
                compareNonNestedFields(targetField, sourceFieldValue, targetFieldValue, newTargetInstance, mapping);
            } else if (!mapping.isSourceNested() && mapping.needUnwrapping()) {
                compareNonNestedFieldsWithUnwrapping(
                        targetField,
                        sourceFieldValue,
                        targetFieldValue,
                        newTargetInstance,
                        mapping
                );
            } else {
                compareNestedFields(
                        targetField,
                        sourceObject,
                        targetObject,
                        newTargetInstance,
                        mapping
                );
            }
        }
    }

    private static <TARGET> void handleMissingAnnotation(
            Field targetField,
            TARGET newTargetInstance,
            Object targetFieldValue
    ) {
        log.info("Writing the original target value, because the target field <{}> {} was not annotated " +
                        "with @{}", targetField.getType().getSimpleName(), targetField.getName(),
                FieldMapping.class.getSimpleName());
        try {
            FieldUtils.writeField(targetField, newTargetInstance, targetFieldValue);
        } catch (IllegalAccessException e) {
            log.warn("Could not write the original target value, {}", ExceptionUtils.getRootCauseMessage(e));
        }
    }

    @SneakyThrows
    private static <TARGET> void compareNonNestedFields(
            Field targetField,
            Object sourceFieldValue,
            Object targetFieldValue,
            TARGET newTargetInstance,
            FieldMapping mapping
    ) {
        log.info("The source field is not nested without unwrapping. sourceFieldName: {}", mapping.source());
        val equaled = compareFieldValues(sourceFieldValue, targetFieldValue);
        if (equaled) {
            log.info("Not writing new target instance's field due to fields' equality: <{}> {}, " +
                            "sourceFieldValue: {} = targetFieldValue: {}", targetField.getType().getSimpleName(),
                    targetField.getName(), sourceFieldValue, targetFieldValue);
            return;
        }
        log.warn("Writing new target instance's field due to fields' inequality: <{}> {}, " +
                        "sourceFieldValue: {} != targetFieldValue: {}",
                targetField.getType().getSimpleName(), targetField.getName(), sourceFieldValue,
                targetFieldValue);
        FieldUtils.writeField(targetField, newTargetInstance, targetFieldValue, true);
    }

    @SneakyThrows
    private static <TARGET> void compareNonNestedFieldsWithUnwrapping(
            Field targetField,
            Object sourceFieldValue,
            Object targetFieldValue,
            TARGET newTargetInstance,
            FieldMapping mapping
    ) {
        log.info("The source field is not nested and needs unwrapping. sourceFieldName: {}", mapping.source());
        val invokeResult = MethodUtils.invokeMethod(targetFieldValue, mapping.targetMethodName());
        log.info("Invoked {}.{}(), result: {}", targetField.getName(), mapping.targetMethodName(), invokeResult);
        val equaled = compareFieldValues(sourceFieldValue, invokeResult);
        if (equaled) {
            log.info("Not writing new target instance's field due to fields' equality: <{}> {}, " +
                            "sourceFieldValue: {} = invokeResult: {}", targetField.getType().getSimpleName(),
                    targetField.getName(), sourceFieldValue, invokeResult);
            return;
        }
        log.warn("Writing new target instance's field due to fields' inequality: <{}> {}, " +
                        "sourceFieldValue: {} != invokeResult: {}",
                targetField.getType().getSimpleName(), targetField.getName(), sourceFieldValue,
                invokeResult);
        FieldUtils.writeField(targetField, newTargetInstance, targetFieldValue, true);
    }

    @SneakyThrows
    private static <SOURCE, TARGET> void compareNestedFields(
            Field targetField,
            SOURCE sourceObject,
            TARGET targetObject,
            TARGET newTargetInstance,
            FieldMapping mapping
    ) {
        log.warn("The source field is nested. Going to compare with the source field: <{}> {}.{}",
                mapping.sourceClass().getSimpleName(), sourceObject.getClass().getSimpleName(), mapping.source());
        val nestedComparedObject = diffObjects(
                FieldUtils.readField(sourceObject, mapping.source(), true),
                FieldUtils.readField(targetField, targetObject, true)
        );
        FieldUtils.writeField(targetField, newTargetInstance, nestedComparedObject);
    }

    private static boolean compareFieldValues(Object value1, Object value2) {
        return Objects.equals(value1, value2);
    }

    private static <SOURCE, TARGET> void compareAndWriteTargetCollection(
            SOURCE sourceObject,
            TARGET targetObject,
            TARGET newTargetInstance
    ) {
        log.warn("Comparing Collection instances, sourceObject: {}, targetObject: {}", sourceObject, targetObject);
        ((Collection<?>) targetObject).forEach(item -> {
            val compared = diffObjects(sourceObject, item);
            //noinspection unchecked,rawtypes
            ((Collection) newTargetInstance).add(compared);
            log.warn("Added new compared into newTargetInstance: {}", newTargetInstance);
        });
    }

    @SneakyThrows
    private static Object readField(Object object, String fieldName) {
        val fields = fieldName.split("\\.");
        if (ArrayUtils.isEmpty(fields)) {
            throw new IllegalArgumentException(format("Cannot parse field name: %s", fieldName));
        }
        Object fieldValue = object;
        for (val field : fields) {
            fieldValue = FieldUtils.readField(fieldValue, field, true);
        }
        return fieldValue;
    }

    /**
     * Invoke fieldName's getter of the object.
     *
     * @param object    the object
     * @param fieldName the field name
     * @return the value of the field
     */
    private static Object invokeGetter(Object object, String fieldName) {
        try {
            val value = FieldUtils.readField(object, fieldName, true);
            log.debug("Done invoking getter, {}.{} = {}", object.getClass().getSimpleName(), fieldName, value);
            return value;
        } catch (IllegalArgumentException | IllegalAccessException e) {
            log.warn("Exception occurred when invoking getter [{}.{}], exception message: {}", object, fieldName,
                    e.getMessage());
            return null;
        }
    }

    /**
     * Count non-null fields.
     *
     * @param object the object
     * @return the long
     */
    public static long countNonNullFields(Object object, Set<String> ignoredFieldNames) {
        return Stream.of(FieldUtils.getAllFields(object.getClass()))
                // WARNING: ignored injected field by testing framework or custom fields
                .filter(field -> !ignoredFieldNames.contains(field.getName()))
                .filter(field -> {
                    val value = invokeGetter(object, field.getName());
                    val nonNull = nonNull(value);
                    if (nonNull) {
                        log.debug("Got non-null field: {} -> {}", field.getName(), value);
                    }
                    return nonNull;
                })
                .count();
    }
}
