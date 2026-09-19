package com.example.TurnKeyGo.Service;

import com.example.TurnKeyGo.Dto.FileUploadDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentRagModelService {

//    private final EmbeddingModel embeddingModel;
    private final VectorStore vectorStore;

    public void Ingest(FileUploadDto dto) {
        if(dto.getFile().isEmpty()) throw new RuntimeException("File is Empty !");

        TokenTextSplitter tokenTextSplitter= TokenTextSplitter.builder()     //also have some default value
                .withChunkSize(200)
                .withMinChunkSizeChars(50)
                .withMinChunkLengthToEmbed(5)
                .withMaxNumChunks(10000)
                .withKeepSeparator(true)
                .build();
        PagePdfDocumentReader reader=new PagePdfDocumentReader(dto.getFile().getResource());
        List<Document> doc=reader.get();
        List<Document> chunked=tokenTextSplitter.apply(doc);
        for(Document chunk:chunked)   {

            log.info(chunk.toString());
        }
        vectorStore.add(chunked);
    }

}
