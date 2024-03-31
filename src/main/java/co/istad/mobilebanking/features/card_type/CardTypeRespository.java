package co.istad.mobilebanking.features.card_type;

import co.istad.mobilebanking.domain.CardType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardTypeRespository extends JpaRepository<CardType,Integer> {
    Boolean existsByName(String name);
}
