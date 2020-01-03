package com.ys.log.until;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Date:2019/12/31
 * Time:16:16
 * author:Mr_YANG
 */
public class GetDateUtil {
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 传1个系统时间，只需要年-月-日
     */
    public static String getDate_(Date date) {
        return sdf.format(date);
    }


}
