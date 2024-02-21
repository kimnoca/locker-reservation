package yu.cse.locker.domain.user.dao;


import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MessageRepository {
    private final int TIME_TO_LEAVE = 3 * 60;

    private final StringRedisTemplate stringRedisTemplate;

    public void createMassageCertification(String phoneNumber, String certificationNumber) {
        stringRedisTemplate.opsForValue()
                .set(phoneNumber, certificationNumber, Duration.ofSeconds(TIME_TO_LEAVE));
    }

    public String getCertificationCode(String phoneNumber) {
        return stringRedisTemplate.opsForValue()
                .get(phoneNumber);
    }

    public void deleteMassageCertification(String phoneNumber) {
        stringRedisTemplate.delete(phoneNumber);
    }

    public boolean checkHasPhoneNumber(String phoneNumber) {
        return stringRedisTemplate.hasKey(phoneNumber);
    }

}
