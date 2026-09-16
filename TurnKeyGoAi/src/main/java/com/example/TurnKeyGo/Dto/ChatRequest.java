package com.example.TurnKeyGo.Dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@AllArgsConstructor
@Builder

public class ChatRequest {
    String prompt;
    String example;
    Long temp;


}
