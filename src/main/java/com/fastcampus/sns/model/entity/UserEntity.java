package com.fastcampus.sns.model.entity;

import com.fastcampus.sns.model.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "\"user\"") // Table명
/*
 * \" ~ \"로 감싸는 이유
 * DB로 사용할 PostgreSQL에 user table이 이미 존재하므로, 구분해야 함
  */

@Getter
@Setter
@SQLDelete(sql = "UPDATED \"user\" SET deleted_at = NOW() where id=?")
@Where(clause = "deleted_at is NULL")
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // id 자동 할당
    private Integer id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "role") // 권한
    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER;

    // 디버깅을 편리하게 하기 위한 데이터

    @Column(name = "registered_at")
    private Timestamp registeredAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    @PrePersist
    void registeredAt() {
        this.registeredAt = Timestamp.from(Instant.now());
    }

    @PreUpdate
    void updatedAt() {
        this.updatedAt = Timestamp.from(Instant.now());
    }

//    @PrePersist
//    void deletedAt() {
//        this.deletedAt = Timestamp.from(Instant.now());
//    }

    /*.
    UserEntity를 신규 생성하는 메서드
     */
    public static UserEntity of(String userName, String password) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(userName);
        userEntity.setPassword(password);
        return userEntity;
    }
}
