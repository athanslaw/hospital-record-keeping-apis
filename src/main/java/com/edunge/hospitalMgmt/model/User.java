package com.edunge.hospitalMgmt.model;

import com.edunge.hospitalMgmt.dto.UserDto;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import javax.persistence.*;
import java.time.Instant;
import java.util.Date;

@Entity
@Table(name="users")
public class User extends BaseModel {
    private String name;
    @Column(unique = true)
    private String uuid;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "registration_date", nullable = false, updatable = false)
    @CreatedDate
    private Date registrationDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    @LastModifiedDate
    private Date updatedAt;

    public User() {
        registrationDate= Date.from(Instant.now());
        updatedAt = Date.from(Instant.now());
    }

    public User toUser(UserDto userDto){
        User user = new User();
        user.setUuid(userDto.getUuid());
        user.setName(userDto.getName());
        return user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registration_date) {
        this.registrationDate = registrationDate;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

}
