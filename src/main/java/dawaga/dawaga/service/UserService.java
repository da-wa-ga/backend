package dawaga.dawaga.service;

import dawaga.dawaga.dto.SignupRequest;
import dawaga.dawaga.model.User;
import dawaga.dawaga.respository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 생성자 주입을 통해 UserRepository와 PasswordEncoder를 주입받기
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(SignupRequest request) {
        // 중복 여부 확인: 로그인 아이디 중복 확인
        if (userRepository.existsByUserId(request.getId())) {
            throw new RuntimeException("이미 사용중인 아이디입니다.");
        }
        // 중복 여부 확인: 닉네임 중복 확인
        if (userRepository.existsByUserNickname(request.getNickname())) {
            throw new RuntimeException("이미 사용중인 닉네임입니다.");
        }

        // 새 User 생성 및 비밀번호 암호화
        User user = new User();
        user.setUserId(request.getId());
        user.setUserName(request.getName());
        user.setUserNickname(request.getNickname());
        user.setUserPassword(passwordEncoder.encode(request.getPassword()));
        user.setUserAddress(request.getAddress());
        user.setUserWithdrawYn(0);

        return userRepository.save(user);
    }
}
