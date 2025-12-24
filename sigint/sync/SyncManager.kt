package sigint.sync

import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class SyncManager {
    private val scheduler = Executors.newScheduledThreadPool(1)

    fun scheduleScan(task: () -> Unit, initialDelaySec: Long = 1, periodSec: Long = 30) {
        scheduler.scheduleAtFixedRate({ task() }, initialDelaySec, periodSec, TimeUnit.SECONDS)
    }

    fun shutdown() = scheduler.shutdown()
}
