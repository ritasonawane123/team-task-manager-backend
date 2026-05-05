package com.example.taskmanager.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.example.taskmanager.entity.Project;
import com.example.taskmanager.repository.ProjectRepository;
import jakarta.validation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/projects")


public class ProjectController {
	
	@Autowired
    private ProjectRepository repo;

//    @PostMapping
//    public Project create(@RequestBody Project p) {
//    	System.out.println("Project name: " + p.getName());
//        return repo.save(p);
//    }

    @GetMapping
    public List<Project> getAll() {
        return repo.findAll();
    }
    
    @PostMapping
    public Project create(@Valid @RequestBody Project p, @RequestParam String role) {

        if (!"ADMIN".equalsIgnoreCase(role)) {
            throw new RuntimeException("Only ADMIN can create project");
        }

        return repo.save(p);
    }

}
