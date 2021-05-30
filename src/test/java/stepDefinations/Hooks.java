package stepDefinations;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;

import java.io.IOException;
import java.util.*;

public class Hooks {

    @Before("@deleteplace")
    public void beforeScenario() throws IOException {
        StepDefination s = new StepDefination();

        if(StepDefination.placeId==null) {
            List<List<String>> dcd = new ArrayList<List<String>>();
            List<String> l1 = Arrays.asList("name", "language", "address");
            List<String> l2 = Arrays.asList("vibhav", "vibhavesse", "vibhav nagar");
            dcd.add(l1);
            dcd.add(l2);

            DataTable dt = DataTable.create(dcd);


            s.add_place_payload_with_folloing_details(dt);
            s.user_calls_with_post_http_request("AddPlaceAPI", "POST");
            s.verify_place_id_created_maps_to_using("vibhav", "GetPlaceAPI");
        }
    }
}
