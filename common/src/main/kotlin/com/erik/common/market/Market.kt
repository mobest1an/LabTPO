package com.erik.common.market

import com.erik.common.user.User
import org.springframework.format.annotation.DateTimeFormat
import java.util.*
import javax.persistence.*

@Entity
class Market(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val name: String,
    val description: String,
    val isActive: Boolean,
) {
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    var lastChange: Date? = null

    @ManyToOne
    @JoinColumn(
        name = "last_change_author"
    )
    var lastChangeAuthor: User? = null

    @OneToOne(
        cascade = [CascadeType.ALL],
        mappedBy = "market"
    )
    var schedule: Schedule? = null
}

@Entity
class Schedule(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @OneToOne
    @JoinColumn(
        name = "market_id",
    )
    val market: Market,

    @OneToMany(
        mappedBy = "schedule",
        cascade = [CascadeType.ALL],
        fetch = FetchType.EAGER,
        orphanRemoval = true,
    )
    val weekDays: MutableSet<WeekDay>
) {
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    var lastChange: Date? = null

    @ManyToOne
    @JoinColumn(
        name = "last_change_author"
    )
    var lastChangeAuthor: User? = null
}

@Entity
class WeekDay(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val day: String,
    @DateTimeFormat(pattern = "HH:mm:ss")
    val startTime: Date,
    @DateTimeFormat(pattern = "HH:mm:ss")
    val endTime: Date,
    @ManyToOne
    @JoinColumn(
        name = "schedule_id"
    )
    val schedule: Schedule
)
