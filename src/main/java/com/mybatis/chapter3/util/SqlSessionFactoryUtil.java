package com.mybatis.chapter3.util;

import jdk.internal.dynalink.support.NameCodec;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * SqlSessionFactoryUtil
 * description TODO
 * create by lxj 2018/4/26
 **/
public class SqlSessionFactoryUtil {
    // SqlSessionFactory对象
    private static SqlSessionFactory sqlSessionFactory = null;
    // 类线程锁
    private static final Class CLASS_LOCK = SqlSessionFactoryUtil.class;
    private static Logger log = Logger.getLogger(SqlSessionFactoryUtil.class.getName());

    /**
     * 私有化构造参数
     */
    private SqlSessionFactoryUtil() {
    }

    private static SqlSessionFactory initSqlSessionFactory() {
        InputStream cfgtStream = null;
        Reader cfgReader = null;

        InputStream proStream = null;
        Reader proReader = null;

        Properties properties = null;

        String resource = "mybatis-config.xml";
        try {
            // 读入配置文件流
            cfgtStream = Resources.getResourceAsStream(resource);
            cfgReader = new InputStreamReader(cfgtStream);

            // 读入属性文件
            proStream = Resources.getResourceAsStream("jdbc.properties");
            proReader = new InputStreamReader(proStream);
            properties = new Properties();
            properties.load(proReader);

            // 解密明文
            properties.setProperty("username", NameCodec.decode(properties.getProperty("username")));
            properties.setProperty("password", NameCodec.decode(properties.getProperty("password")));
        } catch (IOException e) {
            log.log(Level.SEVERE, null, e);
        }
        synchronized (CLASS_LOCK) {
            if (sqlSessionFactory == null) {
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(cfgReader, properties);
            }
        }
        return sqlSessionFactory;
    }
}
