package cn.programingmonkey.Table;


import org.springframework.stereotype.Component;

import javax.persistence.*;


/**
 * Created by cai on 2017/2/27.
 */
@Entity
@Component
@Table(name = "tg_classify")
public class Classify {

    private int id;
    private String name;        // 分类描述



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
