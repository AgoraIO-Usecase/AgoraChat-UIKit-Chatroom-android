package io.agora.chat.uikit.chatroom.http

import android.app.Application
import io.agora.chat.uikit.chatroom.utils.SPUtils
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val context = io.agora.chat.uikit.chatroom.ChatroomUIKitClient.getInstance().getContext().context
            ?: return chain.proceed(chain.request())
        val token = SPUtils.getInstance(context.applicationContext as Application).getToken()
        if (token.isEmpty()) {
            return chain.proceed(chain.request())
        }
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer $token")
            .build()
        return chain.proceed(newRequest)
    }
}