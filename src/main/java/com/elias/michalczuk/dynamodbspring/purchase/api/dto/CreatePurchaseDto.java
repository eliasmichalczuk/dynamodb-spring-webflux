package com.elias.michalczuk.dynamodbspring.purchase.api.dto;

import com.elias.michalczuk.dynamodbspring.purchase.domain.Purchase;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public final class CreatePurchaseDto {

    private List<UUID> productIds;
}
