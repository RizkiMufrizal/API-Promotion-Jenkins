package org.rizki.mufrizal.api.manager.promotion.reader

import io.github.cdimascio.dotenv.Dotenv

/**
 *
 * @Author Rizki Mufrizal <mufrizalrizki@gmail.com>
 * @Web <https://RizkiMufrizal.github.io>
 * @Since 21 December 2017
 * @Time 9:53 AM
 * @Project API-Promotion-Jenkins
 * @Package org.rizki.mufrizal.api.manager.promotion.reader
 * @File ReadFileEnv
 *
 */
class ReadFileEnv constructor(val path: String? = null) {
    private fun getFileEnv(): Dotenv? {
        return ReadFileEnv().path?.let {
            Dotenv
                    .configure()
                    .directory(it)
                    .ignoreIfMalformed()
                    .ignoreIfMissing()
                    .build()
        }
    }

    fun getUsername(): String? {
        return this.getFileEnv()?.get("ENV.APIMANAGER.USERNAME")
    }

    fun getPassword(): String? {
        return this.getFileEnv()?.get("ENV.APIMANAGER.PASSWORD")
    }

    fun getUrl(): String? {
        return this.getFileEnv()?.get("ENV.APIMANAGER.URL")
    }

    fun getOrganizationId(): String? {
        return this.getFileEnv()?.get("ENV.APIMANAGER.ORGANIZATIONID")
    }

    fun getFileReplace(): String? {
        return this.getFileEnv()?.get("ENV.APIMANAGER.PROMOTION.FILEREPLACE")
    }

    fun getFileExport(): String? {
        return this.getFileEnv()?.get("ENV.APIMANAGER.PROMOTION.FILEEXPORT")
    }
}