package org.example.repositories;

import org.example.entities.Ranking;
import org.example.entities.RankingPeriod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface RankingRepository extends JpaRepository<Ranking, Integer> {
    Set<Ranking> findByPeriod(RankingPeriod period);
}
