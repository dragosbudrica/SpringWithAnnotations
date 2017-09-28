package com.kepler.rominfo.dao;

import com.kepler.rominfo.domain.vo.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao {
     Role getRoleByName(@Param("roleName") String roleName);
}
