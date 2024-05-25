package org.example.services;

import org.example.entities.Ranking;
import org.example.entities.RankingPeriod;
import org.example.entities.User;

import java.util.List;

public interface IRankingService {
    List<Ranking> findByPeriodSorted(RankingPeriod rp);
    void updateUserScore(User user, int points);
    void updateSingleRanking(User user, int points, RankingPeriod period);
    void updateAllRankings(RankingPeriod period);

    void resetRankings(RankingPeriod period);

    List<Ranking> getTop3RankingsByPeriod(RankingPeriod period);
}
