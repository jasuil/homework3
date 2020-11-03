package net.class101.homework1.data.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class Account {
    @Id
    private Integer id;
    private String name;
    private String category;
    private String plan;
    private Date createdAt;
    private Date premiumIdExpiredAt;
    
}
