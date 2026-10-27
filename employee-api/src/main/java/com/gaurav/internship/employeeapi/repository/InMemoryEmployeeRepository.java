package com.gaurav.internship.employeeapi.repository;

import com.gaurav.internship.employeeapi.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

// @Repository registers this class as a Spring bean. Data lives in a map, so it
// is lost when the application stops (as the assignment requires: in-memory storage).
// ConcurrentHashMap + AtomicLong keep it safe when several requests arrive at once.
@Repository
public class InMemoryEmployeeRepository implements EmployeeRepository {

    private final Map<Long, Employee> store = new ConcurrentHashMap<>();
    private final AtomicLong idSequence = new AtomicLong(0);

    @Override
    public Employee save(Employee employee) {
        if (employee.getId() == null) {
            employee.setId(idSequence.incrementAndGet());
        }
        store.put(employee.getId(), employee);
        return employee;
    }

    @Override
    public Optional<Employee> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Employee> findByEmail(String email) {
        return store.values().stream()
                .filter(e -> e.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    @Override
    public List<Employee> findAll() {
        return store.values().stream()
                .sorted(Comparator.comparing(Employee::getId))
                .toList();
    }

    @Override
    public boolean existsById(Long id) {
        return store.containsKey(id);
    }

    @Override
    public void deleteById(Long id) {
        store.remove(id);
    }
}
