package dto.presence;

import dto.BaseView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import play.mvc.QueryStringBindable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckPresenceViewWrapper extends BaseView implements QueryStringBindable<CheckPresenceViewWrapper> {

    public List<CheckPresenceView> students;

    @Override
    public Optional<CheckPresenceViewWrapper> bind(String key, Map<String, String[]> data) {
        try{
            //TODO
            return Optional.empty();
        } catch (Exception e){
            return Optional.empty();
        }
    }

    @Override
    public String unbind(String key) {
        return null;
    }

    @Override
    public String javascriptUnbind() {
        return null;
    }
}
