package com.example.TurnKeyGo.Service;

import com.example.TurnKeyGo.Dto.ChatRequest;
import com.example.TurnKeyGo.Dto.ResponsePrompt;
import com.example.TurnKeyGo.Dto.TempChatResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor

@Service
public class SimpleChat {
    private final ChatClient chatClient;
    @Value("${spring.ai.ollama.chat.model}")
    public String MODEL_NAME;

    public TempChatResponse NormalChat(ChatRequest chatRequest) {
        String systemPrompt = """
                This is an free chat model, Which can ask any thing.
                You need to return response in smart way with an small example.
                """;
        String userPrompt = chatRequest.getPrompt();
        if (!chatRequest.getExample().isBlank()) userPrompt += "Example for given prompt :" + chatRequest.getExample();

        ChatResponse response = chatClient.prompt().system(systemPrompt).user(userPrompt).call().chatResponse();
//        return new TempChatResponse(response.getMetadata().getModel() + " : " + response.getResult().getOutput().getText(), chatRequest.getPrompt(), response.getMetadata().getUsage().getTotalTokens());
        return new TempChatResponse(MODEL_NAME+ " : " + response.getResult().getOutput().getText(), chatRequest.getPrompt(), response.getMetadata().getUsage().getTotalTokens());

    }

    public ResponsePrompt Enhanced_Mode(ChatRequest chatRequest, Double temp) {


        String systemPrompt = """
                This is an free chat model, Which can ask any thing.
                You need to think deep and give and response.
                """;
        String chatSystemPrompt = """
                This is model response You need to Enhance the Response.
                the user prompt is :- {UserPrompt}""";
        chatSystemPrompt = new PromptTemplate(chatSystemPrompt).render(Map.of("UserPrompt", chatRequest.getPrompt()));

        String user = chatRequest.getPrompt();

        ResponsePrompt response = chatClient.prompt()
                .system(systemPrompt)
                .user(user + " Expected Response Should be as " + chatRequest.getExample())
                .options(ChatOptions.builder()
                        .temperature(temp)

                )
                .call()
                .entity(ResponsePrompt.class);
        log.info(response.toString());
        Integer tokens = response.getNumberOfTokens();


        response = chatClient.prompt()
                .system(chatSystemPrompt)
                .user("Ai Response is :" + Arrays.toString(response.getResponse()))
                .options(ChatOptions.builder()
                        .temperature(temp)

                )
                .call()
                .entity(ResponsePrompt.class);
        response.setNumberOfTokens(response.getNumberOfTokens() + tokens);
        response.setChatBotModel(MODEL_NAME);

        return response;

    }
}
