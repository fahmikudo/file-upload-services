package com.jetdevs.test.fileuploadservices.util;

import org.springframework.data.domain.Sort;

public class CommonFunction {

    private static final String ASC = "asc";
    private static final String DESC = "desc";

    public static Sort.Direction getSortDirection(String direction) {
        if (direction.equalsIgnoreCase(ASC)) {
            return Sort.Direction.ASC;
        } else if (direction.equalsIgnoreCase(DESC)) {
            return Sort.Direction.DESC;
        }
        return Sort.Direction.ASC;
    }

}
