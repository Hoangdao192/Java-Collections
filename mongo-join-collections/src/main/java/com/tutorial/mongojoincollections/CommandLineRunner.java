package com.tutorial.mongojoincollections;

import com.tutorial.mongojoincollections.entity.Task;
import com.tutorial.mongojoincollections.repository.TaskRepository;
import com.tutorial.mongojoincollections.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CommandLineRunner implements org.springframework.boot.CommandLineRunner {

    private final TaskService taskService;
    private final TaskRepository taskRepository;

    @Override
    public void run(String... args) throws Exception {
        List<Task> tasks = taskService.findAll(
                null, null, null,
                null, null, null, null
        );

        taskRepository.saveAll(tasks);
        System.out.println(tasks.size());
    }
}
