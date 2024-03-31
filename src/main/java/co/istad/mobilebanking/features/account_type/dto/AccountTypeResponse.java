package co.istad.mobilebanking.features.account_type.dto;

public record AccountTypeResponse(
        String alias,
        String name,
        String description,
        Boolean isDeleted
){
}
