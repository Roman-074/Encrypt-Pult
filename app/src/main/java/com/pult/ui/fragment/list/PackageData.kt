package com.pult.ui.fragment.list

import android.util.Log
import com.pult.encryption.RSA
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.nio.charset.StandardCharsets.UTF_8
import java.util.*
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream

class PackageData {

    fun startPackage(list: ArrayList<String>){
        //1) Создаем из массива JSON
        val point1 = convertArrayToJSON(list)
        //2) Проходимся по нему GZIP
        val point2 = compressGZIP(point1.toString())
        //3) Шифруем закрытым ключом RSA 2048 бит и преобразуем в Base64
        val point3 = RSA.encryptBytesByPrivateKey(point2)
        //4) Разбираем на строки по 240 символов
        val result = split240(point3)
        //5) Сохраняем в SP для последующей отправки по DNS

        Log.d("my", "RSA >> BASE64: $point3")
        Log.d("my", "result: $result")
    }

    private fun convertArrayToJSON(list: ArrayList<String>) : JSONObject{
        val myObject = JSONObject()
        list.forEachIndexed { index, s ->
            if (index == 0)
                myObject.put("timestamp", Date().time.toString())
            myObject.put((index+1).toString(), s)
        }
        return myObject
    }

    private fun compressGZIP(content: String): ByteArray {
        val bos = ByteArrayOutputStream()
        GZIPOutputStream(bos).bufferedWriter(UTF_8).use { it.write(content) }
        return bos.toByteArray()
    }

    private fun split240(inputList: String) : MutableMap<String, String> {
        val map = mutableMapOf<String, String>()
        val arrayStrings = inputList.chunked(240) as ArrayList<String>
        arrayStrings.forEachIndexed { index, s ->
            when {
                index < 10 -> map["000$index"] = s
                index in 10..99 -> map["00$index"] = s
                index in 100..999 -> map["0$index"] = s
                else -> map["$index"] = s
            }
        }
        return map
    }

    // Преобразования в обратную сторону
    fun ungzip(content: ByteArray): String =
        GZIPInputStream(content.inputStream()).bufferedReader(UTF_8).use { it.readText() }


//    fun encodeBase64(args: Array<String>) {
//        val oriString = "bezkoder tutorial"
////        val encodedString: String = Base64.getEncoder().encodeToString(oriString.toByteArray())
//        val encodedString: String = Base64.encodeToString(oriString.toByteArray(), Base64.DEFAULT)
//
//        println(encodedString)
//    }

}