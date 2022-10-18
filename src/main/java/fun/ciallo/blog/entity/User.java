package fun.ciallo.blog.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.Generated;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@RequiredArgsConstructor
@Table(name = "tb_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(columnDefinition = "varchar(255) default 'avatar/default.png'")
    @Generated(GenerationTime.INSERT)
    private String avatar;

    @Column
    private String nickname;

    @Column
    private String motto;

    @Column(columnDefinition = "timestamp default current_timestamp")
    @Generated(GenerationTime.INSERT)
    private LocalDateTime createTime;


    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "identity_id", columnDefinition = "bigint default 2", insertable = false)
    @Generated(GenerationTime.INSERT)
    private Identity identity;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    private Account account;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
