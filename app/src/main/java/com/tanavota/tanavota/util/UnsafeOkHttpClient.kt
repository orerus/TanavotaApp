package com.tanavota.tanavota.util

import okhttp3.OkHttpClient
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class UnsafeOkHttpClient : OkHttpClient() {
    companion object {
        fun trustAllCerts(): Array<TrustManager> = arrayOf(
                object : X509TrustManager {
                    override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                        // no op
                    }

                    override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                        // no op
                    }

                    override fun getAcceptedIssuers(): Array<X509Certificate> {
                        return arrayOf()
                    }
                }
        )

        fun trustAllSslContext(): SSLContext {
            return try {
                val context = SSLContext.getInstance("SSL")
                context.init(null, trustAllCerts(), SecureRandom())
                context
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }

        private fun trustAllSslSocketFactory(): SSLSocketFactory {
            return trustAllSslContext().socketFactory
        }

        fun getUnsafeOkHttpClient(): OkHttpClient {
            return OkHttpClient().newBuilder().also {
                val sslSocketFactory = it.sslSocketFactory(trustAllSslSocketFactory(), trustAllCerts()[0] as X509TrustManager)
                it.hostnameVerifier { hostname, session -> true }
            }.build()
        }
    }
}