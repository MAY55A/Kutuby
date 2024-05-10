package org.example.services;

import org.example.entities.Ranking;
import org.example.entities.RankingPeriod;
import org.example.repositories.RankingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class RankingService implements IRankingService {

    private final RankingRepository rankingRepository;

    @Autowired
    public RankingService(RankingRepository rankingRepository) {
        this.rankingRepository = rankingRepository;
    }

    @Override
    public List<Ranking> findAll() {
        return rankingRepository.findAll();
    }

    @Override
    public Ranking findByIdRanking(Integer id) {
        Optional<Ranking> optionalRanking = rankingRepository.findById(id);
        return optionalRanking.orElse(null);
    }

    @Override
    public Set<Ranking> findByPeriod(RankingPeriod rp) {
        return rankingRepository.findByPeriod(rp);
    }

    @Override
    public Ranking addRanking(Ranking r) {
        return rankingRepository.save(r);
    }

    @Override
    public void DeleteRanking(Ranking r) {
        rankingRepository.delete(r);
    }

    @Override
    public Ranking updateRanking(Integer id, Ranking updatedRanking) {
        Optional<Ranking> optionalRanking = rankingRepository.findById(id);
        if (optionalRanking.isPresent()) {
            Ranking existingRanking = optionalRanking.get();
            existingRanking.setRank(updatedRanking.getRank());
            existingRanking.setPoints(updatedRanking.getPoints());
            existingRanking.setPeriod(updatedRanking.getPeriod());
            existingRanking.setUser(updatedRanking.getUser());
            return rankingRepository.save(existingRanking);
        } else {
            throw new RuntimeException("Ranking not found with id: " + id);
        }
    }
}
