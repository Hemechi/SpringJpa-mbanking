package co.istad.mobilebanking.features.card_type;

import co.istad.mobilebanking.features.card_type.dto.CardTypeResponse;

import java.util.List;

public interface CardTypeService {
    List<CardTypeResponse> findAllCardType();

    CardTypeResponse findCardTypeByName(String name);
}
