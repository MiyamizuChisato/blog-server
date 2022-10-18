package fun.ciallo.blog.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@RequiredArgsConstructor
@Table(name = "tb_article")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private String title;

    @Column
    private String image;

    @Column(columnDefinition = "bigint default 0")
    @Generated(GenerationTime.INSERT)
    private Long watch;

    @Column
    private String content;

    @Column(columnDefinition = "timestamp default current_timestamp")
    @Generated(GenerationTime.INSERT)
    private LocalDateTime createTime;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private List<Comment> comments;
}

