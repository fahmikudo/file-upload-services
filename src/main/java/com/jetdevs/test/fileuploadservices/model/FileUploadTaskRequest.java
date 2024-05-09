package com.jetdevs.test.fileuploadservices.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FileUploadTaskRequest {

    private MultipartFile file;

}
