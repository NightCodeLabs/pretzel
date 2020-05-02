package com.github.cucumberlocust4j.pretzel.helpers;

public class Constants {

    public static final String PRETZELRESULTSPATH = "target/pretzel/";
    public static final String PRETEZELINTERNALEXECUTIONFILEPATH = PRETZELRESULTSPATH + "internal/";
    public static final String PRETEZELREPORTFILEPATH = PRETZELRESULTSPATH + "report/";


    public static final String LOCUSTMASTERFILEPATH = PRETEZELINTERNALEXECUTIONFILEPATH + "locustMasterFile/";
    public static final String LOCUSTMASTERFILENAME = "locust-master.py";
    public static final String LOCUSTMASTERCOMPLETEPATH = LOCUSTMASTERFILEPATH + LOCUSTMASTERFILENAME;
    public static final String CSVREPORTFOLDERPATH = PRETEZELINTERNALEXECUTIONFILEPATH + "/locustCsvReport/";
    public static final String STATSREPORTPATH = CSVREPORTFOLDERPATH + "performanceResults_stats.csv";
    public static final String STATSHISTORYREPORTPATH = CSVREPORTFOLDERPATH + "performanceResults_stats_history.csv";

    public static final String CHARTPATH = PRETEZELREPORTFILEPATH + "charts/";

    public static final String EXTENTREPORTCONFIGPATH = "src/main/resources/configs/extent-config.xml";
    public static final String NAMEOFREPORT = "performanceResults";
}
