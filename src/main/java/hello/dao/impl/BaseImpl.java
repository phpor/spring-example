package hello.dao.impl;

import hello.dao.entities.Base;
import hello.dao.interfaces.IBase;
import hello.exception.DbException;
import hello.utils.SpringContextUtil;
import hello.utils.StringUtil;
import hello.utils.page.PageBean;
import hello.utils.page.PageParam;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public abstract class BaseImpl<T extends Base> extends SqlSessionDaoSupport implements IBase<T> {
    protected static final Logger LOG = Logger.getLogger(BaseImpl.class);

    public static final String SQL_INSERT = "insert";
    public static final String SQL_BATCH_INSERT = "batchInsert";
    public static final String SQL_UPDATE_BY_ID = "updateByPrimaryKey";
    public static final String SQL_ALL_UPDATE_BY_ID = "updateAllByPrimaryKey";
    public static final String SQL_BATCH_UPDATE_BY_IDS = "batchUpdateByIds";
    //    public static final String SQL_BATCH_UPDATE_BY_COLUMN = "batchUpdateByColumn";
    public static final String SQL_SELECT_BY_ID = "selectByPrimaryKey";
    //    public static final String SQL_LIST_BY_COLUMN = "listByColumn";
//    public static final String SQL_COUNT_BY_COLUMN = "getCountByColumn";
    public static final String SQL_DELETE_BY_ID = "deleteByPrimaryKey";
    public static final String SQL_BATCH_DELETE_BY_IDS = "batchDeleteByIds";
    //    public static final String SQL_BATCH_DELETE_BY_COLUMN = "batchDeleteByColumn";
    public static final String SQL_LIST_PAGE = "listPage";
//    public static final String SQL_LIST_BY = "listBy";

    public static final String SQL_LIST_BY_OBJ = "listByObj";

    public static final String SQL_LIST_PAGE_COUNT = "listPageCount";
    public static final String SQL_COUNT_BY_PAGE_PARAM = "countByPageParam"; // 根据当前分页参数进行统计


    //@Value("${datasource.contains}")
    private String dataSourceContains;

    /**
     * 通过配置文件得到SqlSessionTemplate
     * 支持多数据源配置
     * @return
     */
    private SqlSessionTemplate getSqlSessionTemplateByConfig(){
        SqlSessionTemplate sqlTemp = null;

        if(dataSourceContains != null && !dataSourceContains.equals("")){
            String className = this.getClass().getName();
            String[] dataSources = dataSourceContains.split(",");
            for(int i = 0; i < dataSources.length; i++){
                if(className.indexOf(dataSources[i]) > -1){
                    sqlTemp = (SqlSessionTemplate) SpringContextUtil.getBean(dataSources[i] + "SqlSessionTemplate");
                    break;
                }
            }
        }

        return sqlTemp;
    }


    /**
     * 注入SqlSessionTemplate实例(要求Spring中进行SqlSessionTemplate的配置).
     * 可以调用sessionTemplate完成数据库操作.
     */
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory){
        super.setSqlSessionFactory(sqlSessionFactory);
    }

    public SqlSessionTemplate getSqlSessionTemplate() {
        SqlSessionTemplate sqlSessionTemplateTemp = sqlSessionTemplate;
        //通过多数据源配置得到Template
        SqlSessionTemplate sqlTemp = getSqlSessionTemplateByConfig();
        if(sqlTemp != null)
            sqlSessionTemplateTemp = sqlTemp;

        return sqlSessionTemplateTemp;
    }


    /**
     * 单条插入数据.
     */
    public int insert(T entity) {
        entity.initId();
        int result = getSqlSessionTemplate().insert(getStatement(SQL_INSERT), entity);
        if (result <= 0) {
            throw DbException.DB_INSERT_RESULT_0.newInstance("数据库操作,insert返回0.{%s}", getStatement(SQL_INSERT));
        }
        return result;
    }

    /**
     * 批量插入数据.
     */
    public int insert(List<T> list) {
        if (list.isEmpty() || list.size() <= 0) {
            return 0;
        }
        for(int i = 0; i < list.size(); i++){
            list.get(i).setId(StringUtil.get32UUID());
        }
        int result = getSqlSessionTemplate().insert(getStatement(SQL_BATCH_INSERT), list);
        if (result <= 0) {
            throw DbException.DB_INSERT_RESULT_0.newInstance("数据库操作,batchInsert返回0.{%s}", getStatement(SQL_BATCH_INSERT));
        }
        return result;
    }

    /**
     * 根据id单条更新数据.
     */
    public int updateByName(T entity) {
        //entity.setEditTime(DateUtils.getReqDateyyyyMMddHHmmss(new Date()));
        int result = getSqlSessionTemplate().update(getStatement(SQL_UPDATE_BY_ID), entity);
        if (result <= 0) {
            throw DbException.DB_UPDATE_RESULT_0.newInstance("数据库操作,updateByPrimaryKey返回0.{%s}", getStatement(SQL_UPDATE_BY_ID));
        }
        return result;
    }

    /**
     * 根据id更新整条数据，更新所有属性
     */
    public int updateAll(T entity){
        int result = getSqlSessionTemplate().update(getStatement(SQL_ALL_UPDATE_BY_ID), entity);
        if (result <= 0) {
            throw DbException.DB_UPDATE_RESULT_0.newInstance("数据库操作,updateAllByPrimaryKey返回0.{%s}", getStatement(SQL_UPDATE_BY_ID));
        }
        return result;
    }

    /**
     * 根据id批量更新数据.
     */
    public int update(List<T> list) {
        if (list.isEmpty() || list.size() <= 0) {
            return 0;
        }
        int result = getSqlSessionTemplate().update(getStatement(SQL_BATCH_UPDATE_BY_IDS), list);
        if (result <= 0) {
            throw DbException.DB_UPDATE_RESULT_0.newInstance("数据库操作,batchUpdateByIds返回0.{%s}", getStatement(SQL_BATCH_UPDATE_BY_IDS));
        }
        return result;
    }
    /**
     * 根据id查询数据.
     */
    public T getById(String id) {
        return getSqlSessionTemplate().selectOne(getStatement(SQL_SELECT_BY_ID), id);
    }

    public List<T> listByObj(T t){
        if (t == null) {
            return null;
        }
        return getSqlSessionTemplate().selectList(getStatement(SQL_LIST_BY_OBJ), t);
    }



    /**
     * 根据id删除数据.
     */
    public int delete(String id) {
        return (int) getSqlSessionTemplate().delete(getStatement(SQL_DELETE_BY_ID), id);
    }

    /**
     * 根据id批量删除数据.
     */
    public int delete(List<String> list) {
        if (list.isEmpty() || list.size() <= 0) {
            return 0;
        } else {
            return (int) getSqlSessionTemplate().delete(getStatement(SQL_BATCH_DELETE_BY_IDS), list);
        }
    }

    public Long totalCount(Map<String, Object> paramMap){
        if(null == paramMap){
            return 0L;
        }
        else{
            Long totalCount = getSqlSessionTemplate().selectOne(getStatement(SQL_LIST_PAGE_COUNT), paramMap);
            if(null == totalCount){
                return 0L;
            }
            else{
                return totalCount;
            }
        }
    }

    /**
     * 分页查询数据 .
     */
    public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) {
        if (paramMap == null) {
            paramMap = new HashMap<String, Object>();
        }

        // 统计总记录数
        Long totalCount = totalCount(paramMap);

        // 校验当前页数
        int currentPage = PageBean.checkCurrentPage(totalCount.intValue(), pageParam.getNumPerPage(), pageParam.getCurrentPage());
        pageParam.setCurrentPage(currentPage); // 为当前页重新设值
        // 校验页面输入的每页记录数numPerPage是否合法
        int numPerPage = PageBean.checkNumPerPage(pageParam.getNumPerPage()); // 校验每页记录数
        pageParam.setNumPerPage(numPerPage); // 重新设值

        // 根据页面传来的分页参数构造SQL分页参数
        paramMap.put("offset", (pageParam.getCurrentPage() - 1) * pageParam.getNumPerPage());
        paramMap.put("pageSize", pageParam.getNumPerPage());
        paramMap.put("startRowNum", (pageParam.getCurrentPage() - 1) * pageParam.getNumPerPage());
        paramMap.put("endRowNum", pageParam.getCurrentPage() * pageParam.getNumPerPage());

        // 获取分页数据集
        List<Object> list = getSqlSessionTemplate().selectList(getStatement(SQL_LIST_PAGE), paramMap);

        Object isCount = paramMap.get("isCount"); // 是否统计当前分页条件下的数据：1:是，其他为否
        if (isCount != null && "1".equals(isCount.toString())) {
            Map<String, Object> countResultMap = getSqlSessionTemplate().selectOne(getStatement(SQL_COUNT_BY_PAGE_PARAM), paramMap);
            return new PageBean(pageParam.getCurrentPage(), pageParam.getNumPerPage(), totalCount.intValue(), list, countResultMap);
        } else {
            // 构造分页对象
            return new PageBean(pageParam.getCurrentPage(), pageParam.getNumPerPage(), totalCount.intValue(), list);
        }
    }
    /**
     * 函数功能说明 ： 获取Mapper命名空间. 修改者名字： Along 修改日期： 2016-1-8 修改内容：
     *
     * @参数：@param sqlId
     * @参数：@return
     * @return：String
     * @throws
     */
    public String getStatement(String sqlId) {
        String name = this.getClass().getName();
        // 单线程用StringBuilder，确保速度；多线程用StringBuffer,确保安全
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(".").append(sqlId);
        return sb.toString();
    }


}
