import java.util.*;
import java.util.stream.Collectors;

public class Collectionstreams {

    record Employee(String name, String department, double salary) {}

    public static void main(String[] args) {

        // ============ LIST ============
        List<String> names = new ArrayList<>(List.of("Rahul", "Priya", "Aman", "Neha"));
        names.add("Karan");
        names.remove("Aman");
        System.out.println("List: " + names);

        // ============ SET (no duplicates) ============
        Set<Integer> uniqueNumbers = new HashSet<>(List.of(1, 2, 2, 3, 3, 3, 4));
        System.out.println("Set: " + uniqueNumbers);

        // ============ MAP (key-value pairs) ============
        Map<String, Integer> ageMap = new HashMap<>();
        ageMap.put("Rahul", 22);
        ageMap.put("Priya", 24);
        ageMap.put("Aman", 23);
        for (Map.Entry<String, Integer> entry : ageMap.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }

        // ============ ITERATOR ============
        Iterator<String> it = names.iterator();
        while (it.hasNext()) {
            String n = it.next();
            if (n.equals("Karan")) it.remove(); // safe removal during iteration
        }
        System.out.println("After iterator removal: " + names);

        // ============ SAMPLE DATA FOR STREAMS ============
        List<Employee> employees = List.of(
                new Employee("Rahul", "Engineering", 75000),
                new Employee("Priya", "Engineering", 82000),
                new Employee("Aman", "Sales", 60000),
                new Employee("Neha", "Sales", 65000),
                new Employee("Karan", "HR", 55000)
        );

        // --- Filtering ---
        List<Employee> highEarners = employees.stream()
                .filter(e -> e.salary() > 60000)
                .collect(Collectors.toList());
        System.out.println("High earners: " + highEarners);

        // --- Sorting ---
        List<Employee> bySalaryDesc = employees.stream()
                .sorted(Comparator.comparingDouble(Employee::salary).reversed())
                .collect(Collectors.toList());
        System.out.println("Sorted by salary desc: " + bySalaryDesc);

        // --- Mapping ---
        List<String> employeeNames = employees.stream()
                .map(Employee::name)
                .collect(Collectors.toList());
        System.out.println("Names only: " + employeeNames);

        // --- Reducing ---
        double totalPayroll = employees.stream()
                .mapToDouble(Employee::salary)
                .sum();
        System.out.println("Total payroll: " + totalPayroll);

        double avgSalary = employees.stream()
                .mapToDouble(Employee::salary)
                .average()
                .orElse(0);
        System.out.println("Average salary: " + avgSalary);

        // --- Collecting into groups ---
        Map<String, List<Employee>> byDept = employees.stream()
                .collect(Collectors.groupingBy(Employee::department));
        System.out.println("Grouped by department: " + byDept);

        Map<String, Double> avgSalaryByDept = employees.stream()
                .collect(Collectors.groupingBy(Employee::department,
                        Collectors.averagingDouble(Employee::salary)));
        System.out.println("Avg salary by department: " + avgSalaryByDept);

        // --- Chained functional pipeline ---
        String topEngineeringNames = employees.stream()
                .filter(e -> e.department().equals("Engineering"))
                .sorted(Comparator.comparingDouble(Employee::salary).reversed())
                .map(Employee::name)
                .collect(Collectors.joining(", "));
        System.out.println("Engineering (highest paid first): " + topEngineeringNames);
    }
}