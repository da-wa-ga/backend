package dawaga.dawaga.service;

import dawaga.dawaga.dto.LoginRequest;
import dawaga.dawaga.dto.SignupRequest;
import dawaga.dawaga.model.User;
import dawaga.dawaga.respository.UserRepository;
import dawaga.dawaga.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;  // 추가
    private final JwtTokenProvider jwtTokenProvider;  // 추가

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
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

    public String authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getId(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtTokenProvider.generateToken(authentication);
    }
}
