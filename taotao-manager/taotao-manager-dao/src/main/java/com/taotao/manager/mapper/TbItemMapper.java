package com.taotao.manager.mapper;




import com.taotao.common.pojo.TbItemParamGroup;
import com.taotao.manager.pojo.TbItem;
import com.taotao.manager.pojo.TbItemDesc;
import com.taotao.manager.pojo.TreeResult;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;


public interface TbItemMapper {
    @Select("SELECT * FROM tbitem WHERE id =#{id}")
    TbItem findTbitemById(long tbitemid);
    /**
     * 分页查询
     * @param page
     * @param limit
     * @return
     */
    @Select("SELECT * from tbitem limit #{page},#{limit}")
    List<TbItem> findByPage(@Param("page") int page,@Param("limit") int limit);

    /**
     * 查询当前数据条数
     */
    @Select("SELECT count(*) from tbitem ")
    int findCount();
    /**
     * 批量查询商品对象信息
     * @param tbItems 商品对象
     * @return
     */
    int  takeOffBatch(@Param("tbItems") List<TbItem> tbItems);

    /**
     * 批量上架
     * @param tbItems
     * @return
     */
    int takeUpBatch(@Param("tbItems")List<TbItem> tbItems);

    /**
     * 批量下架
     * @param tbItems
     * @return
     */
    int deleteBatch(@Param("tbItems")List<TbItem> tbItems);

    /**
     * 多条件动态搜索
     * @param title
     * @param priceMin
     * @param priceMax
     * @param cId
     * @return
     */
    List<TbItem> searchItem(@Param("page") Integer page,@Param("limit") Integer limit,@Param("title") String title, @Param("priceMin") Integer priceMin, @Param("priceMax") Integer priceMax,@Param("cId") Long cId);

    /**
     * 树结构
     * @param id
     * @return
     */
    List<TreeResult> treesearch(@Param("id") long id);

    /**
     * 多条件搜索
     * @param title
     * @param priceMin
     * @param priceMax
     * @param cId
     * @return
     */
    int findCountByC(@Param("title") String title, @Param("priceMin") Integer priceMin, @Param("priceMax") Integer priceMax,@Param("cId") Long cId);

    /**
     * 插入商品基本信息
     * @param item
     */
    void insertTbItem(TbItem item);
    /**
     * 插入商品描述信息
     * @param tbItemDesc
     */
    void insertTbItemDesc(TbItemDesc tbItemDesc);

    /**
     * 通过cId查找规格参数组
     * 多表查询规格参数组和每个组对应的参数项
     * @param cId
     * @return
     */
    List<TbItemParamGroup> showItemGroupById(Long cId);
}