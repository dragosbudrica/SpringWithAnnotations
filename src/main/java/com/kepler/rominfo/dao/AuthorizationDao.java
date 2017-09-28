package com.kepler.rominfo.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorizationDao {
    boolean isAuthorized(@Param("roleId") long roleId, @Param("resourceId") long resourceId);
}
