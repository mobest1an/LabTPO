package com.erik.common.scheduleuploadtask

import com.erik.common.market.Schedule
import org.springframework.format.annotation.DateTimeFormat
import java.util.Date
import javax.persistence.*

@Entity
class ScheduleUploadTask(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @Enumerated(EnumType.STRING)
    val status: TaskStatus,
    @DateTimeFormat(pattern = "yyyy-MM-ddTHH:mm:ssZ")
    val startedAt: Date,
    @ManyToOne
    @JoinColumn(
        name = "schedule_id"
    )
    val schedule: Schedule,
)

enum class TaskStatus {
    SUCCESS,
    ERROR,
    IN_PROGRESS,
}