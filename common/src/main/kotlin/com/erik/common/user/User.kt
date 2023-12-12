package com.erik.common.user

import javax.persistence.*

@Entity
@Table(name = "user")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val username: String,
    val password: String,
    @ElementCollection
    @CollectionTable(
        name = "roles",
        joinColumns = [JoinColumn(name = "user_id")]
    )
    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    val roles: MutableSet<Role>,
)

enum class Role(
    val permissions: Array<String>
) {
    USER(arrayOf("DEFAULT")),
    ADMIN(arrayOf("DEFAULT", "SCHEDULE_EDIT")),
    SUPERUSER(arrayOf("DEFAULT", "SCHEDULE_EDIT", "MARKET_EDIT")),
}
