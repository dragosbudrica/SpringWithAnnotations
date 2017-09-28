package com.kepler.rominfo.dao;

import com.kepler.rominfo.domain.vo.Category;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public interface CategoryDao {
     List<Category> getAllCategories();
     Category getCategoryByName(@Param("categoryName") String categoryName);
}
