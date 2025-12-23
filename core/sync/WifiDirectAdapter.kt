package core.sync

import core.audit.MissionLogger
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.ServerSocket
import java.net.Socket
import kotlin.concurrent.thread

/**
 * SRC - WifiDirectAdapter (Simulé/Alpha)
 * Gère le transport des ThreatMessages entre deux terminaux FARDC.
 */
class WifiDirectAdapter(private val localPort: Int, private val remotePeerIp: String?, private val remotePort: Int) : TransportAdapter {

    private var isRunning = false

    override fun send(msg: ThreatMessage): Boolean {
        if (remotePeerIp == null) return false
        
        return try {
            val socket = Socket(remotePeerIp, remotePort)
            socket.soTimeout = 2000 // Timeout court pour rester opportuniste
            val out = ObjectOutputStream(socket.getOutputStream())
            
            // Simulation de priorité : on pourrait compresser ici
            out.writeObject(msg)
            out.flush()
            
            socket.close()
            true
        } catch (e: Exception) {
            MissionLogger.warning("Mesh Send Failed to $remotePeerIp: ${e.message}")
            false
        }
    }

    override fun receive(handler: (ThreatMessage) -> Unit) {
        if (isRunning) return
        isRunning = true

        thread(priority = Thread.NORM_PRIORITY, name = "MeshListener") {
            try {
                val serverSocket = ServerSocket(localPort)
                MissionLogger.info("Mesh Listener started on port $localPort")

                while (isRunning) {
                    val clientSocket = serverSocket.accept()
                    thread {
                        try {
                            val input = ObjectInputStream(clientSocket.getInputStream())
                            val msg = input.readObject() as ThreatMessage
                            handler(msg)
                        } catch (e: Exception) {
                            MissionLogger.warning("Mesh Receive Error: ${e.message}")
                        } finally {
                            clientSocket.close()
                        }
                    }
                }
            } catch (e: Exception) {
                MissionLogger.critical("Mesh Server Socket Crash: ${e.message}")
            }
        }
    }

    fun stop() {
        isRunning = false
    }
}
