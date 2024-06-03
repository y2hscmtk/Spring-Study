package study.spring_data_jpa.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id","username","age"}) // 연관관계 필드는 무한루프를 야기하므로 toString사용시 제외한다.
@Table(name = "MEMBER")
@NamedQuery( // 재사용되는 쿼리를 위해 사용한다. 실무에서는 거의 사용되지 않는다.
        name = "Member.findByUsername",
        query = "select m from Member m where m.username = :username"
)
// JPA 표준 스펙
// @NamedEntityGraph(name = "Member.all", attributeNodes = @NamedAttributeNode("team"))
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String username;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY) // 모든 연관관계는 웬만하면 지연로딩으로 설정할 것 => 성능 최적화를 위해
    @JoinColumn(name = "team_id")
    private Team team;

    public Member(String username) {
        this.username = username;
    }

    public Member(String username, int age) {
        this.username = username;
        this.age = age;
    }

    public Member(String username, int age, Team team) {
        this.username = username;
        this.age = age;
        if (team != null) {
            changeTeam(team);
        }
    }

    // 연관관계 편의 메소드
    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this); // 반대쪽에도 설정
    }
}
