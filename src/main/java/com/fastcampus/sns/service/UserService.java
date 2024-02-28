package com.fastcampus.sns.service;

import com.fastcampus.sns.exception.SnsApplicationException;
import com.fastcampus.sns.model.User;
import com.fastcampus.sns.model.entity.UserEntity;
import com.fastcampus.sns.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserEntityRepository userEntityRepository;

    // TODO: implement
    public User join(String userName, String password) {
        // userName 중복 확인
        Optional<UserEntity> userEntity = userEntityRepository.findByUserName(userName);

        // user 등록
        userEntityRepository.save(new UserEntity());

        return new User();
    }

    // TODO: implement
    public String login(String userName, String password) { // JWT -> String 반환
        // 회원가입 여부 체크
        UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(() -> new SnsApplicationException());

        // 비밀번호 체크
        if (!userEntity.getPassword().equals(password)) {
            throw new SnsApplicationException();
        }

        // 토큰 생성
        return "";
    }
}