package org.artilapx.bytepsec.models

import kotlin.properties.Delegates

/*data class Schedule(
        val date: String,
        val from: String,
        val to: String,
        val subject: String,
        val Teacher: String,
        val Cabinet: String,
        val group_id: Int)*/
class Schedule {
    private lateinit var date: String
    lateinit var from: String
    lateinit var to: String
    lateinit var subject: String
    lateinit var Teacher: String
    lateinit var Cabinet: String
    var group_id by Delegates.notNull<Int>()

    constructor(date: String, from: String, to: String, subject: String, Teacher: String, Cabinet: String, group_id: Int) {
        this.date = date
        this.from = from
        this.to = to
        this.subject = subject
        this.Teacher = Teacher
        this.Cabinet = Cabinet
        this.group_id = group_id
    }

    constructor()
}