package co.istad.mobilebanking.features.card_type;

import co.istad.mobilebanking.features.card_type.dto.CardTypeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardTypeServiceImpl implements CardTypeService{
    private final CardTypeRespository cardTypeRespository;

    @Override
    public List<CardTypeResponse> findAllCardType() {
        return cardTypeRespository.findAll().stream()
                .map(cardType -> new CardTypeResponse(
                        cardType.getName(),
                        cardType.getIsDeleted()
                ))
                .toList();
    }

    @Override
    public CardTypeResponse findCardTypeByName(String name) {
        if (!cardTypeRespository.existsByName(name)){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Card type doesn't exist!"
            );
        }

        return cardTypeRespository.findAll().stream()
                .filter(cardType -> cardType.getName().equals(name))
                .map(cardType -> new CardTypeResponse(
                        cardType.getName(),
                        cardType.getIsDeleted()
                ))
                .findFirst().orElseThrow();
    }
}
