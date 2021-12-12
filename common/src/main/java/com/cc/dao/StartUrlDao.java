package com.cc.dao;

import com.cc.doamin.StartUrl;

import java.util.List;

public interface StartUrlDao {

    /**根据用户ID查询用户信息
	 * @param id
	 * @return
	 */
	StartUrl getStartUrlById(String id);
	
	/**
	 * 根据用户名查找用户列表
	 * @param statusCode
	 * @return
	 */
	List<StartUrl> getStartUrlByStatusCode(String statusCode);
	
	/**
	 * 添加用户
	 * @param startUrl
	 */
	void insertStartUrl(StartUrl startUrl);

	boolean updateStartUrl(StartUrl startUrl);
}
