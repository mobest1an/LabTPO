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
    @DateTimeFormat(pattern = "yyyy-MM-ddTHH:mm:ssZ")
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
) {
    @OneToMany(
        mappedBy = "schedule",
        cascade = [CascadeType.ALL],
        fetch = FetchType.EAGER,
        orphanRemoval = true,
    )
    lateinit var weekDays: MutableSet<WeekDay>

    @DateTimeFormat(pattern = "yyyy-MM-ddTHH:mm:ssZ")
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
    val dayNumber: Int,
    @DateTimeFormat(pattern = "yyyy-MM-ddTHH:mm:ssZ")
    val startTime: Date? = null,
    @DateTimeFormat(pattern = "yyyy-MM-ddTHH:mm:ssZ")
    val endTime: Date? = null,
    @ManyToOne
    @JoinColumn(
        name = "schedule_id"
    )
    val schedule: Schedule,
)
