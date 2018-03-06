package com.panelbjj.dao;


import java.util.List;

import com.panelbjj.dto.Competitor;
import com.panelbjj.dto.Category;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
@Transactional
public class CompetitorRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void deleteUnconfirmed(long hours){
        Long xHoursAgo = System.currentTimeMillis()-hours*60*60*1000;
        Query query = entityManager.createQuery(
                "delete from Competitor c where c.enabled=false and c.registrationNumber<:olderThan");
        query.setParameter("olderThan",xHoursAgo).executeUpdate();
    }

    public List<Competitor> findByRegNumber(Long registrationNumber){
        Query query = entityManager
                .createQuery("select c from Competitor c where c.registrationNumber like ?1");
        return query.setParameter(1, registrationNumber).getResultList();
    }

    public List<Competitor> findAll(int pageNumber) {
        Query query = entityManager.createQuery
                ("select c from Competitor c");
        List<Competitor> list = query.setFirstResult((pageNumber-1)*100).setMaxResults(100).getResultList();
        return list;
    }

    public void deleteCompetitor(int competitorId) {
        entityManager.remove(entityManager.find(Competitor.class, competitorId));
    }

    public List<Competitor> findByName(String name){
        Query query = entityManager
                .createQuery("select c from Competitor c where c.name like ?1");
        return query.setParameter(1, "%"+name+"%").getResultList();
    }

    public List<Competitor> findCategory(Category category) {
        Query query = entityManager.createQuery
                ("select c from Competitor c where c.category=:category and c.weight=:weight and c.belt=:belt");
        List<Competitor> list = query.setParameter("category", category.getCategory())
                .setParameter("weight", category.getWeight())
                .setParameter("belt", category.getBelt())
                .getResultList();
        return list;
    }

    public List<Competitor> findCategoryToGenerate(Category category) {
        Query query = entityManager.createQuery
                ("select c from Competitor c where c.category=:category and" +
                        " c.weight=:weight and c.belt=:belt and c.enabled=true and c.paid=true");
        List<Competitor> list = query.setParameter("category", category.getCategory())
                .setParameter("weight", category.getWeight())
                .setParameter("belt", category.getBelt())
                .getResultList();
        return list;
    }

    public void confirmZgloszenie(long regNumber) throws NullPointerException {
        Query query = entityManager.createQuery
                ("select c from Competitor c where c.registrationNumber=:regNo");
        List<Competitor> list = query.setParameter("regNo", regNumber).getResultList();
        if (list.isEmpty()) {
            throw new NullPointerException();
        }
        list.forEach(competitor -> competitor.setEnabled(true));
        persistRegistration(list);
    }

    public void persistRegistration(List<Competitor> registrationList) {
        for (Competitor competitor : registrationList) {
            entityManager.persist(competitor);
        }
    }

    public Competitor findCompetitorById(int competitorId) {
        return entityManager.find(Competitor.class, competitorId);
    }

    public void updateCompetitor(Competitor competitor) {
        entityManager.merge(competitor);
    }

}
