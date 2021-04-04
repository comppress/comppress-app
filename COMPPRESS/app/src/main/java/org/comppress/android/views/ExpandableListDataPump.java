package org.comppress.android.views;

import org.comppress.android.App;
import org.comppress.android.R;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedHashMap;

public class ExpandableListDataPump {
    public static LinkedHashMap<String, List<String>> getData() {
        LinkedHashMap<String, List<String>> expandableListDetail = new LinkedHashMap<String, List<String>>();

        List<String> criterion1 = new ArrayList<String>();
        criterion1.add(App.getContext().getResources().getString(R.string.criterion1_description));

        List<String> criterion2 = new ArrayList<String>();
        criterion2.add(App.getContext().getResources().getString(R.string.criterion2_description));

        List<String> criterion3 = new ArrayList<String>();
        criterion3.add(App.getContext().getResources().getString(R.string.criterion3_description));

        expandableListDetail.put(App.getContext().getResources().getString(R.string.criterion1), criterion1);
        expandableListDetail.put(App.getContext().getResources().getString(R.string.criterion2), criterion2);
        expandableListDetail.put(App.getContext().getResources().getString(R.string.criterion3), criterion3);
        return expandableListDetail;
    }
}