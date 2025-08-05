package com.nacho.uala.challenge.domain.util

import androidx.sqlite.SQLiteException
import com.google.gson.JsonSyntaxException
import retrofit2.HttpException
import java.net.UnknownHostException

suspend fun <T> safeCall(block: suspend () -> T): Result<T> {
    return try {
        Result.Success(block())
    } catch (e: HttpException) {
        Result.Error.Network(e)
    } catch (e: UnknownHostException) {
        Result.Error.Network(e)
    } catch (e: JsonSyntaxException) {
        Result.Error.Network(e)
    } catch (e: SQLiteException) {
        Result.Error.Database(e)
    } catch (e: Exception) {
        Result.Error.Unknown(e)
    }
}