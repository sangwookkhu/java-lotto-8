package lotto.model;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LottoResult {
    private final Map<Rank, Integer> result;
    private final int purchaseAmount;

    public LottoResult(List<Lotto> lottos, WinningNumbers winningNumbers, int purchaseAmount) {
        this.result = calculateResult(lottos, winningNumbers);
        this.purchaseAmount = purchaseAmount;
    }

    private Map<Rank, Integer> calculateResult(List<Lotto> lottos, WinningNumbers winningNumbers) {
        Map<Rank, Long> rankCounts = lottos.stream()
                .map(winningNumbers::match)
                .filter(Rank::isWinning)
                .collect(Collectors.groupingBy(rank -> rank, Collectors.counting()));

        return Arrays.stream(Rank.values())
                .filter(Rank::isWinning)
                .collect(Collectors.toMap(
                        rank -> rank,
                        rank -> rankCounts.getOrDefault(rank, 0L).intValue()
                ));
    }

    public int getCount(Rank rank) {
        return result.getOrDefault(rank, 0);
    }

    public double calculateProfitRate() {
        long totalPrize = result.entrySet().stream()
                .mapToLong(entry -> (long) entry.getKey().getPrize() * entry.getValue())
                .sum();
        return Math.round((double) totalPrize / purchaseAmount * 1000) / 10.0;
    }
}