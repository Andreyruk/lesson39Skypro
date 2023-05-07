package dao;

import jakarta.persistence.*;
import model.Employee;

import java.util.List;
import java.util.function.Consumer;

public class EmployeeDao implements Dao<Employee> {
    static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("myUnit");
    static EntityManager entityManager = entityManagerFactory.createEntityManager();

    @Override
    public Employee get(long id) {
        return entityManager.find(Employee.class, id);
    }

    @Override
    public List<Employee> getAll() {
        Query query = entityManager.createQuery("SELECT e FROM Employee e");
        return query.getResultList();
    }

    @Override
    public void save(Employee employee) {
        executeInsideTransaction(entityManager -> entityManager.persist(employee));
    }

    @Override
    public void update(long id, Employee model) {
        Employee employee = this.get(id);
        employee.setFirstName(model.getFirstName());
        employee.setLastName(model.getLastName());
        employee.setGender(model.getGender());
        employee.setAge(model.getAge());
        employee.setCityId(model.getCityId());
        executeInsideTransaction(entityManager -> entityManager.merge(employee));
    }

    @Override
    public void delete(long id) {
        Employee employee = this.get(id);
        executeInsideTransaction(entityManager -> entityManager.remove(employee));
    }

    private void executeInsideTransaction(Consumer<EntityManager> action) {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            action.accept(entityManager);
            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        }
    }
}
