package fun.ciallo.blog.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@RequiredArgsConstructor
@Table(name = "tb_message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Lob
    @Column
    private String content;

    @Column
    private Long target;

    @Column(columnDefinition = "bigint default 0")
    @Generated(GenerationTime.INSERT)
    private Long children;

    @Column(columnDefinition = "timestamp default current_timestamp")
    @Generated(GenerationTime.INSERT)
    private LocalDateTime createTime;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;
}
