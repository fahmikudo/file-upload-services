package com.jetdevs.test.fileuploadservices.repository;

import com.jetdevs.test.fileuploadservices.entity.FileUploadTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileUploadTaskRepository extends JpaRepository<FileUploadTask, Long> {

}
