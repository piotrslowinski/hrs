package pl.com.bottega.hrs.infrastructure;

import pl.com.bottega.hrs.application.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * Created by user on 03.11.2017.
 */
public class JPQLEmployeeFinder implements EmployeeFinder {

    private EntityManager entityManager;

    public JPQLEmployeeFinder(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public EmployeeSearchResults search(EmployeeSearchCriteria criteria) {
        EmployeeSearchResults results = new EmployeeSearchResults();
        String jpql = "SELECT " +
                "NEW pl.com.bottega.hrs.application.BasicEmployeeDto(e.empNo, e.firstName, e.lastName) " +
                "FROM Employee e ";
        String whereJpql = " 1 = 1 ";

        if (criteria.getLastNameQuery() != null) {
            whereJpql += "AND e.lastName LIKE :lastName ";
        }

        if (criteria.getFirstNameQuery() != null) {
            whereJpql += " AND e.firstName LIKE :firstName ";
        }

        Query query = entityManager.createQuery(jpql + "WHERE" + whereJpql);
        if (criteria.getLastNameQuery() != null) {
            query.setParameter("lastName", criteria.getLastNameQuery() + "%");
        }
        if (criteria.getFirstNameQuery() != null) {
            query.setParameter("firstName", criteria.getFirstNameQuery() + "%");
        }
        results.setResults(query.getResultList());
        return results;
    }

    @Override
    public DetailedEmployeeDto getEmployeeDetails(Integer empNo) {
        return null;
    }

}
