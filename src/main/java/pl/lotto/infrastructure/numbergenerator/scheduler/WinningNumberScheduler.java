package pl.lotto.infrastructure.numbergenerator.scheduler;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.lotto.domain.numbergenerator.WinningNumbersGeneratorFacade;
import pl.lotto.domain.numbergenerator.dto.WinningNumbersDto;


@Component
@AllArgsConstructor
@Log4j2
public class WinningNumberScheduler {

    private final WinningNumbersGeneratorFacade winningNumbersGeneratorFacade;

    //https://crontab.guru/ - website with cron editor
    @Scheduled( cron = "${lotto.number-generator.lotteryRunOccurrence}")
    public void generateWiningNumbers(){
        log.info("winning number scheduler started");
        WinningNumbersDto winningNumbersDto = winningNumbersGeneratorFacade.generateWinningNumbers();
        System.out.println(winningNumbersDto.getWinningNumbers());
        System.out.println(winningNumbersDto.getDate());
    }

}
