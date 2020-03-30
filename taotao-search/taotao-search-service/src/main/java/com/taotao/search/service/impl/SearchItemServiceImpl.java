package com.taotao.search.service.impl;

import com.taotao.common.pojo.SearchItem;
import com.taotao.common.pojo.SearchResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.manager.mapper.SearchItemMapper;
import com.taotao.search.dao.SearchDao;
import com.taotao.search.service.SearchItemService;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
@Service
public class SearchItemServiceImpl implements SearchItemService  {
    @Autowired
    private SearchItemMapper searchItemMapper;
    @Autowired
    private SearchDao searchDao;
    @Autowired
    private SolrServer solrServer;
    @Override
    public TaotaoResult importAllItems()  {
        List<SearchItem> searchItems = searchItemMapper.getItemList();
        for (SearchItem searchItem:searchItems) {
            try {
                SolrInputDocument document = new SolrInputDocument();
                // 4、为文档添加域
                document.addField("id", searchItem.getId());
                document.addField("item_title", searchItem.getTitle());
                document.addField("item_sell_point", searchItem.getSellPoint());
                document.addField("item_price", searchItem.getPrice());
                document.addField("item_image", searchItem.getImage());
                document.addField("item_category_name", searchItem.getCategoryName());
                document.addField("item_desc", searchItem.getItemDesc());
                // 5、向索引库中添加文档。
                solrServer.add(document);
            } catch (SolrServerException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    solrServer.commit();
                } catch (SolrServerException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return TaotaoResult.ok();
    }
    //分类，价格，排序
    @Override
    public SearchResult search(String queryString,int page, int rows) throws Exception{
        SolrQuery query = new SolrQuery();
        if(queryString!=null&&!"".equals(queryString)){
            query.setQuery(queryString);
        }else{
            query.setQuery("*:*");
        }
        //开始索引
        query.setStart((page-1)*rows);
        //每一页显示的条数
        query.setRows(rows);
        // 4、需要指定默认搜索域。
        query.set("df", "item_keywords");

        // 5、设置高亮
        query.setHighlight(true);
        query.addHighlightField("item_title");
        query.setHighlightSimplePre("<span style='color:red'>");
        query.setHighlightSimplePost("</span>");
        //service准备数据 做if判断 加入逻辑操作
        SearchResult result = searchDao.search(query);
        //总页数
        long totalPage = result.getRecordCount()%rows==0?result.getRecordCount()/rows:result.getRecordCount()/rows+1;
        result.setPageCount(totalPage);
        return result;
    }

    @Override
    public void addDocument(SearchItem searchItem) {
        try {
            SolrInputDocument document = new SolrInputDocument();
            // 4、为文档添加域
            document.addField("id", searchItem.getId());
            document.addField("item_title", searchItem.getTitle());
            document.addField("item_sell_point", searchItem.getSellPoint());
            document.addField("item_price", searchItem.getPrice());
            document.addField("item_image", searchItem.getImage());
            document.addField("item_category_name", searchItem.getCategoryName());
            document.addField("item_desc", searchItem.getItemDesc());
            // 5、向索引库中添加文档。
            solrServer.add(document);
            solrServer.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public TaotaoResult addDocument(Long itemId) throws Exception {
        // 1、根据商品id查询商品信息。
        SearchItem searchItem = searchItemMapper.getItemById(itemId);
        // 2、创建一SolrInputDocument对象。
        SolrInputDocument document = new SolrInputDocument();
        // 3、使用SolrServer对象写入索引库。
        document.addField("id", searchItem.getId());
        document.addField("item_title", searchItem.getTitle());
        document.addField("item_sell_point", searchItem.getSellPoint());
        document.addField("item_price", searchItem.getPrice());
        document.addField("item_image", searchItem.getImage());
        document.addField("item_category_name", searchItem.getCategoryName());
        document.addField("item_desc", searchItem.getItemDesc());
        // 5、向索引库中添加文档。
        solrServer.add(document);
        solrServer.commit();
        // 4、返回成功，返回TaotaoResult。
        return TaotaoResult.ok();

    }

}

