package com.kepler.rominfo.dao;

import com.kepler.rominfo.domain.vo.Resource;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceDao {
    Resource getResourceByName(@Param("resourceName") String resourceName);
}
