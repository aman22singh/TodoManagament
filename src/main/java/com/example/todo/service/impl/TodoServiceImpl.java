package com.example.todo.service.impl;

import com.example.todo.dto.TodoDto;
import com.example.todo.entity.Todo;
import com.example.todo.exception.ResourceNotFoundException;
import com.example.todo.repository.TodoRepository;
import com.example.todo.service.TodoService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TodoServiceImpl implements TodoService {

    private ModelMapper modelMapper;

    private TodoRepository todoRepository;

    @Override
    public TodoDto addTodo(TodoDto todoDto) {
        //convert TodoDto in todo Jpa entity
        Todo todo = modelMapper.map(todoDto, Todo.class);

        //save todo jpa entity
        Todo savedTodo = todoRepository.save(todo);

        //convert todo entity to tododto
        TodoDto savedTodoDto = modelMapper.map(savedTodo, TodoDto.class);
        return savedTodoDto;
    }

    @Override
    public TodoDto getTodoByTitle(Long todoId) {
        Todo receivedTodo = todoRepository.findById(todoId)
                        .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id: "+todoId));
        TodoDto todoDto = modelMapper.map(receivedTodo, TodoDto.class);
        return todoDto;
    }

    @Override
    public List<TodoDto> getAllTodos() {
        List<Todo> allTodos = todoRepository.findAll();

        return allTodos.stream().map((todo) -> modelMapper.map(todo, TodoDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public TodoDto updateTodo(Long todoId, TodoDto todoDto) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id: "+todoId));

        todo.setTitle(todoDto.getTitle());
        todo.setDescription(todoDto.getDescription());
        todo.setCompleted(todoDto.getCompleted());

        Todo updatedTodo = todoRepository.save(todo);
        return modelMapper.map(updatedTodo, TodoDto.class);
    }

    @Override
    public void deleteTodo(Long todoId) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new ResourceNotFoundException("Todo does not exist with id:"+ todoId));
        todoRepository.delete(todo);
    }

    @Override
    public TodoDto completeTodo(Long todoId) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new ResourceNotFoundException("Todo does not exist with id: "+todoId));
        todo.setCompleted(Boolean.TRUE);
        Todo updatedTodo = todoRepository.save(todo);
        return modelMapper.map(todo, TodoDto.class);
    }

    @Override
    public TodoDto inCompleteTodo(Long todoId) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new ResourceNotFoundException("Todo does not exist with id: "+todoId));

        todo.setCompleted(Boolean.FALSE);
        return modelMapper.map(todo, TodoDto.class);
    }
}
