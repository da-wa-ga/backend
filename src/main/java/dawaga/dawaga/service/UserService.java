package dawaga.dawaga.service;

import dawaga.dawaga.dto.auth.LoginRequest;
import dawaga.dawaga.dto.auth.SignupRequest;
import dawaga.dawaga.dto.auth.WithdrawRequest;
import dawaga.dawaga.model.User;
import dawaga.dawaga.respository.UserRepository;
import dawaga.dawaga.security.JwtTokenProvider;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

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

    @Transactional
    public void withdrawUser(WithdrawRequest withdrawRequest) {
        // 현재 로그인한 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName() == null) {
            throw new RuntimeException("인증 정보가 없습니다. 다시 로그인해주세요.");
        }

        String userId = authentication.getName(); // 로그인한 사용자의 ID 가져오기
        Optional<User> optionalUser = userRepository.findByUserId(userId);

        if (optionalUser.isEmpty()) {
            throw new RuntimeException("사용자를 찾을 수 없습니다.");
        }

        User user = optionalUser.get();

        // 비밀번호 확인
        if (!passwordEncoder.matches(withdrawRequest.getPassword(), user.getUserPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        // 탈퇴 처리 (withdraw_yn = 1, withdraw_date = 현재 시간)
        user.setUserWithdrawYn(1);
        user.setUserWithdrawDate(LocalDateTime.now());

        userRepository.save(user);
    }

}
