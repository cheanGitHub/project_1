package com.cc.dao.impl;

import com.cc.dao.StartUrlDao;
import com.cc.doamin.StartUrl;
import com.cc.mybatis.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class StartUrlDaoImpl implements StartUrlDao {

    @Override
    public StartUrl getStartUrlById(String id) {
        SqlSession sqlSession = SqlSessionFactoryUtils.getSqlSessionFactory().openSession();
        StartUrl startUrl = sqlSession.selectOne("startUrl.getStartUrlById", id);
        sqlSession.close();
        return startUrl;
    }

    @Override
    public List<StartUrl> getStartUrlByStatusCode (String statusCode) {
        SqlSession sqlSession = SqlSessionFactoryUtils.getSqlSessionFactory().openSession();
        List<StartUrl> startUrls = sqlSession.selectList("startUrl.getStartUrlByStatusCode", statusCode);
        sqlSession.close();
        return startUrls;
    }

    @Override
    public void insertStartUrl(StartUrl user) {
        SqlSession sqlSession = SqlSessionFactoryUtils.getSqlSessionFactory().openSession();
        sqlSession.insert("startUrl.insertStartUrl", user);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public boolean updateStartUrl(StartUrl startUrl) {
        SqlSession sqlSession = SqlSessionFactoryUtils.getSqlSessionFactory().openSession();
        int n = sqlSession.update("startUrl.updateStartUrl", startUrl);
        sqlSession.commit();
        sqlSession.close();
        return n == 1;
    }
}
