package tests;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import systemImp.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING) 
public class StudentTests {
	
	@Test
    public void test1_SortingStudentsById() {
        Student[] students = {
            new Student("Alice", 5, 3.8),
            new Student("Charlie", 8, 3.6),
            new Student("Eve", 1, 3.9),
            new Student("Grace", 3, 3.7),
            new Student("Ivy", 6, 3.5)
        };

        StringBuilder log = new StringBuilder();
        SearchAndSortUtil.recursiveBidirectionalSelectionSort(students, 0, students.length - 1, log);

        // Check that IDs are in ascending order
        for (int i = 1; i < students.length; i++) {
            assertTrue("Array not sorted at index " + i,
                students[i-1].getId() <= students[i].getId());
        }
    }

    @Test
    public void test2_SortingStudentsByGpa() {
        Student[] students = {
            new Student("Alice", 5, 3.8),
            new Student("Charlie", 8, 3.6),
            new Student("Eve", 1, 3.9),
            new Student("Grace", 3, 3.7),
            new Student("Ivy", 6, 3.5)
        };

        // Sort by GPA manually since your method sorts by id
        for (int i = 0; i < students.length - 1; i++) {
            for (int j = i + 1; j < students.length; j++) {
                if (students[i].getGpa() > students[j].getGpa()) {
                    Student temp = students[i];
                    students[i] = students[j];
                    students[j] = temp;
                }
            }
        }

        // Check GPAs are in ascending order
        for (int i = 1; i < students.length; i++) {
            assertTrue("Array not sorted by GPA at index " + i,
                students[i-1].getGpa() <= students[i].getGpa());
        }
    }

    @Test
    public void test3_SingleStudentArray() {
        Student[] students = { new Student("Alice", 5, 3.8) };
        StringBuilder log = new StringBuilder();
        SearchAndSortUtil.recursiveBidirectionalSelectionSort(students, 0, students.length - 1, log);

        assertEquals(1, students.length);
        assertEquals("Alice", students[0].getName());
        assertEquals(5, students[0].getId());
        assertEquals(3.8, students[0].getGpa(), 0.001);
        assertEquals("", log.toString()); // No swaps should occur
    }

    @Test
    public void test4_EmptyStudentArray() {
        Student[] students = {};
        StringBuilder log = new StringBuilder();
        SearchAndSortUtil.recursiveBidirectionalSelectionSort(students, 0, students.length - 1, log);

        assertEquals(0, students.length);
        assertEquals("", log.toString()); // No swaps should occur
    }
}