package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.util.List;

public class App {
    public static EntityManagerFactory emf = Persistence.createEntityManagerFactory("studentPU");

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static <T> void printResult(String title, T returned) {
        System.out.println("-------------- " + title + " --------------");
        System.out.println(returned);
        System.out.println("--------------------------------------------------\n");
    }

    public static void getAllStudents() {
        EntityManager em = getEntityManager();
        var jpql = "SELECT s FROM Student s";
        var returned = em.createQuery(jpql, Student.class).getResultList();
        printResult("Task 1: All Students", returned);
        em.close();
    }

    public static void getStudentById(int id) {
        EntityManager em = getEntityManager();
        var jpql = "SELECT s FROM Student s WHERE s.id = :id";
        var returned = em.createQuery(jpql, Student.class)
                .setParameter("id", id)
                .getSingleResult();
        printResult("Task 2: Student of ID: " + id, returned);
        em.close();
    }

    public static void getStudentsByName(String name) {
        EntityManager em = getEntityManager();
        var jpql = "SELECT s FROM Student s WHERE s.name = :name";
        var returned = em.createQuery(jpql, Student.class)
                .setParameter("name", name)
                .getResultList();
        printResult("Task 3: Students Named: " + name, returned);
        em.close();
    }

    public static void getStudentsByNameCaseInsensitive(String name) {
        EntityManager em = getEntityManager();
        var jpql = "SELECT s FROM Student s WHERE LOWER(s.name) = LOWER(:name)";
        var returned = em.createQuery(jpql, Student.class)
                .setParameter("name", name)
                .getResultList();
        printResult("Task 4: Case-Insensitive Name: " + name, returned);
        em.close();
    }

    public static void getStudentsByTech(String Tech) {
        EntityManager em = getEntityManager();
        var jpql = "SELECT s FROM Student s WHERE s.Tech = :Tech";
        var returned = em.createQuery(jpql, Student.class)
                .setParameter("Tech", Tech)
                .getResultList();
        printResult("Task 5: Students Studying: " + Tech, returned);
        em.close();
    }

    public static void getStudentsByNameStartingWith(String letter) {
        EntityManager em = getEntityManager();
        var jpql = "SELECT s FROM Student s WHERE s.name LIKE :pattern";
        var returned = em.createQuery(jpql, Student.class)
                .setParameter("pattern", letter + "%")
                .getResultList();
        printResult("Task 6: Names Starting With: " + letter, returned);
        em.close();
    }

    public static void getStudentsByNameContaining(String substring) {
        EntityManager em = getEntityManager();
        var jpql = "SELECT s FROM Student s WHERE s.name LIKE :pattern";
        var returned = em.createQuery(jpql, Student.class)
                .setParameter("pattern", "%" + substring + "%")
                .getResultList();
        printResult("Task 7: Names Containing: " + substring, returned);
        em.close();
    }

    public static void getStudentsWithIdGreaterThan(int minId) {
        EntityManager em = getEntityManager();
        var jpql = "SELECT s FROM Student s WHERE s.id > :minId";
        var returned = em.createQuery(jpql, Student.class)
                .setParameter("minId", minId)
                .getResultList();
        printResult("Task 8: Students ID > " + minId, returned);
        em.close();
    }

    public static void getStudentsWithIdBetween(int startId, int endId) {
        EntityManager em = getEntityManager();
        var jpql = "SELECT s FROM Student s WHERE s.id BETWEEN :startId AND :endId";
        var returned = em.createQuery(jpql, Student.class)
                .setParameter("startId", startId)
                .setParameter("endId", endId)
                .getResultList();
        printResult("Task 9: Students ID Between " + startId + " and " + endId, returned);
        em.close();
    }

    public static void getStudentsByIdList(List<Integer> idList) {
        EntityManager em = getEntityManager();
        var jpql = "SELECT s FROM Student s WHERE s.id IN :idList";
        var returned = em.createQuery(jpql, Student.class)
                .setParameter("idList", idList)
                .getResultList();
        printResult("Task 10: Students By ID List", returned);
        em.close();
    }

    public static void getAllStudentsOrderByNameAsc() {
        EntityManager em = getEntityManager();
        var jpql = "SELECT s FROM Student s ORDER BY s.name ASC";
        var returned = em.createQuery(jpql, Student.class).getResultList();
        printResult("Task 11: Ordered By Name ASC", returned);
        em.close();
    }

    public static void getAllStudentsOrderByNameDesc() {
        EntityManager em = getEntityManager();
        var jpql = "SELECT s FROM Student s ORDER BY s.name DESC";
        var returned = em.createQuery(jpql, Student.class).getResultList();
        printResult("Task 12: Ordered By Name DESC", returned);
        em.close();
    }

    public static void getFirstNStudents(int n) {
        EntityManager em = getEntityManager();
        var jpql = "SELECT s FROM Student s ORDER BY s.id ASC";
        var returned = em.createQuery(jpql, Student.class)
                .setMaxResults(n)
                .getResultList();
        printResult("Task 13: First " + n + " Students", returned);
        em.close();
    }

    public static void getStudentsWithPagination(int offset, int limit) {
        EntityManager em = getEntityManager();
        var jpql = "SELECT s FROM Student s ORDER BY s.id ASC";
        var returned = em.createQuery(jpql, Student.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
        printResult("Task 14: Pagination (Offset: " + offset + ", Limit: " + limit + ")", returned);
        em.close();
    }

    public static void countAllStudents() {
        EntityManager em = getEntityManager();
        var jpql = "SELECT COUNT(s) FROM Student s";
        var returned = em.createQuery(jpql, Long.class).getSingleResult();
        printResult("Task 15: Total Student Count", returned);
        em.close();
    }

    public static void countStudentsByTech() {
        EntityManager em = getEntityManager();
        var jpql = "SELECT s.Tech, COUNT(s) FROM Student s GROUP BY s.Tech";
        var returned = em.createQuery(jpql, Object[].class).getResultList();

        StringBuilder sb = new StringBuilder();
        for (Object[] row : returned) {
            sb.append("Tech: ").append(row[0]).append(", Count: ").append(row[1]).append("\n");
        }
        printResult("Task 16: Count Students By Tech", sb.toString().trim());
        em.close();
    }

    public static void getDistinctTechs() {
        EntityManager em = getEntityManager();
        var jpql = "SELECT DISTINCT s.Tech FROM Student s";
        var returned = em.createQuery(jpql, String.class).getResultList();
        printResult("Task 17: Distinct Technologies", returned);
        em.close();
    }

    public static void getAverageId() {
        EntityManager em = getEntityManager();
        var jpql = "SELECT AVG(s.id) FROM Student s";
        var returned = em.createQuery(jpql, Double.class).getSingleResult();
        printResult("Task 18: Average ID", returned);
        em.close();
    }

    public static void getMaxId() {
        EntityManager em = getEntityManager();
        var jpql = "SELECT MAX(s.id) FROM Student s";
        var returned = em.createQuery(jpql, Integer.class).getSingleResult();
        printResult("Task 19: Maximum ID", returned);
        em.close();
    }

    public static void getStudentsWithLongestNames() {
        EntityManager em = getEntityManager();
        var jpql = "SELECT s FROM Student s WHERE LENGTH(s.name) = (SELECT MAX(LENGTH(s2.name)) FROM Student s2)";
        var returned = em.createQuery(jpql, Student.class).getResultList();
        printResult("Task 20: Students With Longest Names", returned);
        em.close();
    }

    public static void getStudentsByTechAndNamePattern(String Tech, String pattern) {
        EntityManager em = getEntityManager();
        var jpql = "SELECT s FROM Student s WHERE s.Tech = :Tech AND s.name LIKE :pattern";
        var returned = em.createQuery(jpql, Student.class)
                .setParameter("Tech", Tech)
                .setParameter("pattern", "%" + pattern + "%")
                .getResultList();
        printResult("Task 21: Tech (" + Tech + ") & Name Pattern (" + pattern + ")", returned);
        em.close();
    }

    public static void getStudentsWithNullTech() {
        EntityManager em = getEntityManager();
        var jpql = "SELECT s FROM Student s WHERE s.Tech IS NULL";
        var returned = em.createQuery(jpql, Student.class).getResultList();
        printResult("Task 22: Students With NULL Tech", returned);
        em.close();
    }

    public static void getStudentsWithNonNullTech() {
        EntityManager em = getEntityManager();
        var jpql = "SELECT s FROM Student s WHERE s.Tech IS NOT NULL";
        var returned = em.createQuery(jpql, Student.class).getResultList();
        printResult("Task 23: Students With Non-NULL Tech", returned);
        em.close();
    }

    public static void updateStudentTech(int id, String newTech) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        var jpql = "UPDATE Student s SET s.Tech = :newTech WHERE s.id = :id";
        var updatedCount = em.createQuery(jpql)
                .setParameter("newTech", newTech)
                .setParameter("id", id)
                .executeUpdate();
        tx.commit();
        em.close();
        printResult("Task 24: Update Student Tech (ID " + id + ")", "Successfully updated " + updatedCount + " student(s).");
    }

    public static void updateTechByPattern(String oldPattern, String newTech) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        var jpql = "UPDATE Student s SET s.Tech = :newTech WHERE s.Tech LIKE :oldPattern";
        var updatedCount = em.createQuery(jpql)
                .setParameter("newTech", newTech)
                .setParameter("oldPattern", "%" + oldPattern + "%")
                .executeUpdate();
        tx.commit();
        em.close();
        printResult("Task 25: Update Tech By Pattern", "Successfully updated " + updatedCount + " student(s).");
    }

    public static void deleteStudentById(int id) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        var jpql = "DELETE FROM Student s WHERE s.id = :id";
        var deletedCount = em.createQuery(jpql)
                .setParameter("id", id)
                .executeUpdate();
        tx.commit();
        em.close();
        printResult("Task 26: Delete Student By ID (" + id + ")", "Deleted " + deletedCount + " record(s).");
    }

    public static void deleteStudentsByTech(String Tech) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        var jpql = "DELETE FROM Student s WHERE s.Tech = :Tech";
        var deletedCount = em.createQuery(jpql)
                .setParameter("Tech", Tech)
                .executeUpdate();
        tx.commit();
        em.close();
        printResult("Task 27: Delete Students By Tech (" + Tech + ")", "Deleted " + deletedCount + " record(s).");
    }

    public static void getStudentsWithMostCommonTech() {
        EntityManager em = getEntityManager();
        var jpql = "SELECT s FROM Student s WHERE s.Tech = (" +
                "SELECT s2.Tech FROM Student s2 GROUP BY s2.Tech " +
                "ORDER BY COUNT(s2) DESC)";
        var returned = em.createQuery(jpql, Student.class)
                .setMaxResults(1)
                .getResultList();
        printResult("Task 28: Most Common Tech Students", returned);
        em.close();
    }

    public static void getStudentsWithUniqueTechs() {
        EntityManager em = getEntityManager();
        var jpql = "SELECT s FROM Student s WHERE s.Tech IN (" +
                "SELECT s2.Tech FROM Student s2 GROUP BY s2.Tech HAVING COUNT(s2) = 1)";
        var returned = em.createQuery(jpql, Student.class).getResultList();
        printResult("Task 29: Unique Tech Students", returned);
        em.close();
    }

    public static void getStudentsWithLongerThanAverageNames() {
        EntityManager em = getEntityManager();
        var jpql = "SELECT s FROM Student s WHERE LENGTH(s.name) > (SELECT AVG(LENGTH(s2.name)) FROM Student s2)";
        var returned = em.createQuery(jpql, Student.class).getResultList();
        printResult("Task 30: Name Length Greater Than Average", returned);
        em.close();
    }

    public static void main(String[] args) {
        getAllStudents();
        getStudentById(1);
        getStudentsByTech("Java");
        countAllStudents();

        updateStudentTech(1, "Python");

        emf.close();
    }
}