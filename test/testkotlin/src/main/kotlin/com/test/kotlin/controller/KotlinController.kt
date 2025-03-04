package com.test.kotlin.controller

import com.test.kotlin.domain.TableOneDomain
import com.test.kotlin.entity.TableOne
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException
import org.springframework.web.bind.annotation.*
import pers.acp.core.log.LogFactory
import pers.acp.springboot.core.enums.ResponseCode
import pers.acp.springboot.core.exceptions.ServerException

/**
 * @author zhangbin by 28/04/2018 13:06
 * @since JDK1.8
 */
@RestController
class KotlinController(private val tableOneDomain: TableOneDomain) {

    @GetMapping("/query", produces = [(MediaType.APPLICATION_JSON_UTF8_VALUE)])
    @Throws(ServerException::class, JpaObjectRetrievalFailureException::class)
    fun doQuery(@RequestParam name: String): ResponseEntity<TableOne> =
            try {
                tableOneDomain.query(name)
                        .let {
                            if (it.isPresent) {
                                ResponseEntity.ok(it.get())
                            } else {
                                throw ServerException(ResponseCode.DBError.value, "没有记录")
                            }
                        }
            } catch (e: Exception) {
                log.error(e.message ?: "", e)
                if (e !is ServerException) throw ServerException(ResponseCode.serviceError)
                throw e
            }

    @PostMapping("/add", produces = [(MediaType.APPLICATION_JSON_UTF8_VALUE)])
    @Throws(ServerException::class, JpaObjectRetrievalFailureException::class)
    fun doAdd(@RequestBody tableOne: TableOne): ResponseEntity<TableOne> =
            try {
                tableOneDomain.create(tableOne)
                        .let { ResponseEntity.status(HttpStatus.CREATED).body(it) }
            } catch (e: Exception) {
                log.error(e.message ?: "", e)
                if (e !is ServerException) throw ServerException(ResponseCode.serviceError)
                throw e
            }

    private val log: LogFactory = LogFactory.getInstance(this.javaClass)

}