package com.jetdevs.test.fileuploadservices.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "file_upload_task_log_activities")
@Data
public class FileUploadTaskLogActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "activity_name")
    private String activityName;

    @Column(name = "creation_time")
    private Long creationTime;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "last_updated_time")
    private Long lastUpdatedTime;

    @Column(name = "last_updated_by")
    private String lastUpdatedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "reference_id")
    private String referenceId;

}