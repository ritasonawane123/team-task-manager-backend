package com.example.taskmanager.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.taskmanager.entity.Task;
import com.example.taskmanager.repository.TaskRepository;

import jakarta.validation.*;
@CrossOrigin("*")
@RestController
@RequestMapping("/tasks")


public class TaskController {
	
	 @Autowired
	    private TaskRepository repo;

	    @PostMapping
	    public Task create(@Valid @RequestBody Task task) {
	        return repo.save(task);
	    }

	    @GetMapping
	    public List<Task> getAll() {
	        return repo.findAll();
	    }

	    @PutMapping("/{id}")
	    public Task updateStatus(@PathVariable Long id, @RequestBody Task updated) {
	        Task task = repo.findById(id).orElseThrow();
	        task.setStatus(updated.getStatus());
	        return repo.save(task);
	    }
	    
	    @GetMapping("/dashboard")
	    public Map<String, Long> dashboard() {
	        List<Task> tasks = repo.findAll();

	        long total = tasks.size();
	        long completed = tasks.stream()
	                .filter(t -> "DONE".equalsIgnoreCase(t.getStatus()))
	                .count();

	        long pending = tasks.stream()
	                .filter(t -> "TODO".equalsIgnoreCase(t.getStatus()))
	                .count();

	        long overdue = tasks.stream()
	                .filter(t -> t.getDueDate() != null &&
	                        t.getDueDate().isBefore(LocalDate.now()) &&
	                        !"DONE".equalsIgnoreCase(t.getStatus()))
	                .count();

	        Map<String, Long> result = new HashMap<>();
	        result.put("total", total);
	        result.put("completed", completed);
	        result.put("pending", pending);
	        result.put("overdue", overdue);

	        return result;
	    }

}
