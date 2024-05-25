package org.example.scheduler;

import org.example.entities.RankingPeriod;
import org.example.services.RankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RankingScheduler {

    @Autowired
    private RankingService rankingService;

    @Scheduled(cron = "0 0 0 * * MON") // Every Monday at midnight
    public void resetWeeklyRankings() {
        rankingService.resetRankings(RankingPeriod.Week);
    }

    @Scheduled(cron = "0 0 0 1 * ?") // First day of every month at midnight
    public void resetMonthlyRankings() {
        rankingService.resetRankings(RankingPeriod.Month);
    }

    @Scheduled(cron = "0 0 0 1 1 ?") // First day of every year at midnight
    public void resetYearlyRankings() {
        rankingService.resetRankings(RankingPeriod.Year);
    }
}
