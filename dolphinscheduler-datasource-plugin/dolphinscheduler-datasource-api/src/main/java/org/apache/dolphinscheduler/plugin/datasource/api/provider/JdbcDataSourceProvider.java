/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.dolphinscheduler.plugin.datasource.api.provider;

import org.apache.dolphinscheduler.plugin.datasource.api.utils.PasswordUtils;
import org.apache.dolphinscheduler.spi.datasource.BaseConnectionParam;
import org.apache.dolphinscheduler.spi.utils.Constants;
import org.apache.dolphinscheduler.spi.utils.PropertyUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * Jdbc Data Source Provider
 */
public class JdbcDataSourceProvider {

    private static final Logger logger = LoggerFactory.getLogger(JdbcDataSourceProvider.class);

    public static DruidDataSource createJdbcDataSource(BaseConnectionParam properties) {
        logger.info("Creating DruidDataSource pool for maxActive:{}", PropertyUtils.getInt(Constants.SPRING_DATASOURCE_MAX_ACTIVE, 50));

        DruidDataSource druidDataSource = new DruidDataSource();

        druidDataSource.setDriverClassName(properties.getDriverClassName());
        druidDataSource.setUrl(properties.getJdbcUrl());
        druidDataSource.setUsername(properties.getUser());
        druidDataSource.setPassword(PasswordUtils.decodePassword(properties.getPassword()));

        druidDataSource.setMinIdle(PropertyUtils.getInt(Constants.SPRING_DATASOURCE_MIN_IDLE, 5));
        druidDataSource.setMaxActive(PropertyUtils.getInt(Constants.SPRING_DATASOURCE_MAX_ACTIVE, 50));
        druidDataSource.setTestOnBorrow(PropertyUtils.getBoolean(Constants.SPRING_DATASOURCE_TEST_ON_BORROW, false));

        if (properties.getProps() != null) {
            properties.getProps().forEach(druidDataSource::addConnectionProperty);
        }

        logger.info("Creating DruidDataSource pool success.");
        return druidDataSource;
    }

    /**
     * @return One Session Jdbc DataSource
     */
    public static DruidDataSource createOneSessionJdbcDataSource(BaseConnectionParam properties) {
        logger.info("Creating OneSession DruidDataSource pool for maxActive:{}", PropertyUtils.getInt(Constants.SPRING_DATASOURCE_MAX_ACTIVE, 50));

        DruidDataSource druidDataSource = new DruidDataSource();

        druidDataSource.setDriverClassName(properties.getDriverClassName());
        druidDataSource.setUrl(properties.getJdbcUrl());
        druidDataSource.setUsername(properties.getUser());
        druidDataSource.setPassword(PasswordUtils.decodePassword(properties.getPassword()));

        druidDataSource.setMinIdle(1);
        druidDataSource.setMaxActive(1);
        druidDataSource.setTestOnBorrow(true);

        if (properties.getProps() != null) {
            properties.getProps().forEach(druidDataSource::addConnectionProperty);
        }

        logger.info("Creating OneSession DruidDataSource pool success.");
        return druidDataSource;
    }

}
