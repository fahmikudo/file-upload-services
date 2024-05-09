package com.jetdevs.test.fileuploadservices.repository;

import com.jetdevs.test.fileuploadservices.entity.FileUploadTaskLogActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileUploadTaskLogActivityRepository extends JpaRepository<FileUploadTaskLogActivity, Long> {
}
