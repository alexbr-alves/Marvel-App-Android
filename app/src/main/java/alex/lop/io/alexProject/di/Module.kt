package alex.lop.io.alexProject.di

import alex.lop.io.alexProject.data.remote.ServiceApi
import alex.lop.io.alexProject.util.Constants.API_KEY
import alex.lop.io.alexProject.util.Constants.BASE_URL
import alex.lop.io.alexProject.util.Constants.HASH
import alex.lop.io.alexProject.util.Constants.PRIVATE_KEY
import alex.lop.io.alexProject.util.Constants.PUBLIC_KEY
import alex.lop.io.alexProject.util.Constants.TS
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Singleton
    @Provides
    fun provideOkHttpClient() : OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient().newBuilder()
            .addInterceptor { chain ->
                val currentTimesTamp = System.currentTimeMillis()
                val newUrl = chain.request().url
                    .newBuilder()
                    .addQueryParameter(TS, currentTimesTamp.toString())
                    .addQueryParameter(API_KEY, PUBLIC_KEY)
                    .addQueryParameter(
                        HASH,
                        provideToMd5Hash(currentTimesTamp.toString() + PUBLIC_KEY + PRIVATE_KEY)
                    )
                    .build()

                val request = chain.request()
                    .newBuilder()
                    .url(newUrl)
                    .build()
                chain.proceed(request)
            }
            .addInterceptor(logging)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(client : OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    fun provideServiceApi(retrofit : Retrofit) : ServiceApi {
        return retrofit.create(ServiceApi::class.java)
    }

    private fun provideToMd5Hash(encrypted : String) : String {
        var pass = encrypted
        var encryptedString : String? = null
        val md5 : MessageDigest
        try {
            md5 = MessageDigest.getInstance("MD5")
            md5.update(pass.toByteArray(), 0, pass.length)
            pass = BigInteger(1, md5.digest()).toString(16)
            while (pass.length < 32) {
                pass = "0$pass"
            }
            encryptedString = pass
        } catch (e1 : NoSuchAlgorithmException) {
            e1.printStackTrace()
        }
        Timber.d("hash -> $encryptedString")
        return encryptedString ?: ""
    }

}