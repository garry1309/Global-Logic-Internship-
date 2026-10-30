DROP DATABASE IF EXISTS student_management;
CREATE DATABASE student_management
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;
USE student_management;

CREATE TABLE Student (
    student_id      INT            NOT NULL AUTO_INCREMENT,
    first_name      VARCHAR(50)    NOT NULL,
    last_name       VARCHAR(50)    NOT NULL,
    email           VARCHAR(100)   NOT NULL,
    date_of_birth   DATE           NOT NULL,
    enrollment_date DATE           NOT NULL,
    CONSTRAINT pk_student       PRIMARY KEY (student_id),
    CONSTRAINT uq_student_email UNIQUE (email)
);


CREATE TABLE Course (
    course_id    INT            NOT NULL AUTO_INCREMENT,
    course_name  VARCHAR(100)   NOT NULL,
    course_code  VARCHAR(10)    NOT NULL,
    credits      INT            NOT NULL,
    CONSTRAINT pk_course        PRIMARY KEY (course_id),
    CONSTRAINT uq_course_code   UNIQUE (course_code),
    CONSTRAINT chk_course_credits CHECK (credits BETWEEN 1 AND 6)
);


CREATE TABLE Enrollment (
    enrollment_id   INT            NOT NULL AUTO_INCREMENT,
    student_id      INT            NOT NULL,
    course_id       INT            NOT NULL,
    enrollment_date DATE           NOT NULL,
    grade           DECIMAL(5,2)   NULL,
    CONSTRAINT pk_enrollment         PRIMARY KEY (enrollment_id),
    CONSTRAINT fk_enrollment_student FOREIGN KEY (student_id)
        REFERENCES Student (student_id) ON DELETE CASCADE,
    CONSTRAINT fk_enrollment_course  FOREIGN KEY (course_id)
        REFERENCES Course (course_id)   ON DELETE CASCADE,
    
    CONSTRAINT uq_student_course     UNIQUE (student_id, course_id),
    CONSTRAINT chk_enrollment_grade  CHECK (grade IS NULL OR grade BETWEEN 0 AND 100)
);




INSERT INTO Student (first_name, last_name, email, date_of_birth, enrollment_date) VALUES
    ('Aarav',  'Sharma', 'aarav.sharma@example.com',  '2003-04-12', '2023-07-15'),
    ('Priya',  'Verma',  'priya.verma@example.com',   '2002-11-05', '2023-07-15'),
    ('Rohan',  'Mehta',  'rohan.mehta@example.com',   '2003-01-23', '2023-07-20'),
    ('Ananya', 'Singh',  'ananya.singh@example.com',  '2004-03-17', '2024-07-10'),
    ('Karan',  'Patel',  'karan.patel@example.com',   '2002-08-30', '2022-07-18'),
    ('Sneha',  'Iyer',   'sneha.iyer@example.com',    '2003-06-09', '2023-07-22'),
    ('Vikram', 'Rao',    'vikram.rao@example.com',    '2001-12-14', '2022-07-18'),
    ('Neha',   'Gupta',  'neha.gupta@example.com',    '2004-02-27', '2024-07-12'),
    ('Arjun',  'Nair',   'arjun.nair@example.com',    '2003-09-19', '2023-07-25'),
    ('Divya',  'Reddy',  'divya.reddy@example.com',   '2002-05-02', '2022-07-20'),
    ('Rahul',  'Joshi',  'rahul.joshi@example.com',   '2004-10-11', '2024-07-15'),
    ('Meera',  'Kapoor', 'meera.kapoor@example.com',  '2003-07-07', '2024-07-15');


INSERT INTO Course (course_name, course_code, credits) VALUES
    ('Database Systems',   'DBS101', 4),
    ('Data Structures',    'DSA201', 4),
    ('Java Programming',   'JAV102', 3),
    ('Web Development',    'WEB202', 3),
    ('Operating Systems',  'OPS301', 4),
    ('Machine Learning',   'MLN401', 3),
    ('Cloud Computing',    'CLD302', 3);


INSERT INTO Enrollment (student_id, course_id, enrollment_date, grade) VALUES
    (1,  1, '2023-08-01', 88.50),
    (1,  3, '2023-08-01', 92.00),
    (1,  2, '2023-08-02', 79.00),
    (2,  1, '2023-08-01', 95.00),
    (2,  3, '2023-08-02', 85.50),
    (3,  1, '2023-08-03', 72.00),
    (3,  4, '2023-08-03', 68.50),
    (4,  2, '2024-08-05', 81.00),
    (4,  3, '2024-08-05', 90.00),
    (5,  1, '2022-08-01', 64.00),
    (5,  5, '2022-08-02', 70.50),
    (6,  3, '2023-08-04', 87.00),
    (6,  4, '2023-08-04', NULL),
    (7,  5, '2022-08-03', 91.00),
    (7,  2, '2022-08-03', 76.00),
    (8,  4, '2024-08-06', 83.50),
    (9,  2, '2023-08-05', 89.00),
    (9,  6, '2023-08-05', NULL),
    (10, 5, '2022-08-04', 58.00),
    (10, 6, '2022-08-04', 74.00);


SELECT * FROM Student;


SELECT s.student_id, s.first_name, s.last_name, s.email, e.grade
FROM Student s
INNER JOIN Enrollment e ON e.student_id = s.student_id
INNER JOIN Course c     ON c.course_id  = e.course_id
WHERE c.course_code = 'DBS101'
ORDER BY s.last_name;


SELECT student_id, email FROM Student WHERE student_id = 3;   

UPDATE Student
SET    email = 'rohan.mehta@university.edu'
WHERE  student_id = 3;

SELECT student_id, email FROM Student WHERE student_id = 3;   


SELECT COUNT(*) AS enrollments_before_delete FROM Enrollment;

DELETE FROM Enrollment
WHERE  student_id = 10 AND course_id = 6;

SELECT COUNT(*) AS enrollments_after_delete FROM Enrollment;


SELECT student_id, first_name, last_name, enrollment_date
FROM   Student
ORDER BY enrollment_date ASC, last_name ASC;



SELECT s.student_id,
       CONCAT(s.first_name, ' ', s.last_name) AS student_name,
       c.course_code,
       c.course_name
FROM Student s
INNER JOIN Enrollment e ON e.student_id = s.student_id
INNER JOIN Course c     ON c.course_id  = e.course_id
ORDER BY s.student_id, c.course_code;


SELECT s.student_id,
       CONCAT(s.first_name, ' ', s.last_name) AS student_name,
       GROUP_CONCAT(c.course_code ORDER BY c.course_code SEPARATOR ', ') AS courses
FROM Student s
INNER JOIN Enrollment e ON e.student_id = s.student_id
INNER JOIN Course c     ON c.course_id  = e.course_id
GROUP BY s.student_id, s.first_name, s.last_name
ORDER BY s.student_id;


SELECT c.course_code,
       c.course_name,
       COUNT(e.enrollment_id) AS students_enrolled
FROM Course c
LEFT JOIN Enrollment e ON e.course_id = c.course_id
GROUP BY c.course_id, c.course_code, c.course_name
ORDER BY students_enrolled DESC, c.course_code;


SELECT c.course_code,
       c.course_name,
       COUNT(e.enrollment_id) AS students_enrolled
FROM Course c
INNER JOIN Enrollment e ON e.course_id = c.course_id
GROUP BY c.course_id, c.course_code, c.course_name
HAVING COUNT(e.enrollment_id) > 2
ORDER BY students_enrolled DESC, c.course_code;


SELECT s.student_id, s.first_name, s.last_name, s.email
FROM Student s
LEFT JOIN Enrollment e ON e.student_id = s.student_id
WHERE e.enrollment_id IS NULL;


SELECT c.course_code,
       c.course_name,
       MAX(e.grade) AS highest_grade
FROM Course c
LEFT JOIN Enrollment e ON e.course_id = c.course_id
GROUP BY c.course_id, c.course_code, c.course_name
ORDER BY c.course_code;


SELECT c.course_code,
       CONCAT(s.first_name, ' ', s.last_name) AS top_student,
       e.grade
FROM Enrollment e
INNER JOIN Student s ON s.student_id = e.student_id
INNER JOIN Course c  ON c.course_id  = e.course_id
WHERE e.grade = (SELECT MAX(e2.grade)
                 FROM Enrollment e2
                 WHERE e2.course_id = e.course_id)
ORDER BY c.course_code;



CREATE OR REPLACE VIEW vw_student_course_details AS
SELECT CONCAT(s.first_name, ' ', s.last_name) AS student_name,
       c.course_name                          AS course_name,
       e.enrollment_date                      AS enrollment_date,
       e.grade                                AS grade
FROM Enrollment e
INNER JOIN Student s ON s.student_id = e.student_id
INNER JOIN Course c  ON c.course_id  = e.course_id;


SELECT * FROM vw_student_course_details
ORDER BY student_name, course_name;


SELECT * FROM vw_student_course_details
WHERE student_name = 'Aarav Sharma';


SELECT student_name, course_name, grade
FROM vw_student_course_details
WHERE grade >= 85
ORDER BY grade DESC;


SELECT course_name, ROUND(AVG(grade), 2) AS average_grade
FROM vw_student_course_details
GROUP BY course_name
ORDER BY average_grade DESC;


SELECT (SELECT COUNT(*) FROM vw_student_course_details) AS rows_in_view,
       (SELECT COUNT(*) FROM Enrollment)                AS rows_in_enrollment;


DROP FUNCTION IF EXISTS fn_student_course_count;

DELIMITER $$
CREATE FUNCTION fn_student_course_count(p_student_id INT)
RETURNS INT
READS SQL DATA
BEGIN
    DECLARE v_count INT;

    SELECT COUNT(*)
    INTO   v_count
    FROM   Enrollment
    WHERE  student_id = p_student_id;

    RETURN v_count;
END$$
DELIMITER ;


SELECT fn_student_course_count(1)   AS courses_of_student_1;     
SELECT fn_student_course_count(11)  AS courses_of_student_11;     
SELECT fn_student_course_count(999) AS courses_of_unknown_student; 


SELECT student_id,
       CONCAT(first_name, ' ', last_name)   AS student_name,
       fn_student_course_count(student_id)  AS total_courses
FROM   Student
ORDER BY total_courses DESC, student_id;


DROP PROCEDURE IF EXISTS sp_get_student_courses;
DROP PROCEDURE IF EXISTS sp_add_enrollment;

DELIMITER $$


CREATE PROCEDURE sp_get_student_courses(IN p_student_id INT)
BEGIN
    SELECT c.course_id,
           c.course_code,
           c.course_name,
           c.credits,
           e.enrollment_date,
           e.grade
    FROM   Enrollment e
    INNER JOIN Course c ON c.course_id = e.course_id
    WHERE  e.student_id = p_student_id
    ORDER BY c.course_code;
END$$


CREATE PROCEDURE sp_add_enrollment(IN p_student_id INT, IN p_course_id INT)
BEGIN
    IF NOT EXISTS (SELECT 1 FROM Student WHERE student_id = p_student_id) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Student does not exist';
    END IF;

    IF NOT EXISTS (SELECT 1 FROM Course WHERE course_id = p_course_id) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Course does not exist';
    END IF;

    IF EXISTS (SELECT 1 FROM Enrollment
               WHERE student_id = p_student_id AND course_id = p_course_id) THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Student is already enrolled in this course';
    END IF;

    INSERT INTO Enrollment (student_id, course_id, enrollment_date, grade)
    VALUES (p_student_id, p_course_id, CURDATE(), NULL);

    SELECT LAST_INSERT_ID() AS new_enrollment_id,
           'Enrollment created successfully' AS message;
END$$

DELIMITER ;

CALL sp_get_student_courses(1);
CALL sp_get_student_courses(11);     

CALL sp_get_student_courses(2);      
CALL sp_add_enrollment(2, 5);
CALL sp_get_student_courses(2);      


SELECT s.student_id,
       CONCAT(s.first_name, ' ', s.last_name) AS student_name,
       COUNT(e.enrollment_id)                 AS courses_enrolled
FROM Student s
INNER JOIN Enrollment e ON e.student_id = s.student_id
GROUP BY s.student_id, s.first_name, s.last_name
HAVING COUNT(e.enrollment_id) > 1
ORDER BY courses_enrolled DESC, s.student_id;


SELECT c.course_code,
       c.course_name,
       COUNT(e.enrollment_id) AS students_enrolled
FROM Course c
LEFT JOIN Enrollment e ON e.course_id = c.course_id
GROUP BY c.course_id, c.course_code, c.course_name
HAVING COUNT(e.enrollment_id) > (
        SELECT AVG(per_course.cnt)
        FROM (SELECT COUNT(e2.enrollment_id) AS cnt
              FROM Course c2
              LEFT JOIN Enrollment e2 ON e2.course_id = c2.course_id
              GROUP BY c2.course_id) AS per_course)
ORDER BY students_enrolled DESC, c.course_code;

SELECT s.student_id,
       CONCAT(s.first_name, ' ', s.last_name) AS student_name,
       ROUND(AVG(e.grade), 2)                 AS student_average,
       (SELECT ROUND(AVG(grade), 2) FROM Enrollment) AS overall_average
FROM Student s
INNER JOIN Enrollment e ON e.student_id = s.student_id
GROUP BY s.student_id, s.first_name, s.last_name
HAVING AVG(e.grade) > (SELECT AVG(grade) FROM Enrollment)
ORDER BY student_average DESC;


SELECT s.student_id,
       CONCAT(s.first_name, ' ', s.last_name) AS student_name,
       COUNT(e.enrollment_id)                 AS courses_enrolled
FROM Student s
LEFT JOIN Enrollment e ON e.student_id = s.student_id
GROUP BY s.student_id, s.first_name, s.last_name
ORDER BY courses_enrolled, s.student_id;


SELECT c.course_code, c.course_name
FROM Course c
LEFT JOIN Enrollment e ON e.course_id = c.course_id
WHERE e.enrollment_id IS NULL;

SELECT c.course_code,
       CONCAT(s.first_name, ' ', s.last_name) AS student_name,
       e.grade
FROM Enrollment e
INNER JOIN Student s ON s.student_id = e.student_id
INNER JOIN Course c  ON c.course_id  = e.course_id
WHERE e.grade > (SELECT AVG(e2.grade)
                 FROM Enrollment e2
                 WHERE e2.course_id = e.course_id)
ORDER BY c.course_code, e.grade DESC;


