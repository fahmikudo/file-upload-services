package com.jetdevs.test.fileuploadservices.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "file_upload_tasks")
@Data
public class FileUploadTask {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "status")
    private String status;

    @Column(name = "last_access_time")
    private Long lastAccessTime;

    @Column(name = "creation_time")
    private Long creationTime;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "last_updated_time")
    private Long lastUpdatedTime;

    @Column(name = "last_updated_by")
    private String lastUpdatedBy;



}
