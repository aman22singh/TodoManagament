package com.example.todo.controller;

import com.example.todo.dto.TodoDto;

import com.example.todo.service.TodoService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@AllArgsConstructor
@RequestMapping("/api/todos/")
public class TodoController {

    private TodoService todoService;

    //Build Create TODO REST API
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<TodoDto> addTodo(@RequestBody TodoDto todoDto){
        TodoDto savedDto = todoService.addTodo(todoDto);
        return new ResponseEntity<>(savedDto, HttpStatus.CREATED);
    }

    //Build GET TODO by ID REST API
    @PreAuthorize("permitAll()")
    @GetMapping("{id}")
    public ResponseEntity<TodoDto> getTodoByTitle(@PathVariable("id") Long todoId){
        TodoDto receivedDto = todoService.getTodoByTitle(todoId);
        return ResponseEntity.ok(receivedDto);
    }

    //Build Get all TODO REST API
    @PreAuthorize("permitAll()")
    @GetMapping
    public ResponseEntity<List<TodoDto>> getAllTodos(){
        List<TodoDto> allTodos = todoService.getAllTodos();
        return ResponseEntity.ok(allTodos);
    }

    //Build Update TODO REST API
    @PreAuthorize("hasRole('ADMIN)")
    @PutMapping("{id}")
    public ResponseEntity<TodoDto> updateTodo(@PathVariable("id") Long todoId, @RequestBody TodoDto todoDto){
        TodoDto updatedTodo = todoService.updateTodo(todoId, todoDto);
        return ResponseEntity.ok(updatedTodo);
    }

    //Build Delete TODO REST API
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteTodo(@PathVariable("id") Long todoId){
        todoService.deleteTodo(todoId);
        return ResponseEntity.ok("Todo Deleted Successfully");
    }

    //Build Complete TODO REST API
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PatchMapping("{id}/complete")
    public ResponseEntity<TodoDto> completeTodo(@PathVariable("id") Long todoId){
        TodoDto updatedTodo = todoService.completeTodo(todoId);
        return ResponseEntity.ok(updatedTodo);
    }

    //Build InComplete TODO REST API
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PatchMapping("{id}/incomplete")
    public ResponseEntity<TodoDto> inComplete(@PathVariable("id") Long todoId){
        TodoDto updatedTodo = todoService.inCompleteTodo(todoId);
        return ResponseEntity.ok(updatedTodo);
    }
}
