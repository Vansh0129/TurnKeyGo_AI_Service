package com.example.TurnKeyGo.Dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@AllArgsConstructor
@Builder

public class TempChatResponse {
    String response;
    String prompt;
    Integer totalTokenUsed;


}
