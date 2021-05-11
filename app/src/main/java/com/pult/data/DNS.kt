package com.pult.data

import android.os.StrictMode
import android.util.Log
import java.io.IOException
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

class DNS (
    body : String,
    var remoteHost: String
) {

    init {
        Log.d("my", "DNS start >>> ")
        val threadWithRunnable = Thread(udp_DataArrival())
        threadWithRunnable.start()
        sendUDP(body)
    }

//    var remoteHost: String = "192.168.1.255"
//    var remotePort: Int = 6454
    var remotePort: Int = 53


    private fun sendUDP(messageStr: String) {
        // Hack Prevent crash (sending should be done using an async task)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        try {
            //Open a port to send the package
            val socket = DatagramSocket()
            socket.broadcast = true
            val sendData = messageStr.toByteArray()
            val sendPacket = DatagramPacket(sendData, sendData.size, InetAddress.getByName(remoteHost), remotePort)
            socket.send(sendPacket)
            println("fun sendBroadcast: packet sent to: " + InetAddress.getByName(remoteHost) + ":" + remotePort)
        } catch (e: IOException) {
            //            Log.e(FragmentActivity.TAG, "IOException: " + e.message)
        }
    }

    fun receiveUDP() {
        val buffer = ByteArray(2048)
        var socket: DatagramSocket? = null
        try {
            //Keep a socket open to listen to all the UDP trafic that is destined for this port
            socket = DatagramSocket(remotePort, InetAddress.getByName(remoteHost))
            socket.broadcast = true
            val packet = DatagramPacket(buffer, buffer.size)
            socket.receive(packet)
            println("open fun receiveUDP packet received = " + packet.data)

        } catch (e: Exception) {
            println("open fun receiveUDP catch exception." + e.toString())
            e.printStackTrace()
        } finally {
            socket?.close()
        }
    }

    inner class udp_DataArrival: Runnable {
        override fun run() {
            println("${Thread.currentThread()} Runnable Thread Started.")
            while (true){
                receiveUDP()
            }
        }
    }

}




