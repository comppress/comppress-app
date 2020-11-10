package com.example.couscousapp.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> criterion1 = new ArrayList<String>();
        criterion1.add("- Sind die Aussagen des Textes belegt?\n- Werden unterschiedliche Quellen genannt?");
//        criterion1.add("Textaussagen sind belegt");
//        criterion1.add("Verschiedenartige Quellen");

        List<String> criterion2 = new ArrayList<String>();
        criterion2.add("- Sind die Arguemente / Werteurteile belegt?\n- Werden diese reflektiert?\n- Bedient sicht der Autor einer logischen\n  Argumentation?");
//        criterion2.add("Werturteile reflektiert");
//        criterion2.add("Argumentation logisch");

        List<String> criterion3 = new ArrayList<String>();
        criterion3.add("- Werden unterschiedliche Argumentations-\n  standpunkte wiedergegeben?\n- Wird fair und ausgewogen berichtet?");
//        criterion3.add("Fair und ausgewogen");
//        criterion3.add("Criterion 3.3");

        expandableListDetail.put("Sorgfältige Quellenarbeit", criterion1);
        expandableListDetail.put("Plausibel Argumentiert", criterion2);
        expandableListDetail.put("Ausgewogene Berichterstattung", criterion3);
        return expandableListDetail;
    }
}