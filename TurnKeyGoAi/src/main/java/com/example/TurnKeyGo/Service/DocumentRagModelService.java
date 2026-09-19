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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentRagModelService {

    private final EmbeddingModel embeddingModel;
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
        List<Document> doc=new ArrayList<>();
        for (Document page : reader.get()) {

            String cleanedText = page.getText()
                    .replaceAll("\\s+", " ")
                    .trim();

            Document cleanedPage =
                    new Document(cleanedText, page.getMetadata());

            doc.add(cleanedPage);
        }

//        doc=doc.stream().map(text -> text.getText().replaceAll("\\s+", " ").trim()).toList();
        List<Document> chunked=tokenTextSplitter.apply(doc);
        for(Document chunk:chunked)   {

            log.info(chunk.toString());
        }
        vectorStore.add(chunked);
    }

    public List<String> SimilaritySearch(String request ,Double threshold,Integer topK) {
        List<Document> search=vectorStore.similaritySearch(
                SearchRequest.builder()
                        .query(request)
                        .topK(topK)
                        .similarityThreshold(threshold)
                        .build()
        );
        return search.stream()
                .map(Document::getText)
                .filter(Objects::nonNull)
                .map(text -> text.replaceAll("\\s+", " ").trim())
                .toList();

    }



}
