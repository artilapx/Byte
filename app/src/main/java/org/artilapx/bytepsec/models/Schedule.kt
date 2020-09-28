package org.artilapx.bytepsec.models

data class Schedule(
        val date: String,
        val from: String,
        val to: String,
        val subject: String,
        val Teacher: String,
        val Cabinet: String,
        val group_id: Int)