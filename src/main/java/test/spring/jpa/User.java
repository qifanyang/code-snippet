package test.spring.jpa;

import javax.persistence.*;

/**
 * @author yangqf
 * @version 1.0 2016/8/12
 */
@Entity
@Table(name = "user")
public class User{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column
    private Integer age;

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public Integer getAge(){
        return age;
    }

    public void setAge(Integer age){
        this.age = age;
    }
}
