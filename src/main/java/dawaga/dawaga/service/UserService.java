package dawaga.dawaga.service;

import dawaga.dawaga.dto.auth.*;
import dawaga.dawaga.dto.user.ChangeAddressRequest;
import dawaga.dawaga.dto.user.ChangeNicknameRequest;
import dawaga.dawaga.model.User;
import dawaga.dawaga.repository.UserRepository;
import dawaga.dawaga.security.JwtTokenProvider;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

/**
 * 사용자 관련 비즈니스 로직을 처리하는 서비스 클래스.
 *
 * @author SeryeongK
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final S3Service s3Service;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider,
                       S3Service s3Service) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.s3Service = s3Service;
    }

    /**
     * 회원가입을 처리하는 메서드.
     *
     * @param request 회원가입 요청 정보 (아이디, 이름, 닉네임, 비밀번호, 주소)
     * @return 새로 등록된 사용자
     *
     * @throws RuntimeException 아이디 중복 => "이미 사용중인 아이디입니다."
     * @throws RuntimeException 닉네임 중복 => "이미 사용중인 닉네임입니다."
     *
     * @see UserRepository#existsByUserId(String) 아이디 중복 확인
     * @see UserRepository#existsByUserNickname(String) 닉네임 중복 확인
     * @see PasswordEncoder#encode(CharSequence) 비밀번호 암호화
     */
    public User registerUser(SignupRequest request) {
        if (userRepository.existsByUserId(request.getId())) {
            throw new RuntimeException("이미 사용중인 아이디입니다.");
        }
        if (userRepository.existsByUserNickname(request.getNickname())) {
            throw new RuntimeException("이미 사용중인 닉네임입니다.");
        }

        User user = new User();
        user.setUserId(request.getId());
        user.setUserName(request.getName());
        user.setUserNickname(request.getNickname());
        user.setUserPassword(passwordEncoder.encode(request.getPassword()));
        user.setUserAddress(request.getAddress());
        user.setUserWithdrawYn(0);

        return userRepository.save(user);
    }

    /**
     * 사용자의 로그인을 처리하는 메서드.
     *
     * @param loginRequest 로그인 요청 정보 (아이디, 비밀번호)
     * @return JWT 토큰
     */
    public String authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getId(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtTokenProvider.generateToken(authentication);
    }

    /**
     * 사용자의 회원 탈퇴를 처리하는 메서드.
     *
     * @param withdrawRequest 회원 탈퇴 요청 정보 (비밀번호 포함)
     * @throws RuntimeException 인증 정보 없음 -> "인증 정보가 없습니다. 다시 로그인해주세요."
     * @throws RuntimeException 비밀번호 불일치 -> "비밀번호가 일치하지 않습니다."
     */
    @Transactional
    public void withdrawUser(WithdrawRequest withdrawRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new RuntimeException("인증 정보가 없습니다. 다시 로그인해주세요.");
        }

        String userId = authentication.getName();
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(withdrawRequest.getPassword(), user.getUserPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        user.setUserWithdrawYn(1);
        user.setUserWithdrawDate(LocalDateTime.now());

        userRepository.save(user);
    }

    /**
     * 사용자의 닉네임을 변경하는 메서드.
     *
     * @param changeNicknameRequest 닉네임 변경 요청 정보
     * @throws RuntimeException 닉네임 중복 -> "이미 사용 중인 닉네임입니다."
     */
    @Transactional
    public void changeNickname(ChangeNicknameRequest changeNicknameRequest) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        if (userRepository.existsByUserNickname(changeNicknameRequest.getNewNickname())) {
            throw new RuntimeException("이미 사용 중인 닉네임입니다.");
        }

        user.setUserNickname(changeNicknameRequest.getNewNickname());
        userRepository.save(user);
    }

    /**
     * 사용자의 주소를 변경하는 메서드.
     *
     * @param changeAddressRequest 주소 변경 요청 정보
     */
    @Transactional
    public void changeAddress(ChangeAddressRequest changeAddressRequest) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        user.setUserAddress(changeAddressRequest.getNewAddress());
        userRepository.save(user);
    }

    /**
     * 사용자의 프로필 이미지를 변경하는 메서드.
     *
     * @param file 업로드할 프로필 이미지
     * @return 업로드된 프로필 이미지 URL
     */
    @Transactional
    public String changeProfileImage(MultipartFile file) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        if (user.getUserProfileUrl() != null) {
            s3Service.deleteFile(user.getUserProfileUrl());
        }

        String newProfileImageUrl = s3Service.uploadFile(file);
        user.setUserProfileUrl(newProfileImageUrl);
        userRepository.save(user);

        return newProfileImageUrl;
    }
}
