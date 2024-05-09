package com.jetdevs.test.fileuploadservices.service;

import com.jetdevs.test.fileuploadservices.entity.User;
import com.jetdevs.test.fileuploadservices.model.FileUploadTaskRequest;
import com.jetdevs.test.fileuploadservices.model.FileUploadTaskResponse;
import com.jetdevs.test.fileuploadservices.model.ReviewFileRequest;
import org.springframework.data.domain.Page;

public interface FileUploadTaskService {

    FileUploadTaskResponse uploadFileTask(FileUploadTaskRequest fileUploadTaskRequest, User user);

    FileUploadTaskResponse checkStatusFileUploadTask(Long id);

    Page<FileUploadTaskResponse> getAllFileUploadTasks(Integer page, Integer size, String orderBy, String sort);

    FileUploadTaskResponse reviewFile(Long id, ReviewFileRequest reviewFileRequest, User user);

    FileUploadTaskResponse deleteFile(Long id, User user);

}
