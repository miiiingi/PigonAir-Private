package com.example.pigonair.member;
import static org.assertj.core.api.AssertionsForClassTypes.*;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.pigonair.domain.member.entity.Member;
import com.example.pigonair.domain.member.repository.MemberRepository;
@SpringBootTest
public class MemberGeneratorTest {
    @Autowired
    MemberRepository memberRepository;
    private void generateMembers(int numberOfMembers) {
        // DB에서 마지막 멤버 가져오기
        Member lastMember = memberRepository.findTopByOrderByIdDesc().orElse(null);
        int startNumber = 1; // Default start number
        if (lastMember != null) {
            Pattern pattern = Pattern.compile("test(\\d+)");
            Matcher matcher = pattern.matcher(lastMember.getName());
            if (matcher.find()) {
                startNumber = Integer.parseInt(matcher.group(1)) + 1;
            }
        }
        // 멤버 데이터 생성
        for (int i = startNumber; i < startNumber + numberOfMembers; i++) {
            String name = "test" + i;
            String email = name + "@email.com";
            String phoneNumber = generateRandomPhoneNumber();
            String password = "$2a$10$/nQm7P07OtMUceFvcKab1O4s1tCg45.Q.dnmDq7me6SnjeM9X31LW";
            Member member = new Member(name, email, phoneNumber, password);
            memberRepository.save(member);
        }
    }
    private String generateRandomPhoneNumber() {
        Random random = new Random();
        return "010-" + (1000 + random.nextInt(9000)) + "-" + (1000 + random.nextInt(9000));
    }
    @Test
    public void generateRandomMember() {
        int numberOfMembers = 10000;
        int memberSizeBefore = memberRepository.findAll().size();
        generateMembers(numberOfMembers);
        int memberSizeAfter = memberRepository.findAll().size();
        assertThat(memberSizeAfter - memberSizeBefore).isEqualTo(numberOfMembers);
    }
}