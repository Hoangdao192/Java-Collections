package com.tutorial.mongojoincollections.service;

import com.tutorial.mongojoincollections.entity.Task;
import com.tutorial.mongojoincollections.repository.TaskRepository;
import com.tutorial.mongojoincollections.util.MongoAggregationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final MongoTemplate mongoTemplate;

    public List<Task> findAll(
            String enterpriseName, String assignerName, String assigneeName,
            Integer page, Integer size, String sortBy, String order
    ) {
        List<AggregationOperation> operations = new ArrayList<>();
        operations.addAll(
                MongoAggregationUtil.joinAggregate(
                        "enterpriseId",
                        "enterprise",
                        "_id",
                        "enterprise"
                )
        );
        operations.addAll(
                MongoAggregationUtil.joinAggregate(
                        "assignerId",
                        "user",
                        "_id",
                        "assigner"
                )
        );
        operations.addAll(
                MongoAggregationUtil.joinAggregate(
                        "assigneeId",
                        "user",
                        "_id",
                        "assignee"
                )
        );

        if (page != null && size != null) {
            operations.add(new SkipOperation((long) page * size));
            operations.add(new LimitOperation(size));
        }

        Criteria criteria = new Criteria();
        if (enterpriseName != null) {
            criteria.and("enterprise.name").regex(enterpriseName, "i");
        }
        if (assigneeName != null) {
            criteria.and("assignee.name").regex(assigneeName, "i");
        }
        if (assignerName != null) {
            criteria.and("assigner.name").regex(assignerName, "i");
        }
        operations.add(new MatchOperation(criteria));

        Aggregation aggregation = Aggregation.newAggregation(operations);
        System.out.println(aggregation.getPipeline().toString());
        AggregationResults<Task> results = mongoTemplate.aggregate(
                aggregation, "task", Task.class
        );
        return results.getMappedResults();
    }

}
