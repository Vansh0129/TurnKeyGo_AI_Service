package com.example.TurnKeyGo.Service;

import com.example.TurnKeyGo.Dto.ChatRequest;
import com.example.TurnKeyGo.Dto.TempChatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor

@Service
public class SimpleChat {
    private final ChatClient chatClient;

    public TempChatResponse NormalChat(ChatRequest chatRequest) {
        String systemPrompt= """
                This is an free chat model, Which can ask any thing.
                You need to return response in the ["properFormat"] of given format.
                
                """;
        String userPrompt=chatRequest.getPrompt();
        if(!chatRequest.getExample().isBlank()) userPrompt+="Example for given prompt"+chatRequest.getExample();

        ChatResponse response=chatClient.prompt().system(systemPrompt).user(userPrompt).call().chatResponse();
        return new TempChatResponse(response.getMetadata().getModel() +" : "+response.getResult().getOutput().getText(),chatRequest.getPrompt(),response.getMetadata().getUsage().getTotalTokens());

    }
}
