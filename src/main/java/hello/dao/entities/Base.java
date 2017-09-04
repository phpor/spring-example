package hello.dao.entities;

import hello.utils.StringUtil;

import java.io.Serializable;

/**
 * 所有实体类都应该具有的特点.
 */
public class Base implements Serializable {
    private String id;// 主键ID.

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public void initId(){
        if(this.id==null || "".equals(this.id)){
            this.id = StringUtil.get32UUID();
        }
    }
}
