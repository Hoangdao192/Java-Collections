package com.tutorial.mongojoincollections.util;

import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.ConvertOperators;

import java.util.ArrayList;
import java.util.List;

public class MongoAggregationUtil {

    /**
     *
     * @param localStringIdField The id that connect two collection represented as string
     * @param targetCollection Join collection name
     * @param foreignField Field that match localStringIdField in join collection
     * @param valueName After join, a value be created to represent the document in target collection
     * @return List of operation for process joining.
     */
    public static List<AggregationOperation> joinAggregate(
            String localStringIdField,
            String targetCollection,
            String foreignField,
            String valueName
    ) {
        List<AggregationOperation> operations = new ArrayList<>();
        String convertedIdFieldName = localStringIdField + "_" + System.currentTimeMillis();
        operations.add(Aggregation.addFields().addFieldWithValue(
                convertedIdFieldName,
                ConvertOperators.ToObjectId.toObjectId("$" + localStringIdField)
        ).build());
        operations.add(Aggregation.lookup(
                targetCollection, convertedIdFieldName, foreignField, valueName
        ));
        operations.add(Aggregation.unwind(valueName));
        return operations;
    }

}
