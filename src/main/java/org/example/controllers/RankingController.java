package org.example.controllers;

import org.example.entities.Ranking;
import org.example.entities.RankingPeriod;
import org.example.services.IRankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/rankings")
public class RankingController {

    private final IRankingService rankingService;

    @Autowired
    public RankingController(IRankingService rankingService) {
        this.rankingService = rankingService;
    }

    @GetMapping
    public ResponseEntity<List<Ranking>> getAllRankings() {
        List<Ranking> rankings = rankingService.findAll();
        return new ResponseEntity<>(rankings, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ranking> getRankingById(@PathVariable Integer id) {
        Ranking ranking = rankingService.findByIdRanking(id);
        if (ranking != null) {
            return new ResponseEntity<>(ranking, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/period/{period}")
    public ResponseEntity<Set<Ranking>> getRankingByPeriod(@PathVariable RankingPeriod period) {
        Set<Ranking> rankings = rankingService.findByPeriod(period);
        return new ResponseEntity<>(rankings, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Ranking> addRanking(@RequestBody Ranking ranking) {
        Ranking addedRanking = rankingService.addRanking(ranking);
        return new ResponseEntity<>(addedRanking, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ranking> updateRanking(@PathVariable Integer id, @RequestBody Ranking ranking) {
        Ranking updatedRanking = rankingService.updateRanking(id, ranking);
        if (updatedRanking != null) {
            return new ResponseEntity<>(updatedRanking, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRanking(@PathVariable Integer id) {
        Ranking ranking = rankingService.findByIdRanking(id);
        if (ranking != null) {
            rankingService.DeleteRanking(ranking);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Add other endpoints for ranking-related operations if needed
}
