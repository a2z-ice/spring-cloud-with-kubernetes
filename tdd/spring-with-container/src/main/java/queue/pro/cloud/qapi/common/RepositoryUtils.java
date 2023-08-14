package queue.pro.cloud.qapi.common;

import org.springframework.data.domain.Sort;

import java.util.Iterator;

public class RepositoryUtils {
    public static String getOrderByClause(String tableAlias, Sort sort) {
        if(sort == null || sort.isUnsorted()) return "";

        StringBuilder sortOrder = new StringBuilder(" ORDER BY ");
        Iterator<Sort.Order> iterator = sort.iterator();
        while (iterator.hasNext()){
            Sort.Order order = iterator.next();
            order.getProperty();
            sortOrder.append(tableAlias).append(".").append(order.getProperty()).append(" ").append(order.getDirection());
            if(iterator.hasNext()) sortOrder.append(",");
            sortOrder.append(" ");
        }
        return sortOrder.toString();
    }
}
