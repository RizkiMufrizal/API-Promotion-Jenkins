package org.rizki.mufrizal.api.manager.promotion

import org.apache.http.HttpHeaders
import org.apache.http.client.methods.HttpPost
import org.apache.http.conn.ssl.NoopHostnameVerifier
import org.apache.http.entity.ContentType
import org.apache.http.entity.mime.HttpMultipartMode
import org.apache.http.entity.mime.MultipartEntityBuilder
import org.apache.http.entity.mime.content.FileBody
import org.apache.http.entity.mime.content.StringBody
import org.apache.http.impl.client.HttpClients
import org.apache.http.ssl.SSLContextBuilder
import java.io.File
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

object APIManagerPromotion {
    @JvmStatic
    fun main(args: Array<String>) {
        val fileReplace = "D:\\devops\\url.txt"
        val fileName = "D:\\devops\\api-export.dat"

        val path = Paths.get(fileName)
        val charset = StandardCharsets.UTF_8
        var content = String(Files.readAllBytes(path), charset)

        File(fileReplace).forEachLine {
            content = content.replace(it.split(",")[0], it.split(",")[1])
        }
        Files.write(path, content.toByteArray(charset))

        val fileUpload = File(fileName)
        val fileBody = FileBody(fileUpload, ContentType.DEFAULT_BINARY)
        val organizationId = StringBody("17d032e3-77e9-4878-a282-a502579156be", ContentType.MULTIPART_FORM_DATA)

        val sslContext = SSLContextBuilder().loadTrustMaterial { _, _ -> true }.build()

        val httpClientBuilder = HttpClients.custom().setSSLContext(sslContext).setSSLHostnameVerifier(NoopHostnameVerifier()).build()

        val httpPost = HttpPost("https://localhost:8075/api/portal/v1.3/proxies/import")

        val multipartEntityBuilder = MultipartEntityBuilder.create()
        multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
        multipartEntityBuilder.addPart("file", fileBody)
        multipartEntityBuilder.addPart("organizationId", organizationId)

        val entity = multipartEntityBuilder.build()
        httpPost.entity = entity

        val usernamePassword = "apiadmin:changeme"
        val usernamePasswordBase64 = Base64.getEncoder().encode(usernamePassword.toByteArray(Charset.defaultCharset()))
        val basicUsernamePasswordBase64 = "Basic ${usernamePasswordBase64.toString(Charset.defaultCharset())}"

        httpPost.addHeader(HttpHeaders.AUTHORIZATION, basicUsernamePasswordBase64)
        val httpResponse = httpClientBuilder.execute(httpPost)

        println(httpResponse)
    }
}