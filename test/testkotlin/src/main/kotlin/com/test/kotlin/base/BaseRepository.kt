package com.test.kotlin.base

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.NoRepositoryBean

/**
 * @author zhangbin by 28/04/2018 13:04
 * @since JDK1.8
 */
@NoRepositoryBean
interface BaseRepository<T, ID> : JpaSpecificationExecutor<T>, JpaRepository<T, ID>