package com.jetdevs.test.fileuploadservices.controller;

import com.jetdevs.test.fileuploadservices.entity.User;
import com.jetdevs.test.fileuploadservices.model.FileUploadTaskRequest;
import com.jetdevs.test.fileuploadservices.model.FileUploadTaskResponse;
import com.jetdevs.test.fileuploadservices.model.ReviewFileRequest;
import com.jetdevs.test.fileuploadservices.service.AuthenticationService;
import com.jetdevs.test.fileuploadservices.service.FileUploadTaskService;
import com.jetdevs.test.fileuploadservices.util.CommonFunction;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/files")
public class FileUploadTaskController extends BaseController {

    private final FileUploadTaskService fileUploadTaskService;

    public FileUploadTaskController(AuthenticationService authenticationService, FileUploadTaskService fileUploadTaskService) {
        super(authenticationService);
        this.fileUploadTaskService = fileUploadTaskService;
    }

    @PostMapping(value = "/uploads", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<FileUploadTaskResponse> uploadFile(@RequestParam("file") MultipartFile file) {

        User user = getUserActiveFromContext();

        FileUploadTaskRequest request = new FileUploadTaskRequest();
        request.setFile(file);

        FileUploadTaskResponse fileUploadTaskResponse = fileUploadTaskService.uploadFileTask(request, user);

        return new ResponseEntity<>(fileUploadTaskResponse, HttpStatus.OK);
    }

    @GetMapping(value = "/check-progress/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<FileUploadTaskResponse> checkStatusFileUpload(@PathVariable Long id) {
        FileUploadTaskResponse fileUploadTaskResponse = fileUploadTaskService.checkStatusFileUploadTask(id);
        return new ResponseEntity<>(fileUploadTaskResponse, HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<Map<Object, Object>> getAllFileUploadTasks(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                                     @RequestParam(value = "size", defaultValue = "10") Integer size,
                                                                     @RequestParam(value = "sortBy", required = false, defaultValue = "id") String sortBy,
                                                                     @RequestParam(value = "orderBy", required = false, defaultValue = "asc") String orderBy) {

        Page<FileUploadTaskResponse> fileUploadTaskResponses = fileUploadTaskService.getAllFileUploadTasks(page - 1, size, orderBy, sortBy);
        Map<Object, Object> response = new HashMap<>();
        response.put("currentPage", fileUploadTaskResponses.getNumber() + 1);
        response.put("totalPage", fileUploadTaskResponses.getTotalPages());
        response.put("size", fileUploadTaskResponses.getSize());
        response.put("totalRecord", fileUploadTaskResponses.getTotalElements());
        response.put("data", fileUploadTaskResponses.getContent());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(value = "/reviews/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<FileUploadTaskResponse> reviewFileUpload(@PathVariable Long id, @RequestBody ReviewFileRequest reviewFileRequest) {
        User user = getUserActiveFromContext();
        FileUploadTaskResponse fileUploadTaskResponse = fileUploadTaskService.reviewFile(id, reviewFileRequest, user);
        return new ResponseEntity<>(fileUploadTaskResponse, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<FileUploadTaskResponse> deleteFile(@PathVariable Long id) {
        User user = getUserActiveFromContext();
        FileUploadTaskResponse fileUploadTaskResponse = fileUploadTaskService.deleteFile(id, user);
        return new ResponseEntity<>(fileUploadTaskResponse, HttpStatus.OK);
    }


}
