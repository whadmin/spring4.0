package com.mybatis.chapter2.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * SqlSessionFactoryUtil
 * description SqlSessionFactory工具类
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

    /**
     * 构建SqlSessionFactory
     *
     * @return
     */
    private static SqlSessionFactory initSqlSessionFactory() {
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            log.log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
        synchronized (CLASS_LOCK) {
            if (sqlSessionFactory == null) {
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            }
        }
        return sqlSessionFactory;
    }

    /**
     * 获取SqlSession
     *
     * @return
     */
    public static SqlSession openSqlSession() {
        if (sqlSessionFactory == null) {
            initSqlSessionFactory();
        }
        return sqlSessionFactory.openSession();
    }
}
