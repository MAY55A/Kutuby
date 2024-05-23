package org.example.repositories;

import org.example.entities.Ranking;
import org.example.entities.RankingPeriod;
import org.example.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface RankingRepository extends JpaRepository<Ranking, Integer> {
    Ranking findByUser(User user);
    Set<Ranking> findByPeriod(RankingPeriod rp);
    Ranking findByUserAndPeriod(User user, RankingPeriod period);
}
