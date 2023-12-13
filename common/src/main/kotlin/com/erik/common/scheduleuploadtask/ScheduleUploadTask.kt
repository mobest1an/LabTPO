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
    var status: TaskStatus,
    @DateTimeFormat(pattern = "yyyy-MM-ddTHH:mm:ssZ")
    val startedAt: Date,
    @ManyToMany(
        fetch = FetchType.EAGER,
    )
    @JoinTable(
        name = "upload_task_to_schedule",
        joinColumns = [JoinColumn(name = "task_id")],
        inverseJoinColumns = [JoinColumn(name = "schedule_id")],
    )
    var schedules: MutableSet<Schedule>? = null,
)

enum class TaskStatus {
    SUCCESS,
    ERROR,
    IN_PROGRESS,
}
