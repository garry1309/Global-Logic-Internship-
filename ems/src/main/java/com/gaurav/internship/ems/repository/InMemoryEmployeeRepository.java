package com.gaurav.internship.ems.repository;

import com.gaurav.internship.ems.model.Employee;
import java.util.*;


public class InMemoryEmployeeRepository implements EmployeeRepository {

    private final Map<Integer, Employee> storage = new HashMap<>();

    @Override
    public void save(Employee employee) {
        storage.put(employee.getId(), employee);
    }

    @Override
    public Optional<Employee> findById(int id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Employee> findAll() {
        return new ArrayList<>(storage.values());
    }
}
