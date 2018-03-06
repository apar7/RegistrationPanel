package com.panelbjj.dao;

import com.panelbjj.dto.Club;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;


@Repository
@Transactional
public class ClubRepository {


    @PersistenceContext
    private EntityManager entityManager;

    public void usunKlub(int klubId) {
        entityManager.remove(entityManager.find(Club.class, klubId));
    }

    public void addKlub(Club klub) {
        entityManager.persist(klub);
    }

    public void updateKlub(Club klub) {
        entityManager.merge(klub);
    }

    public List<String> downloadClubList() {
        Query query = entityManager.createQuery("select c.name from Club c order by c.name");
        return query.getResultList();

    }

    public List<Club> downloadClubstoEdit() {
        Query query = entityManager.createQuery("select c from Club c order by c.name");
        return query.getResultList();
    }

}
