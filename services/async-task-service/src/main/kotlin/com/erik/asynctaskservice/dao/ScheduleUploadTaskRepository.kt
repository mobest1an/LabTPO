package com.erik.asynctaskservice.dao

import com.erik.common.scheduleuploadtask.ScheduleUploadTask
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ScheduleUploadTaskRepository : CrudRepository<ScheduleUploadTask, Long>
