package dto.presence;

import dto.BaseView;
import io.ebean.Finder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckPresenceView extends BaseView {

    public String ID;
    public String firstName;
    public String lastName;
    public String presenceStatus;

    //mock data
    public static List<CheckPresenceView> findAll() {
        List<CheckPresenceView> list = new ArrayList<>();
        list.add(new CheckPresenceView("0", "Adam", "Kowalski", "Obecny"));
        return list;
    }
}
