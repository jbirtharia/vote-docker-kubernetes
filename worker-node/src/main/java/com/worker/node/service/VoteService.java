package com.worker.node.service;

import com.worker.node.dto.Votes;
import com.worker.node.entities.VoteEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class VoteService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private RedisService redisService;

    @Transactional
    public void saveVote(String voteType, Votes vote) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery();
        Root<VoteEntity> fromRoot = criteriaQuery.from(VoteEntity.class);
        criteriaQuery.select(fromRoot);

        criteriaQuery.where(criteriaBuilder.equal(fromRoot.get("vote"), vote.getVote()));

        List<Object> resultList = entityManager.createQuery(criteriaQuery).getResultList();

        if (resultList.isEmpty()) {
            VoteEntity voteEntity = new VoteEntity();
            voteEntity.setVote(vote.getVote());
            voteEntity.setCount(vote.getCount());
            entityManager.persist(voteEntity);
            vote.setAllProcessed(true);
            redisService.set(voteType, vote);

        }else {
            VoteEntity voteEntity = (VoteEntity) resultList.get(0);
            voteEntity.setCount(vote.getCount());
            entityManager.merge(voteEntity);
            vote.setAllProcessed(true);
            redisService.set(voteType, vote);
        }
        log.info("Vote has been saved");
    }
}
