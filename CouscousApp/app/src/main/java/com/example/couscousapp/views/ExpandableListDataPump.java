package com.example.couscousapp.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> criterion1 = new ArrayList<String>();
        criterion1.add("Criterion 1.1");
        criterion1.add("Criterion 1.2");
//        criterion1.add("Criterion 1.3");

        List<String> criterion2 = new ArrayList<String>();
        criterion2.add("Criterion 2.1");
        criterion2.add("Criterion 2.2");
        criterion2.add("Criterion 2.3");

        List<String> criterion3 = new ArrayList<String>();
        criterion3.add("Criterion 3.1");
        criterion3.add("Criterion 3.2");
//        criterion3.add("Criterion 3.3");

        expandableListDetail.put("Sorgfältige Quellenarbeit", criterion1);
        expandableListDetail.put("Plausibel Argumentiert", criterion2);
        expandableListDetail.put("Ausgewogene Berichterstattung", criterion3);
        return expandableListDetail;
    }
}