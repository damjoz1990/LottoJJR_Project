package pl.lotto.domain.numberreceiver;

import lombok.AllArgsConstructor;
import pl.lotto.domain.numberreceiver.dto.InputNumberResultDto;
import pl.lotto.domain.numberreceiver.dto.TicketDto;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import static pl.lotto.domain.numberreceiver.dto.InputNumberResultDto.*;

@AllArgsConstructor
public class NumberReceiverFacade {

    private final NumberValidator validator;
    private final NumberReceiverRepository repository;
    private final Clock cloc;

    public InputNumberResultDto inputNumbers(Set<Integer> numbersFromUser){
       boolean areAllNumbersInRAnge = validator.areAllNumbersInRange(numbersFromUser);
        if(areAllNumbersInRAnge) {
            String ticketId = UUID.randomUUID().toString();
            LocalDateTime drawDate = LocalDateTime.now(cloc);
            Ticket savedTicket  = repository.save(new Ticket(ticketId, drawDate, numbersFromUser));
            return builder()
                    .drawDate(savedTicket.drawDate())
                    .ticketId(savedTicket.ticketId())
                    .numbersFromUser(numbersFromUser)
                    .message("success")
                    .build();
        }
        return builder()
                .message("failed")
                .build();
    }

    public List<TicketDto> userNumbers(LocalDateTime date){
        List<Ticket> allTicketsByDrawDate = repository.findAllTicketsByDrawDate(date);
        return allTicketsByDrawDate
                .stream()
                .map(ticket -> TicketMapper.mapFromTicket(ticket))
                .toList();
    }

}
