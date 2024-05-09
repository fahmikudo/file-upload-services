package com.jetdevs.test.fileuploadservices.service.impl;

import com.jetdevs.test.fileuploadservices.entity.FileUploadTask;
import com.jetdevs.test.fileuploadservices.entity.FileUploadTaskLogActivity;
import com.jetdevs.test.fileuploadservices.entity.FileUploadTaskStatus;
import com.jetdevs.test.fileuploadservices.entity.User;
import com.jetdevs.test.fileuploadservices.model.FileUploadTaskRequest;
import com.jetdevs.test.fileuploadservices.model.FileUploadTaskResponse;
import com.jetdevs.test.fileuploadservices.model.ReviewFileRequest;
import com.jetdevs.test.fileuploadservices.repository.FileUploadTaskLogActivityRepository;
import com.jetdevs.test.fileuploadservices.repository.FileUploadTaskRepository;
import com.jetdevs.test.fileuploadservices.service.FileUploadTaskService;
import com.jetdevs.test.fileuploadservices.util.CommonFunction;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileUploadTaskServiceImpl implements FileUploadTaskService {

    private static final String FILE_NOT_FOUND_MESSAGE = "File Upload Task not found";
    private final Path root = Paths.get("files");
    private final FileUploadTaskRepository fileUploadTaskRepository;
    private final FileUploadTaskLogActivityRepository fileUploadTaskLogActivityRepository;


    @Override
    @Transactional(rollbackFor = {Exception.class, ResponseStatusException.class})
    public FileUploadTaskResponse uploadFileTask(FileUploadTaskRequest fileUploadTaskRequest, User user) {
        if (fileUploadTaskRequest.getFile() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File cannot be null");
        }

        try {
            String fileName = fileUploadTaskRequest.getFile().getOriginalFilename();
            String fileType = fileUploadTaskRequest.getFile().getContentType();
            Path filePath = root.resolve(System.currentTimeMillis() + "_" + fileName);

            Files.copy(fileUploadTaskRequest.getFile().getInputStream(), filePath);

            FileUploadTask fileUploadTask = new FileUploadTask();
            fileUploadTask.setFileName(fileName);
            fileUploadTask.setFileType(fileType);
            fileUploadTask.setFilePath(filePath.toAbsolutePath().toString());
            fileUploadTask.setStatus(FileUploadTaskStatus.UPLOADED.name());
            fileUploadTask.setCreationTime(Instant.now().toEpochMilli());
            fileUploadTask.setCreatedBy(user.getUsername());


            FileUploadTask savedFileUploadTask = fileUploadTaskRepository.save(fileUploadTask);

            logUserActivity(savedFileUploadTask, user, new ReviewFileRequest("UPLOAD_FILE"));

            return toFileUploadTaskResponse(savedFileUploadTask);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public FileUploadTaskResponse checkStatusFileUploadTask(Long id) {

        Optional<FileUploadTask> fileUploadTask = fileUploadTaskRepository.findById(id);
        if (fileUploadTask.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, FILE_NOT_FOUND_MESSAGE);
        }

        return toFileUploadTaskResponse(fileUploadTask.get());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FileUploadTaskResponse> getAllFileUploadTasks(Integer page, Integer size, String orderBy, String sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(CommonFunction.getSortDirection(orderBy), sort));

        Page<FileUploadTask> fileUploadTasks = fileUploadTaskRepository.findAll(pageable);
        List<FileUploadTaskResponse> fileUploadTaskResponses = fileUploadTasks.stream()
                .map(this::toFileUploadTaskResponse)
                .toList();

        return new PageImpl<>(fileUploadTaskResponses, pageable, fileUploadTasks.getTotalElements());
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, ResponseStatusException.class})
    public FileUploadTaskResponse reviewFile(Long id, ReviewFileRequest reviewFileRequest, User user) {
        Optional<FileUploadTask> fileUploadTask = fileUploadTaskRepository.findById(id);
        if (fileUploadTask.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, FILE_NOT_FOUND_MESSAGE);
        }

        fileUploadTask.get().setLastAccessTime(Instant.now().toEpochMilli());
        fileUploadTask.get().setLastUpdatedTime(Instant.now().toEpochMilli());
        fileUploadTask.get().setLastUpdatedBy(user.getUsername());
        fileUploadTaskRepository.save(fileUploadTask.get());

        logUserActivity(fileUploadTask.get(), user, reviewFileRequest);

        return toFileUploadTaskResponse(fileUploadTask.get());
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, ResponseStatusException.class})
    public FileUploadTaskResponse deleteFile(Long id, User user) {
        try {
            Optional<FileUploadTask> fileUploadTask = fileUploadTaskRepository.findById(id);
            if (fileUploadTask.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, FILE_NOT_FOUND_MESSAGE);
            }
            Path file = root.resolve(fileUploadTask.get().getFilePath());
            Files.deleteIfExists(file);
            logUserActivity(fileUploadTask.get(), user, new ReviewFileRequest("DELETED_FILE"));
            fileUploadTaskRepository.delete(fileUploadTask.get());
            return toFileUploadTaskResponse(fileUploadTask.get());
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private void logUserActivity(FileUploadTask fileUploadTask, User user, ReviewFileRequest reviewFileRequest) {
        FileUploadTaskLogActivity fileUploadTaskLogActivity = new FileUploadTaskLogActivity();
        fileUploadTaskLogActivity.setActivityName(reviewFileRequest.getActivityName());
        fileUploadTaskLogActivity.setReferenceId(String.valueOf(fileUploadTask.getId()));
        fileUploadTaskLogActivity.setUser(user);
        fileUploadTaskLogActivity.setCreatedBy(user.getUsername());
        fileUploadTaskLogActivity.setCreationTime(Instant.now().toEpochMilli());
        fileUploadTaskLogActivity.setLastUpdatedBy(user.getUsername());
        fileUploadTaskLogActivity.setLastUpdatedTime(Instant.now().toEpochMilli());

        fileUploadTaskLogActivityRepository.save(fileUploadTaskLogActivity);
    }


    private FileUploadTaskResponse toFileUploadTaskResponse(FileUploadTask fileUploadTask) {
        return FileUploadTaskResponse.builder()
                .id(fileUploadTask.getId())
                .fileName(fileUploadTask.getFileName())
                .fileType(fileUploadTask.getFileType())
                .filePath(fileUploadTask.getFilePath())
                .status(fileUploadTask.getStatus())
                .build();
    }
}
