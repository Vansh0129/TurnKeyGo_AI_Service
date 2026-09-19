package com.example.TurnKeyGo.Controller;

import com.example.TurnKeyGo.Dto.FileUploadDto;
import com.example.TurnKeyGo.Service.DocumentRagModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

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

        return ResponseEntity.ok(map);
    }






}
