package com.example.TurnKeyGo.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@Getter
@Setter
public class ResponsePrompt {
    private String prompt;
    private String[] response;
    private String TypeOfResponseExpected;
    private Integer numberOfTokens;
    private String ChatBotModel;
}
