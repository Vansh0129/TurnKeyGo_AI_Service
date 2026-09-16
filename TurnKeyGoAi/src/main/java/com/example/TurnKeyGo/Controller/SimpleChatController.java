package com.example.TurnKeyGo.Controller;

import com.example.TurnKeyGo.Dto.ChatRequest;
import com.example.TurnKeyGo.Dto.TempChatResponse;
import com.example.TurnKeyGo.Service.SimpleChat;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path="/TempMode")
public class SimpleChatController {
    private final SimpleChat simpleChatService;
  @PostMapping("/prompt")
    public ResponseEntity<TempChatResponse> Prompt(@RequestBody ChatRequest chatRequest){
      return ResponseEntity.ok( simpleChatService.NormalChat(chatRequest));



  }





}
