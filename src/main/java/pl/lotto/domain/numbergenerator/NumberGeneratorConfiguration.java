package pl.lotto.domain.numbergenerator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.lotto.domain.numberreceiver.NumberReceiverFacade;

import java.time.LocalDateTime;
import java.util.Optional;

@Configuration
public class NumberGeneratorConfiguration {

    @Bean
    WinningNumbersGeneratorFacade winningNumbersGeneratorFacade(WinningNumbersRepository winningNumbersRepository, NumberReceiverFacade numberReceiverFacade, RandomNumberGenerable randomNumberGenerable, WinningNumbersGeneratorFacadeConfigurationProperties properties ){
        WinningNumberValidator winningNumberValidator = new WinningNumberValidator();
        return new WinningNumbersGeneratorFacade(randomNumberGenerable, winningNumberValidator, winningNumbersRepository, numberReceiverFacade, properties);
    }

    WinningNumbersGeneratorFacade createForTest(RandomNumberGenerable generator, WinningNumbersRepository winningNumbersRepository, NumberReceiverFacade numberReceiverFacade) {
        WinningNumbersGeneratorFacadeConfigurationProperties properties = WinningNumbersGeneratorFacadeConfigurationProperties.builder()
                .upperBand(99)
                .count(6)
                .lowerBand(1)
                .build();
        return winningNumbersGeneratorFacade(winningNumbersRepository, numberReceiverFacade, generator, properties);
    }
}