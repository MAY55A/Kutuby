package org.example.repositories;

import org.example.entities.Ranking;
import org.example.entities.RankingPeriod;
import org.example.entities.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RankingRepository extends JpaRepository<Ranking, Integer> {
    Ranking findByUser(User user);
    List<Ranking> findByPeriod(RankingPeriod rp);
    List<Ranking> findByPeriodOrderByRankAsc(RankingPeriod rp);

    Ranking findByUserAndPeriod(User user, RankingPeriod period);

    List<Ranking> findTop3ByPeriodOrderByRankAsc(RankingPeriod period, Pageable pageable);
}
