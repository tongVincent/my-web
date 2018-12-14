package com.twx.java.text;

import com.twx.BaseTest;
import com.twx.core.util.MessageUtil;
import org.junit.Test;

import java.text.DecimalFormat;

/**
 * Created by vincent.tong on 2017/2/8.
 */
public class DecimalFormatTest extends BaseTest {

    /**
     * 通过下面的测试，可以知道：
     * 1、0表示，当位数不够的时候，补0。#表示，当位数不够的时候，不管。
     * 2、"00#"与".#0"格式是不合法的，根据第一点，可以推出此不合法。
     * 3、整数的分隔符，以小数点的地方第一次出现的为准。如#,###,##是每2位分隔，#,##,###是每3位分隔。
     * 4、小数没有分隔的概念。
     */
    @Test
    public void test001() {
        DecimalFormat format = new DecimalFormat("00.00");
        MessageUtil.onTime("00.00格式");
        MessageUtil.onTime(format.format(1321322.563213213));
        MessageUtil.onTime(format.format(0.69313213));
        MessageUtil.onTime(format.format(0.6));
        MessageUtil.onTime(format.format(0));
        MessageUtil.onTime(format.format(321654));

        format = new DecimalFormat("##.##");
        MessageUtil.onTime("##.##格式");
        MessageUtil.onTime(format.format(1321322.563213213));
        MessageUtil.onTime(format.format(0.69313213));
        MessageUtil.onTime(format.format(0.6));
        MessageUtil.onTime(format.format(0));
        MessageUtil.onTime(format.format(321654));

        format = new DecimalFormat(",##.##");
        MessageUtil.onTime(",##.##格式");
        MessageUtil.onTime(format.format(1321322.563213213));
        MessageUtil.onTime(format.format(0.69313213));
        MessageUtil.onTime(format.format(0.6));
        MessageUtil.onTime(format.format(0));
        MessageUtil.onTime(format.format(321654));

        format = new DecimalFormat("#,##.##");
        MessageUtil.onTime("#,##.##格式");
        MessageUtil.onTime(format.format(1321322.563213213));
        MessageUtil.onTime(format.format(0.69313213));
        MessageUtil.onTime(format.format(0.6));
        MessageUtil.onTime(format.format(0));
        MessageUtil.onTime(format.format(321654));

        format = new DecimalFormat("#,###,##.##");
        MessageUtil.onTime("#,###,##.##格式");
        MessageUtil.onTime(format.format(1321322.563213213));
        MessageUtil.onTime(format.format(0.69313213));
        MessageUtil.onTime(format.format(0.6));
        MessageUtil.onTime(format.format(0));
        MessageUtil.onTime(format.format(321653334));

        format = new DecimalFormat("#,##,###.##");
        MessageUtil.onTime("#,##,###.##格式");
        MessageUtil.onTime(format.format(1321322.563213213));
        MessageUtil.onTime(format.format(0.69313213));
        MessageUtil.onTime(format.format(0.6));
        MessageUtil.onTime(format.format(0));
        MessageUtil.onTime(format.format(321653334));

        format = new DecimalFormat("###,##,#.##");
        MessageUtil.onTime("###,##,#.##格式");
        MessageUtil.onTime(format.format(1321322.563213213));
        MessageUtil.onTime(format.format(0.69313213));
        MessageUtil.onTime(format.format(0.6));
        MessageUtil.onTime(format.format(0));
        MessageUtil.onTime(format.format(321653334));

        format = new DecimalFormat("##.0#");
        MessageUtil.onTime("##.0#格式");
        MessageUtil.onTime(format.format(1321322.563213213));
        MessageUtil.onTime(format.format(0.69313213));
        MessageUtil.onTime(format.format(0.6));
        MessageUtil.onTime(format.format(0));
        MessageUtil.onTime(format.format(321654));

        format = new DecimalFormat("#00");
        MessageUtil.onTime("#00格式");
        MessageUtil.onTime(format.format(1321322.563213213));
        MessageUtil.onTime(format.format(0.69313213));
        MessageUtil.onTime(format.format(0.6));
        MessageUtil.onTime(format.format(0));
        MessageUtil.onTime(format.format(321654));

    }


}
