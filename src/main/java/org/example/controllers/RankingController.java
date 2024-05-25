package org.example.controllers;

import org.example.entities.Ranking;
import org.example.entities.RankingPeriod;
import org.example.services.IRankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/leaderboard")
public class RankingController {

    private final IRankingService rankingService;

    @Autowired
    public RankingController(IRankingService rankingService) {
        this.rankingService = rankingService;
    }

    @GetMapping("/{period}")
    public String getRankingByPeriod(@PathVariable RankingPeriod period, Model model) {
        List<Ranking> rankings = rankingService.findByPeriodSorted(period);
        if(rankings != null)
            model.addAttribute("rankings", rankings);
        return "Guest/leaderboard";
    }
}
