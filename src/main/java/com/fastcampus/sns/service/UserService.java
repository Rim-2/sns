package com.fastcampus.sns.service;

import com.fastcampus.sns.exception.ErrorCode;
import com.fastcampus.sns.exception.SnsApplicationException;
import com.fastcampus.sns.model.User;
import com.fastcampus.sns.model.entity.UserEntity;
import com.fastcampus.sns.repository.UserEntityRepository;
import com.fastcampus.sns.util.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserEntityRepository userEntityRepository;
    private final BCryptPasswordEncoder encoder;

    @Transactional
    public User join(String userName, String password) {
        // 회원가입 - userName 중복 확인
        userEntityRepository.findByUserName(userName).ifPresent(it -> {
            throw new SnsApplicationException(ErrorCode.DUPLICATED_USER_NAME, String.format("%s is duplicated", userName));
        });

        // 회원가입 - user 등록
        UserEntity userEntity = userEntityRepository.save(UserEntity.of(userName, encoder.encode(password))); // entity 생성
        return User.fromEntity(userEntity); // dto 변환
    }

    // TODO: implement
    // 로그인
    public String login(String userName, String password) { // JWT -> String 반환
        // 회원가입 여부 체크
        UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(() -> new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded", userName)));

        // 비밀번호 체크
//      if (!userEntity.getPassword().equals(password)) { // 한쪽은 암호화되어있고 한쪽은 그대로임 -> 비교 안됨
        if(!encoder.matches(password, userEntity.getPassword())) { // encoder.matches를 통해 두개 비교 가능
            throw new SnsApplicationException(ErrorCode.INVALID_PASSWORD); // password에 관한 정보는 로깅하지 않음 -> 메세지 없는 생성자 만들기
        }

        // 토큰 생성
        JwtTokenUtils.generateToken(userName, )
        return "";
    }
}
