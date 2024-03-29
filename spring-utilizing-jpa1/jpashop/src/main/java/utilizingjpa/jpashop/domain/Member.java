package utilizingjpa.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Entity
@Getter @Setter
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String name;

    @Embedded // 내장 타입
    private Address address;

    // 하나의 회원이 여러개의 주문을 할 수 있음 -> 일대다 연관관계
    // '일'에 속하므로 연관관계의 주인이 아닌 매핑되는 테이블임
    @OneToMany(mappedBy = "member") // Order테이블의 필드명 member에 의해 매핑되었다는 의미(fk가 member다)
    private List<Order> orders = new ArrayList<>(); // '일'에 속하므로 단순 조회
}
