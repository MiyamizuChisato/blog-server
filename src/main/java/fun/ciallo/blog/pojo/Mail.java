package fun.ciallo.blog.pojo;

import lombok.Data;

import java.util.Map;

@Data
public class Mail {
    private String taker;
    private String title;
    private Map<String, Object> params;
}
