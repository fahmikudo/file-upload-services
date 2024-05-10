package com.jetdevs.test.fileuploadservices.service.impl;

import com.jetdevs.test.fileuploadservices.entity.FileUploadTask;
import com.jetdevs.test.fileuploadservices.entity.FileUploadTaskStatus;
import com.jetdevs.test.fileuploadservices.repository.FileUploadTaskRepository;
import com.jetdevs.test.fileuploadservices.service.FileUploadProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Log4j2
public class FileUploadProcessorImpl implements FileUploadProcessor {

    private final FileUploadTaskRepository fileUploadTaskRepository;

    /**
     * This method for simulate another next action for excel file
     * e.g. add data from excel to database or etc.
     */
    @Override
    @Scheduled(cron = "0 * * * * *")
    public void processFIleTask() {
        log.info("Processing FIle Task ...");
        List<FileUploadTask> allByStatus = fileUploadTaskRepository.findAllByStatus(FileUploadTaskStatus.UPLOADED.name());
        for (FileUploadTask byStatus : allByStatus) {
            // do some process for excel
            byStatus.setStatus(FileUploadTaskStatus.PROCESS.name());
            fileUploadTaskRepository.save(byStatus);

            // do something with excel file and save process to be completed
            boolean isSuccess = false;
            if (isSuccess) {
                byStatus.setStatus(FileUploadTaskStatus.FAILED.name());
            }
            byStatus.setStatus(FileUploadTaskStatus.COMPLETED.name());

            fileUploadTaskRepository.save(byStatus);
        }

    }
}
