```sql
SELECT  IFNULL(CASE WHEN c.SERVICE_ID = 0 THEN 1 ELSE 0 END,0) AS isBreak,  
IFNULL(CASE WHEN d.PRIORITY > 0 THEN d.PRIORITY ELSE a.PRIORITY END,0) AS SS_PRIORITY,  
d.PRIORITY, c.SC_ID, c.TRANS_ID, d.TKEN_NO2, IFNULL(PAGE_NAME,'') AS ROOT_PAGE, c.SERVICE_ID, b.SERVICE_NAME, c.STATE, d.TKEN_NO, 
TIME(IFNULL(c.SDST,NOW())) AS START_TIME,
d.TKEN_ISSUE_DATE, d.TKEN_ISSUE_TIME, d.TKIS_ID, d.LANG_ID, d.REF_DATA,
CASE WHEN c.SDST IS NOT NULL THEN IFNULL(TIME_FORMAT(SEC_TO_TIME(TIME_TO_SEC(DATE_FORMAT(c.SDST, '%T')) - TIME_TO_SEC(d.TKEN_ISSUE_TIME)), '%H:%i:%s'), '')
ELSE IFNULL(TIME_FORMAT(SEC_TO_TIME(TIME_TO_SEC(DATE_FORMAT(NOW(), '%T')) - TIME_TO_SEC(d.TKEN_ISSUE_TIME)), '%H:%i:%s'), '') END AS WAITING_TIME,
CASE WHEN c.SDST IS NOT NULL THEN IFNULL(TIME_TO_SEC(DATE_FORMAT(c.SDST, '%T')) - TIME_TO_SEC(d.TKEN_ISSUE_TIME), 0)
ELSE IFNULL(TIME_TO_SEC(DATE_FORMAT(NOW(), '%T')) - TIME_TO_SEC(d.TKEN_ISSUE_TIME), 0) END AS WAITING_SEC,
IFNULL(d.CUST_ID,'') AS CUST_ID , 
IFNULL(d.REMARKS,'') AS REMARKS
FROM transaction_details c
LEFT JOIN sdc_service_info a ON a.SDC_ID=1 AND a.SERVICE_ID=c.SERVICE_ID
LEFT JOIN service_info b ON c.SERVICE_ID=b.SERVICE_ID 
LEFT JOIN transaction_master d ON c.TRANS_ID=d.TRANS_ID 
LEFT JOIN page_info e ON d.TKIS_ROOT_PAGE=e.PAGE_ID 
WHERE c.SC_ID=1 AND ((c.SDC_ID=1 AND c.STATE IN(0,3)) OR (c.SDC_ID=0 AND c.STATE=0 AND a.SDC_ID IS NOT NULL)) AND d.TKEN_ISSUE_DATE = DATE(NOW()) 
AND (ISNULL(d.NEXT_TRY_TIME) OR d.NEXT_TRY_TIME<TIME(NOW()) )
GROUP BY c.TRANS_ID, c.SERVICE_ID
ORDER BY c.SDC_ID DESC, c.STATE DESC, isBreak DESC, a.PRIORITY DESC, d.PRIORITY ASC, d.TKEN_ISSUE_TIME ASC, TRANS_ID ASC
```

# Credentials of db Zamir's pc

```sh
192.168.30.83
port: 5432
postgres/12345
```

# java code
```java
String filterForCancelToken = "";
        //After serve * token by this sdc cancel token will be considered
        if (DBConfiguration.RE_TRY_TOKEN_OPTION.equals("1")) {
            int tryAfterToken = DBConfiguration.RE_TRY_AFTER_TOKENS - 1;
            filterForCancelToken = " OR (d.NEXT_TRY_TIME>TIME(NOW()) AND (SELECT COUNT(DISTINCT c2.TRANS_ID) token_served"
                    + " FROM transaction_details c2 WHERE c2.SC_ID=" + sScId + " AND c2.SDC_ID=" + sdcId + " AND c2.STATE=1"
                    + " AND c2.SDET >c.SDST GROUP BY c2.SDC_ID)>" + tryAfterToken + ")";
        }


        String setQueryForBreak = "ifnull(case when c.SERVICE_ID = 0 then 1 else 0 end,0) as isBreak,  ifnull(case WHEN d.PRIORITY > 0 THEN d.PRIORITY else a.PRIORITY end,0) as SS_PRIORITY,";//SS_PRIORITY = SDC SERVICE PRIORITY
        String sql1 = " SELECT " + setQueryForBreak + " d.PRIORITY, c.SC_ID, c.TRANS_ID, d.TKEN_NO2, ifnull(PAGE_NAME,'') as ROOT_PAGE, c.SERVICE_ID, b.SERVICE_NAME, c.STATE, d.TKEN_NO, ";
        sql1 += " TIME(IFNULL(c.SDST,NOW())) AS START_TIME,";
        sql1 += " d.TKEN_ISSUE_DATE, d.TKEN_ISSUE_TIME, d.TKIS_ID, d.LANG_ID, d.REF_DATA,";
        //sql1 += " ifnull(TIME_FORMAT(SEC_TO_TIME(TIME_TO_SEC(DATE_FORMAT(now(), '%T')) - TIME_TO_SEC(d.TKEN_ISSUE_TIME)), '%H:%i:%s'), '') AS WAITING_TIME,";
        sql1 +=" CASE WHEN c.SDST IS NOT NULL THEN IFNULL(TIME_FORMAT(SEC_TO_TIME(TIME_TO_SEC(DATE_FORMAT(c.SDST, '%T')) - TIME_TO_SEC(d.TKEN_ISSUE_TIME)), '%H:%i:%s'), '')";
        sql1 +=" ELSE IFNULL(TIME_FORMAT(SEC_TO_TIME(TIME_TO_SEC(DATE_FORMAT(NOW(), '%T')) - TIME_TO_SEC(d.TKEN_ISSUE_TIME)), '%H:%i:%s'), '') END AS WAITING_TIME, ";
        //sql1 += " ifnull(TIME_TO_SEC(DATE_FORMAT(now(), '%T')) - TIME_TO_SEC(d.TKEN_ISSUE_TIME), 0) AS WAITING_SEC, ifnull(d.CUST_ID,'') as CUST_ID";
        sql1 += " CASE WHEN c.SDST IS NOT NULL THEN IFNULL(TIME_TO_SEC(DATE_FORMAT(c.SDST, '%T')) - TIME_TO_SEC(d.TKEN_ISSUE_TIME), 0)";
        sql1 += " ELSE IFNULL(TIME_TO_SEC(DATE_FORMAT(NOW(), '%T')) - TIME_TO_SEC(d.TKEN_ISSUE_TIME), 0) END AS WAITING_SEC,";
        sql1 += " ifnull(d.CUST_ID,'') as CUST_ID , ";
        sql1 += " IFNULL(d.REMARKS,'') AS REMARKS";
        sql1 += " FROM transaction_details c";
        sql1 += " left join sdc_service_info a on a.SDC_ID=" + sdcId + " and a.SERVICE_ID=c.SERVICE_ID";
        sql1 += " left join service_info b on c.SERVICE_ID=b.SERVICE_ID ";
        sql1 += " left join transaction_master d on c.TRANS_ID=d.TRANS_ID ";
        sql1 += " left join page_info e on d.TKIS_ROOT_PAGE=e.PAGE_ID ";
        sql1 += " WHERE c.SC_ID=" + sScId + " AND ((c.SDC_ID=" + sdcId + " and c.STATE IN(0,3)) OR (c.SDC_ID=0 AND c.STATE=0 AND a.SDC_ID is not null)) AND d.TKEN_ISSUE_DATE = date(now()) ";
        sql1 += " AND (isnull(d.NEXT_TRY_TIME) or d.NEXT_TRY_TIME<time(now())";
        sql1 += filterForCancelToken + ")";
        //skipTransIds are the tokens which have dependent service for multiple service
        if (skipTransIds.length() > 1) {
            sql1 += " AND c.TRANS_ID NOT IN(" + skipTransIds.substring(0, skipTransIds.length() - 1) + ")";
        }
        sql1 += " GROUP by c.TRANS_ID, c.SERVICE_ID ";
        sql1 += " ORDER BY c.SDC_ID desc, c.STATE desc, isBreak desc";
        if(priorityRatio==-1){
            sql1 += ", a.PRIORITY desc, d.PRIORITY asc, d.TKEN_ISSUE_TIME ASC, TRANS_ID asc limit 0,1"; //a.PRIORITY desc,  , SS_PRIORITY desc, truncate(d.SERVICE_PRIORITY/100,0) desc
        }else if (noOfService >= priorityRatio) {
            sql1 += ", a.PRIORITY desc, d.TKEN_ISSUE_TIME ASC, TRANS_ID asc limit 0,1"; // a.PRIORITY desc,    , SS_PRIORITY desc, truncate(d.SERVICE_PRIORITY/100,0) desc
        } else {
            sql1 += ", a.PRIORITY desc, d.PRIORITY desc, d.TKEN_ISSUE_TIME ASC, TRANS_ID asc limit 0,1"; //a.PRIORITY desc,  , SS_PRIORITY desc, truncate(d.
        }
        Utilities.generateLog(sdcManagerMysql, sql1, "");
        return sql1;
```
#  Check number of active connection 
```sql
SELECT count(distinct(numbackends)) FROM pg_stat_database;

SELECT datname, numbackends FROM pg_stat_database; -- connection per schema

```
# some data from old structure db

```sql
SELECT  transaction_details.*
FROM transaction_details transaction_details
LEFT JOIN transaction_master transaction_master ON transaction_details.TRANS_ID=transaction_master.TRANS_ID 

WHERE 
transaction_details.SC_ID=1 AND ((transaction_details.SDC_ID=1 AND transaction_details.STATE IN(0,3)) OR (transaction_details.SDC_ID=0 AND transaction_details.STATE=0)) -- AND d.TKEN_ISSUE_DATE = DATE(NOW()) 
AND (ISNULL(transaction_master.NEXT_TRY_TIME) OR transaction_master.NEXT_TRY_TIME<TIME(NOW()) )
GROUP BY transaction_details.TRANS_ID, transaction_details.SERVICE_ID
ORDER BY transaction_details.SDC_ID DESC, transaction_details.STATE DESC, transaction_master.PRIORITY ASC, transaction_master.TKEN_ISSUE_TIME ASC
```