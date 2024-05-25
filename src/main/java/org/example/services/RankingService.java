package org.example.services;

import org.example.entities.Ranking;
import org.example.entities.RankingPeriod;
import org.example.entities.User;
import org.example.repositories.RankingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Comparator;

@Service
public class RankingService implements IRankingService {

    private final RankingRepository rankingRepository;

    @Autowired
    public RankingService(RankingRepository rankingRepository) {
        this.rankingRepository = rankingRepository;
    }
    @Override
    public List<Ranking> findByPeriodSorted(RankingPeriod rp) {
        return rankingRepository.findByPeriodOrderByRankAsc(rp);
    }
    @Transactional
    @Override
    public void updateUserScore(User user, int points) {
        updateSingleRanking(user, points, RankingPeriod.Week);
        updateSingleRanking(user, points, RankingPeriod.Month);
        updateSingleRanking(user, points, RankingPeriod.Year);
    }

    @Override
    public void updateSingleRanking(User user, int points, RankingPeriod period) {
        Ranking ranking = rankingRepository.findByUserAndPeriod(user, period);
        ranking.setPoints(ranking.getPoints()+points);
        rankingRepository.save(ranking);
        updateAllRankings(period);
    }
    @Override
    public void updateAllRankings(RankingPeriod period) {
        List<Ranking> rankings = rankingRepository.findByPeriod(period);
        // Sort the rankings in descending order of points
        rankings.sort(Comparator.comparingInt(Ranking::getPoints).reversed());
        // Assign ranks based on the sorted order
        int rank = 1;
        for (Ranking ranking : rankings) {
            ranking.setRank(rank++);
        }
        rankingRepository.saveAll(rankings);
    }
    @Override
    public void resetRankings(RankingPeriod period) {
        List<Ranking> rankings = rankingRepository.findByPeriod(period);
        for (Ranking ranking : rankings) {
            ranking.setPoints(0);
            updateAllRankings(period);
        }
        rankingRepository.saveAll(rankings);
    }
    @Override
    public List<Ranking> getTop3RankingsByPeriod(RankingPeriod period) {
        // Create a Pageable object to limit the results to the top 3
        Pageable pageable = PageRequest.of(0, 3);

        // Call the repository method to fetch the top 3 rankings
        return rankingRepository.findTop3ByPeriodOrderByRankAsc(period, pageable);
    }
}
