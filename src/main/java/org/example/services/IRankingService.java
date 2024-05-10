package org.example.services;

import org.example.entities.Ranking;
import org.example.entities.RankingPeriod;

import java.util.List;
import java.util.Set;

public interface IRankingService {
    List<Ranking> findAll();
    Ranking findByIdRanking(Integer id);
    Set<Ranking> findByPeriod(RankingPeriod rp);
    Ranking addRanking(Ranking r);
    void DeleteRanking(Ranking r);
    Ranking updateRanking(Integer id, Ranking updatedRanking);
}
