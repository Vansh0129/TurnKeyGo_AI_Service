package com.example.TurnKeyGo.Controller;

import com.example.TurnKeyGo.Dto.ChatRequest;
import com.example.TurnKeyGo.Dto.FileUploadDto;
import com.example.TurnKeyGo.Service.DocumentRagModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/vector-search")
@RequiredArgsConstructor
public class DocumentRagModelController {
    private final DocumentRagModelService service;

//    @PostMapping("/upload")
//    public ResponseEntity<HashMap<String,String>> EmbeddingRequestDoc(@RequestBody FileUploadDto dto){
//        service.Ingest(dto);
//        HashMap<String,String> map=new HashMap<>();
//        map.put("Status","success");
//        return ResponseEntity.ok(map);
//    }

//    File Uploader !
    @PostMapping(
            value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<HashMap<String, String>> EmbeddingRequestDoc(
            @RequestParam("file") MultipartFile file) {

        FileUploadDto dto = new FileUploadDto();
        dto.setFile(file);

        service.Ingest(dto);

        HashMap<String, String> map = new HashMap<>();
        map.put("Status", "success");

        return new ResponseEntity<>(map,HttpStatusCode.valueOf(201));
    }

    @GetMapping(path = "/search/{searchKey}")
    public ResponseEntity<List<String>> SimilaritySearch(
            @PathVariable(name = "searchKey") String request,
            @RequestParam(name = "threshold",defaultValue = "0.4") Double threshold,
            @RequestParam(name = "topK",defaultValue = "3") Integer topK
    ) {
        List<String> response = service.SimilaritySearch(request, threshold, topK);
        return ResponseEntity.ok(response);
    }







}
