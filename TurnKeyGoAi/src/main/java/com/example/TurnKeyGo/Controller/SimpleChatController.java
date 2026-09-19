package com.example.TurnKeyGo.Controller;

import com.example.TurnKeyGo.Dto.ChatRequest;
import com.example.TurnKeyGo.Dto.ResponsePrompt;
import com.example.TurnKeyGo.Dto.TempChatResponse;
import com.example.TurnKeyGo.Service.SimpleChat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path="/TempMode")
@Slf4j
public class SimpleChatController {

    private final SimpleChat simpleChatService;

  @PostMapping("/prompt")
    public ResponseEntity<TempChatResponse> Prompt(@RequestBody ChatRequest chatRequest){
      return ResponseEntity.ok( simpleChatService.NormalChat(chatRequest));
  }

    @PostMapping("/think-mode")
    public ResponseEntity<ResponsePrompt> Enhanced_Mode(@RequestBody ChatRequest chatRequest, @RequestParam(defaultValue = "0.2",value = "temp") Double temp){
      log.info("{}",chatRequest.toString());
        return ResponseEntity.ok( simpleChatService.Enhanced_Mode(chatRequest,temp));
    }





}
