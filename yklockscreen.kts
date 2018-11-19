#!/usr/bin/env kscript

import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.lang.Thread.sleep

println("yklockscreen starting up")

val systemProfilerBuilder = ProcessBuilder()
systemProfilerBuilder.command("system_profiler", "SPUSBDataType")

systemProfilerBuilder.directory(File(System.getProperty("user.home")))
val pmsetBuilder = ProcessBuilder()
pmsetBuilder.command("pmset", "displaysleepnow")

pmsetBuilder.directory(File(System.getProperty("user.home")))
while (true) {
    println("Waiting for Yubikey")

    while (!isYubikeyPresent()) {
        sleep(2_000)
    }

    println("Armed. Waiting for removal...")

    while (isYubikeyPresent()) {
        sleep(2_000)
    }

    println("Removed. Locking screen..")
    sleepDisplay()
}



fun isYubikeyPresent(): Boolean {
    val process = systemProfilerBuilder.start()

    val present = BufferedReader(InputStreamReader(process.inputStream)).lines().anyMatch {
        it.toLowerCase().contains("yubikey")
    }

    process.waitFor()
    return present
}

fun sleepDisplay() {
    val process = pmsetBuilder.start()
    process.waitFor()
}