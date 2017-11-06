package dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Message {

    public String text;
    public String type;

}
