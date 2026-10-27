package com.gaurav.internship.employeeapi;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Starts the real Spring context and sends real HTTP-style requests through MockMvc.
// Each test uses its own e-mail address because the in-memory data is shared
// between tests in the same run.
@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static String json(String name, String email, String department, String salary) {
        return """
                {"name":"%s","email":"%s","department":"%s","salary":%s}
                """.formatted(name, email, department, salary);
    }

    private long createEmployee(String email) throws Exception {
        MvcResult result = mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json("Test User", email, "Engineering", "50000")))
                .andExpect(status().isCreated())
                .andReturn();
        return ((Number) JsonPath.read(result.getResponse().getContentAsString(), "$.id")).longValue();
    }

    @Test
    void addEmployee_returns201WithLocationAndBody() throws Exception {
        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json("Gaurav", "add@example.com", "Engineering", "55000")))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Gaurav"))
                .andExpect(jsonPath("$.email").value("add@example.com"));
    }

    @Test
    void addEmployee_withInvalidData_returns400WithFieldErrors() throws Exception {
        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json("", "not-an-email", "Engineering", "-5")))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.fieldErrors.name").exists())
                .andExpect(jsonPath("$.fieldErrors.email").exists())
                .andExpect(jsonPath("$.fieldErrors.salary").exists());
    }

    @Test
    void addEmployee_withDuplicateEmail_returns409() throws Exception {
        createEmployee("duplicate@example.com");

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json("Other", "duplicate@example.com", "HR", "40000")))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value(containsString("already exists")));
    }

    @Test
    void getEmployee_whenMissing_returns404() throws Exception {
        mockMvc.perform(get("/api/employees/99999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value(containsString("99999")));
    }

    @Test
    void getEmployee_withNonNumericId_returns400() throws Exception {
        mockMvc.perform(get("/api/employees/abc"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void fullCrudFlow_createReadUpdateDelete() throws Exception {
        long id = createEmployee("flow@example.com");

        // READ one
        mockMvc.perform(get("/api/employees/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("flow@example.com"));

        // READ all (list contains at least this employee)
        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.id == " + id + ")]").exists());

        // UPDATE
        mockMvc.perform(put("/api/employees/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json("Updated Name", "flow@example.com", "Finance", "70000")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Name"))
                .andExpect(jsonPath("$.department").value("Finance"));

        // DELETE
        mockMvc.perform(delete("/api/employees/" + id))
                .andExpect(status().isNoContent());

        // Gone
        mockMvc.perform(get("/api/employees/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateEmployee_whenMissing_returns404() throws Exception {
        mockMvc.perform(put("/api/employees/88888")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json("Nobody", "nobody@example.com", "HR", "30000")))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteEmployee_whenMissing_returns404() throws Exception {
        mockMvc.perform(delete("/api/employees/77777"))
                .andExpect(status().isNotFound());
    }
}
