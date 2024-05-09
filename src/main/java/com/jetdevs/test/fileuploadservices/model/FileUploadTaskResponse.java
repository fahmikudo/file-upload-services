package com.jetdevs.test.fileuploadservices.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FileUploadTaskResponse {

    private Long id;
    private String fileName;
    private String filePath;
    private String fileType;
    private String status;

}
