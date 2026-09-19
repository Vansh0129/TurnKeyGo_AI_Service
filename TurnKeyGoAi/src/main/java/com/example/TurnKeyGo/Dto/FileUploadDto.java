package com.example.TurnKeyGo.Dto;

import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.util.file.ConfigurationSource;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class FileUploadDto {
    MultipartFile file;
}
