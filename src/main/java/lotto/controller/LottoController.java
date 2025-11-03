package lotto.controller;

import lotto.model.Lotto;
import lotto.model.LottoGenerator;
import lotto.model.LottoResult;
import lotto.model.WinningNumbers;
import lotto.view.InputView;
import lotto.view.OutputView;
import java.util.List;
import java.util.function.Supplier;

public class LottoController {
    private final InputView inputView;
    private final OutputView outputView;

    public LottoController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        int purchaseAmount = readPurchaseAmount();
        List<Lotto> lottos = generateLottos(purchaseAmount);
        outputView.printLottos(lottos);

        WinningNumbers winningNumbers = readWinningNumbers();
        LottoResult result = new LottoResult(lottos, winningNumbers, purchaseAmount);
        outputView.printResult(result);
    }

    private int readPurchaseAmount() {
        return repeatUntilSuccess(inputView::readPurchaseAmount);
    }

    private List<Lotto> generateLottos(int purchaseAmount) {
        return repeatUntilSuccess(() -> LottoGenerator.generate(purchaseAmount));
    }

    private WinningNumbers readWinningNumbers() {
        return repeatUntilSuccess(() -> {
            List<Integer> numbers = readWinningNumberList();
            int bonusNumber = readBonusNumber();
            return new WinningNumbers(numbers, bonusNumber);
        });
    }

    private List<Integer> readWinningNumberList() {
        return repeatUntilSuccess(() -> {
            List<Integer> numbers = inputView.readWinningNumbers();
            new Lotto(numbers);
            return numbers;
        });
    }

    private int readBonusNumber() {
        return repeatUntilSuccess(inputView::readBonusNumber);
    }

    private <T> T repeatUntilSuccess(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }
}