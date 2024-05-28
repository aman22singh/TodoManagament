package com.example.todo.service;

import com.example.todo.dto.TodoDto;

import java.util.List;

public interface TodoService {
    TodoDto addTodo(TodoDto todoDto);
    TodoDto getTodoByTitle(Long id);
    List<TodoDto> getAllTodos();
    TodoDto updateTodo(Long todoId, TodoDto todoDto);
    void deleteTodo(Long todoId);
    TodoDto completeTodo(Long todoId);
    TodoDto inCompleteTodo(Long todoId);


}
