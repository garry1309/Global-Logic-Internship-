package com.gaurav.internship.employeeapi.service;

import com.gaurav.internship.employeeapi.dto.EmployeeRequest;
import com.gaurav.internship.employeeapi.dto.EmployeeResponse;
import com.gaurav.internship.employeeapi.exception.DuplicateEmailException;
import com.gaurav.internship.employeeapi.exception.EmployeeNotFoundException;
import com.gaurav.internship.employeeapi.mapper.EmployeeMapper;
import com.gaurav.internship.employeeapi.model.Employee;
import com.gaurav.internship.employeeapi.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

// @Service makes this a Spring bean. DEPENDENCY INJECTION: Spring sees the
// constructor needs an EmployeeRepository and an EmployeeMapper, finds those beans,
// and passes them in. The class never calls "new" on its collaborators.
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;
    private final EmployeeMapper mapper;

    public EmployeeServiceImpl(EmployeeRepository repository, EmployeeMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public EmployeeResponse create(EmployeeRequest request) {
        repository.findByEmail(request.email()).ifPresent(existing -> {
            throw new DuplicateEmailException(request.email());
        });
        Employee saved = repository.save(mapper.toEntity(request));
        return mapper.toResponse(saved);
    }

    @Override
    public EmployeeResponse update(Long id, EmployeeRequest request) {
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        // The email may stay the same, but it must not belong to a DIFFERENT employee.
        repository.findByEmail(request.email())
                .filter(other -> !other.getId().equals(id))
                .ifPresent(other -> {
                    throw new DuplicateEmailException(request.email());
                });

        mapper.updateEntity(employee, request);
        return mapper.toResponse(repository.save(employee));
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EmployeeNotFoundException(id);
        }
        repository.deleteById(id);
    }

    @Override
    public EmployeeResponse getById(Long id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    @Override
    public List<EmployeeResponse> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }
}
