package com.taotao.manager.mapper;




import com.taotao.common.pojo.TbItemParamGroup;
import com.taotao.common.pojo.TbItemParamKey;
import com.taotao.common.pojo.TbItemParamValue;
import com.taotao.manager.pojo.TbItem;
import com.taotao.manager.pojo.TbItemDesc;
import com.taotao.common.pojo.TreeResult;
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

    /**
     * 在商品规格参数值表中插入值
     * @param tbItemParamValues
     */

    void insertTbItemParamValue(@Param("tbItemParamValues") List<TbItemParamValue> tbItemParamValues);

    /**
     * 添加规格参数组的方法
     * @param groups 规格参数组 包括 商品分类id，组名，项对象（项名）
     *               注意：组的id自增长，项的id自增长
     * @return
     */
    int addParamGroup(@Param("groups") List<TbItemParamGroup> groups);

    /**
     * 根据分类id查询组信息
     * @param cId  分类id
     * @return 规格参数组集合对象
     */
    List<TbItemParamGroup> findTbItemGroupBycId(Long cId);

    /**
     * 插入规格参数项数据到数据库中
     * @param paramKeys 规格参数项集合对象
     * @return
     */
    int addParamGroupKeys(@Param("paramKeys") List<TbItemParamKey> paramKeys);

    int addParamGroup(TbItemParamGroup group);

    TbItemParamGroup findIdByCIdAndName(Long cId, String name);

    void addParamKey(TbItemParamKey tbItemParamKey);
}