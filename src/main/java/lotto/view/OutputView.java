package lotto.view;

import lotto.model.Lotto;
import lotto.model.LottoResult;
import lotto.model.Rank;
import java.util.List;

public class OutputView {
    public void printLottos(List<Lotto> lottos) {
        System.out.println("\n" + lottos.size() + "개를 구매했습니다.");
        lottos.forEach(lotto -> System.out.println(lotto.getNumbers()));
    }

    public void printResult(LottoResult result) {
        System.out.println("\n당첨 통계");
        System.out.println("---");
        printRankResult(Rank.FIFTH, result);
        printRankResult(Rank.FOURTH, result);
        printRankResult(Rank.THIRD, result);
        printRankResult(Rank.SECOND, result);
        printRankResult(Rank.FIRST, result);
        printProfitRate(result);
    }

    private void printRankResult(Rank rank, LottoResult result) {
        System.out.printf("%s (%,d원) - %d개%n",
                rank.getDescription(),
                rank.getPrize(),
                result.getCount(rank));
    }

    private void printProfitRate(LottoResult result) {
        System.out.printf("총 수익률은 %.1f%%입니다.%n", result.calculateProfitRate());
    }

    public void printError(String message) {
        System.out.println(message);
    }
}