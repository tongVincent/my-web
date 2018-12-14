package com.twx.org.apache.commons;

import com.google.common.base.Charsets;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.Range;
import com.google.common.collect.Sets;
import com.twx.BaseTest;
import com.twx.core.util.BigDecimalUtil;
import com.twx.core.util.Category;
import com.twx.core.util.FileUtil;
import com.twx.core.util.json.GsonUtil;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class CSVTest extends BaseTest {

    private static String[] BANKS = new String[]{
            "山东",
            "福建",
            "厦门",
            "河北",
            "河南",
            "宁波",
            "重庆",
            "湖南",
            "湖北",
            "海南",
            "江西",
            "黑龙江",
            "天津",
            "陕西",
            "贵州",
            "新疆",
            "青岛",
            "大连",
            "江苏",
            "安徽",
            "西藏",
            "苏州",
            "吉林",
            "上海",
            "山西",
            "甘肃",
            "宁夏",
            "四川",
            "广西",
            "浙江",
            "深圳",
            "云南",
            "内蒙古",
            "辽宁",
            "广东",
            "青海",
            "北京",
    };

    /**
     * 分行交易
     * @throws Exception
     */
    @Test
    public void test001() throws Exception {
        Multimap<Integer, String[]> lines = ArrayListMultimap.create();
        readFile("F:\\code\\tansun\\echarts\\tmp_fund_tx_oneday.csv", lines, true);

        for (int i = 0; i < 24; i++) {
            Map<Category, String[]> oldMap = Maps.newHashMap();

            for (String[] line : lines.get((2 * i) % 48)) {
                String begin = line[0];
                String end = line[1];
                String money = new BigDecimal(line[2]).divide(BigDecimal.valueOf(2)).toString();
                String[] newLine = {begin, end, money, "-1"};
                lines.put(2 * i + 1, newLine);
                oldMap.put(new Category(begin, end), newLine);
            }

            for (String[] line : lines.get((2 * i + 2) % 48)) {
                String begin = line[0];
                String end = line[1];
                String money = new BigDecimal(line[2]).divide(BigDecimal.valueOf(2)).toString();

                Category key = new Category(begin, end);
                if (oldMap.containsKey(key)) {
                    String[] oldLine = oldMap.get(key);
                    oldLine[2] = new BigDecimal(oldLine[2]).add(new BigDecimal(money)).toString();
                    oldLine[3] = "1";
                } else {
                    String[] newLine = {begin, end, money, "-1"};
                    lines.put(2 * i + 1, newLine);
                }
            }
        }

        Multimap<Integer, String[]> haiwai = ArrayListMultimap.create();
        readFile("F:\\code\\tansun\\echarts\\haiwai.csv", haiwai, false);
        for (int i = 0; i < 48; i++) {
            lines.putAll(i, haiwai.get(0));
        }

        FileUtil.writeFile("var data = " + GsonUtil.toJson(lines.asMap()), "F:\\code\\tansun\\echarts\\lines.js1", Charsets.UTF_8);
    }

    private void readFile(String fileName, Multimap<Integer, String[]> lines, boolean needDouble) throws IOException {
        try (InputStream in = new FileInputStream(fileName);
             InputStreamReader reader = new InputStreamReader(in, "utf-8");
             CSVParser parse = CSVFormat.EXCEL.parse(reader);
        ) {

            for (CSVRecord record : parse) {
                String source = record.get(0);
                String target = record.get(1);
                String money = record.get(3);
                if (Objects.equals(source, "其他")
                        || Objects.equals(target, "其他")
                        || Objects.equals(source, target)
                        || Objects.equals(money, "0")) {
                    continue;
                }

                String[] line = new String[]{source, target, money, "1"};
                int multiple = needDouble ? 2 : 1;
                lines.put(Integer.valueOf(record.get(2)) * multiple, line);
            }
        }
    }

    @Test
    public void test002() throws Exception {
        Map<String, int[]> result = Maps.newHashMap();

        readFile("F:\\code\\tansun\\echarts\\tmp_fund_tx_oneday.csv", result);
//        readFile("F:\\code\\tansun\\echarts\\haiwai.csv", result);

        FileUtil.writeFile(GsonUtil.toJson(result), "F:\\code\\tansun\\echarts\\cords.js1", Charsets.UTF_8);
    }

    private void readFile(String fileName, Map<String, int[]> result) throws IOException {
        try (InputStream in = new FileInputStream(fileName);
             InputStreamReader reader = new InputStreamReader(in, "utf-8");
             CSVParser parse = CSVFormat.EXCEL.parse(reader);
        ) {
            Set<String> citySet = Sets.newHashSet();
            for (CSVRecord record : parse) {
                String source = record.get(0);
                String target = record.get(1);
                if (Objects.equals(source, "其他")
                        || Objects.equals(target, "其他")) {
                    continue;
                }
                citySet.add(source);
                citySet.add(target);
            }

            for (String city : citySet) {
                result.put(city, new int[]{0, 0});
            }
        }
    }

    /**
     * 不累加
     */
    @Test
    public void test003() throws Exception {
        Multimap<Integer, String> lines = ArrayListMultimap.create();

        try (InputStream in = new FileInputStream("F:\\code\\tansun\\echarts\\4jiao.csv");
             InputStreamReader reader = new InputStreamReader(in, "utf-8");
             CSVParser parse = CSVFormat.EXCEL.parse(reader);
        ) {

            for (CSVRecord record : parse) {
                Integer key = Integer.valueOf(record.get(0));
                lines.put(key, record.get(1));
                lines.put(key, record.get(2));
                lines.put(key, record.get(3));
                lines.put(key, record.get(4));
            }
        }

        FileUtil.writeFile(GsonUtil.toJson(lines.asMap()), "F:\\code\\tansun\\echarts\\4jiao.js1", Charsets.UTF_8);
    }

    /**
     * 对于test003 累加
     *
     * @throws Exception
     */
    @Test
    public void test004() throws Exception {
        Multimap<Integer, String> lines = ArrayListMultimap.create();
        Map<Integer, BigDecimal> rightTopData = Maps.newHashMap();

        try (InputStream in = new FileInputStream("F:\\code\\tansun\\echarts\\4jiao.csv");
             InputStreamReader reader = new InputStreamReader(in, "utf-8");
             CSVParser parse = CSVFormat.EXCEL.parse(reader);
        ) {

            BigDecimal num1 = BigDecimal.ZERO;
            BigDecimal num2 = BigDecimal.ZERO;
            BigDecimal num3 = BigDecimal.ZERO;
            BigDecimal num4 = BigDecimal.ZERO;

            for (CSVRecord record : parse) {
                Integer key = Integer.valueOf(record.get(0));
                BigDecimal add1 = new BigDecimal(record.get(1));
                BigDecimal add2 = new BigDecimal(record.get(2));
                BigDecimal add3 = new BigDecimal(record.get(3));
                BigDecimal add4 = new BigDecimal(record.get(4));
                num1 = num1.add(add1);
                num2 = num2.add(add2);
                num3 = num3.add(add3);
                num4 = num4.add(add4);
                lines.put(key, num1.toString());
                lines.put(key, num2.toString());
                lines.put(key, num3.toString());
                lines.put(key, num4.toString());
                rightTopData.put(key, num1.divide(new BigDecimal(100000)));
            }
        }

        FileUtil.writeFile("var rightTopData = " + GsonUtil.toJson(rightTopData), "F:\\code\\tansun\\echarts\\右上角.js2", Charsets.UTF_8);
    }

    @Test
    public void test005() throws Exception {
        Multimap<Integer, String[]> lines = ArrayListMultimap.create();
        readFile("F:\\code\\tansun\\echarts\\tmp_fund_tx_oneday.csv", lines, false);

        Multiset<Range<BigDecimal>> set = HashMultiset.create();

        for (String[] line : lines.get(0)) {
            set.add(getRange(new BigDecimal(line[2])));
        }

        List<Object[]> result = Lists.newArrayList();
        for (Range<BigDecimal> range : set.elementSet()) {
            result.add(new Object[]{range, BigDecimal.valueOf(set.count(range))});
        }


        FileUtil.writeFile(GsonUtil.toJson(result), "F:\\code\\tansun\\echarts\\sandian.js", Charsets.UTF_8);
    }

    private static List<Range<BigDecimal>> ranges = Lists.<Range<BigDecimal>>newArrayList(
            Range.closedOpen(BigDecimal.valueOf(0), BigDecimal.valueOf(5000)),
            Range.closedOpen(BigDecimal.valueOf(5000), BigDecimal.valueOf(500000)),
            Range.closedOpen(BigDecimal.valueOf(500000), BigDecimal.valueOf(1000000)),
//            Range.closedOpen(BigDecimal.valueOf(20000), BigDecimal.valueOf(30000)),
//            Range.closedOpen(BigDecimal.valueOf(30000), BigDecimal.valueOf(40000)),
//            Range.closedOpen(BigDecimal.valueOf(40000), BigDecimal.valueOf(50000)),
//            Range.closedOpen(BigDecimal.valueOf(50000), BigDecimal.valueOf(60000)),
//            Range.closedOpen(BigDecimal.valueOf(60000), BigDecimal.valueOf(70000)),
//            Range.closedOpen(BigDecimal.valueOf(70000), BigDecimal.valueOf(80000)),
//            Range.closedOpen(BigDecimal.valueOf(80000), BigDecimal.valueOf(90000)),
//            Range.closedOpen(BigDecimal.valueOf(90000), BigDecimal.valueOf(100000)),
//            Range.closedOpen(BigDecimal.valueOf(10000), BigDecimal.valueOf(100000)),
            Range.greaterThan(BigDecimal.valueOf(1000000))
    );

    private Range<BigDecimal> getRange(BigDecimal num) {
        for (Range<BigDecimal> range : ranges) {
            if (range.contains(num)) {
                return range;
            }
        }
        return null;
    }

    /**
     * 左上角
     *
     * @throws Exception
     */
    @Test
    public void test006() throws Exception {
        Multimap<Integer, String[]> lines = ArrayListMultimap.create();
        readFile("F:\\code\\tansun\\echarts\\tmp_fund_tx_oneday.csv", lines, false);

        Multimap<Integer, Object[]> zuoShangJiao = ArrayListMultimap.create();
        Map<Integer, Map<String, BigDecimal>> zuoXiaJiao = Maps.newHashMap();
        BigDecimal div = BigDecimal.valueOf(100000000);


        for (Integer key : lines.keySet()) {
            Multimap<String, BigDecimal> incomeMap = ArrayListMultimap.create(); // 收入
            Multimap<String, BigDecimal> expenditureMap = ArrayListMultimap.create(); // 支出

            lines.get(key).forEach(line -> {
                BigDecimal money = new BigDecimal(line[2]);
                incomeMap.put(line[1], money);
                expenditureMap.put(line[0], money.negate());
            });

            BigDecimal maxIncome = BigDecimal.ZERO;
            BigDecimal maxExpenditure = BigDecimal.ZERO;
            Map<String, BigDecimal> bankIncome = Maps.newHashMap();
            for (String bank : BANKS) {
                BigDecimal income = incomeMap.get(bank).stream()
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal expenditure = expenditureMap.get(bank).stream()
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                maxIncome = maxIncome.max(income);
                maxExpenditure = maxExpenditure.min(expenditure);

                bankIncome.put(bank, income);
                zuoShangJiao.put(key, new Object[]{bank, BigDecimalUtil.round(income.divide(div), 2), BigDecimalUtil.round(expenditure.divide(div), 2), BigDecimalUtil.round(income.add(expenditure).divide(div), 2)});
            }

            System.out.println(maxIncome);
            System.out.println(maxExpenditure);

            Map<String, BigDecimal> data = Maps.newHashMap();
            data.put("长三角", (bankIncome.get("上海")
                    .add(bankIncome.get("江苏"))
                    .add(bankIncome.get("苏州"))
                    .add(bankIncome.get("浙江"))
                    .add(bankIncome.get("宁波"))).divide(BigDecimal.valueOf(20)));

            data.put("珠三角", (bankIncome.get("福建")
                    .add(bankIncome.get("厦门"))
                    .add(bankIncome.get("广东"))
                    .add(bankIncome.get("深圳"))));

            data.put("环渤海", (bankIncome.get("北京")
                    .add(bankIncome.get("天津"))
                    .add(bankIncome.get("河北"))
                    .add(bankIncome.get("山东"))
                    .add(bankIncome.get("青岛"))));

            data.put("东北地区", (bankIncome.get("辽宁")
                    .add(bankIncome.get("大连"))
                    .add(bankIncome.get("吉林"))
                    .add(bankIncome.get("黑龙江"))));

            data.put("中部地区", (bankIncome.get("山西")
                    .add(bankIncome.get("安徽"))
                    .add(bankIncome.get("江西"))
                    .add(bankIncome.get("河南"))
                    .add(bankIncome.get("湖北"))
                    .add(bankIncome.get("湖南"))
                    .add(bankIncome.get("广西"))
                    .add(bankIncome.get("海南"))));

            data.put("西部地区", (bankIncome.get("重庆")
                    .add(bankIncome.get("四川"))
                    .add(bankIncome.get("贵州"))
                    .add(bankIncome.get("云南"))
                    .add(bankIncome.get("西藏"))
                    .add(bankIncome.get("陕西"))
                    .add(bankIncome.get("甘肃"))
                    .add(bankIncome.get("青海"))
                    .add(bankIncome.get("宁夏"))
                    .add(bankIncome.get("新疆"))
                    .add(bankIncome.get("内蒙古"))));


            zuoXiaJiao.put(key, data);

        }



        FileUtil.writeFile("var leftTopData = " + GsonUtil.toJson(zuoShangJiao.asMap()), "F:\\code\\tansun\\echarts\\左上角.js1", Charsets.UTF_8);
        FileUtil.writeFile("var leftBottomData = " + GsonUtil.toJson(zuoXiaJiao), "F:\\code\\tansun\\echarts\\左下角.js1", Charsets.UTF_8);
    }

    /**
     * 左下角
     *
     * @throws Exception
     */
    @Test
    public void test007() throws Exception {
        Multimap<Integer, String[]> lines = ArrayListMultimap.create();
        readFile("F:\\code\\tansun\\echarts\\tmp_fund_tx_oneday.csv", lines, false);

        Map<Integer, Map<String, BigDecimal>> zuoXiaJiao = Maps.newHashMap();
        for (Integer key : lines.keySet()) {
            Multimap<String, BigDecimal> incomeMap = ArrayListMultimap.create(); // 收入

            lines.get(key).forEach(line -> {
                if (!Area.isSameArea(line[0], line[1])) {
                    BigDecimal money = new BigDecimal(line[2]);
                    incomeMap.put(line[1], money);
                }
            });

            Map<String, BigDecimal> bankIncome = Maps.newHashMap();
            for (String bank : BANKS) {
                BigDecimal income = incomeMap.get(bank).stream()
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                bankIncome.put(bank, income);
            }

            Map<String, BigDecimal> data = Maps.newHashMap();
            data.put("长三角", (bankIncome.get("上海")
                    .add(bankIncome.get("江苏"))
                    .add(bankIncome.get("苏州"))
                    .add(bankIncome.get("浙江"))
                    .add(bankIncome.get("宁波"))).divide(BigDecimal.valueOf(1)));

            data.put("珠三角", (bankIncome.get("福建")
                    .add(bankIncome.get("厦门"))
                    .add(bankIncome.get("广东"))
                    .add(bankIncome.get("深圳"))));

            data.put("环渤海", (bankIncome.get("北京")
                    .add(bankIncome.get("天津"))
                    .add(bankIncome.get("河北"))
                    .add(bankIncome.get("山东"))
                    .add(bankIncome.get("青岛"))));

            data.put("东北地区", (bankIncome.get("辽宁")
                    .add(bankIncome.get("大连"))
                    .add(bankIncome.get("吉林"))
                    .add(bankIncome.get("黑龙江"))));

            data.put("中部地区", (bankIncome.get("山西")
                    .add(bankIncome.get("安徽"))
                    .add(bankIncome.get("江西"))
                    .add(bankIncome.get("河南"))
                    .add(bankIncome.get("湖北"))
                    .add(bankIncome.get("湖南"))
                    .add(bankIncome.get("广西"))
                    .add(bankIncome.get("海南"))));

            data.put("西部地区", (bankIncome.get("重庆")
                    .add(bankIncome.get("四川"))
                    .add(bankIncome.get("贵州"))
                    .add(bankIncome.get("云南"))
                    .add(bankIncome.get("西藏"))
                    .add(bankIncome.get("陕西"))
                    .add(bankIncome.get("甘肃"))
                    .add(bankIncome.get("青海"))
                    .add(bankIncome.get("宁夏"))
                    .add(bankIncome.get("新疆"))
                    .add(bankIncome.get("内蒙古"))));

            zuoXiaJiao.put(key, data);
        }

        FileUtil.writeFile("var leftBottomData = " + GsonUtil.toJson(zuoXiaJiao), "F:\\code\\tansun\\echarts\\左下角.js1", Charsets.UTF_8);
    }


    public static enum Area {
        长三角("上海", "江苏", "苏州", "浙江", "宁波"),
        珠三角("福建", "厦门", "广东", "深圳"),
        环渤海("北京", "天津", "河北", "山东", "青岛"),
        东北地区("辽宁", "大连", "吉林", "黑龙江"),
        中部地区("山西", "安徽", "江西", "河南", "湖北", "湖南", "广西", "海南"),
        西部地区("重庆", "四川", "贵州", "云南", "西藏", "陕西", "甘肃", "青海", "宁夏", "新疆", "内蒙古"),
        ;

        private List<String> cities;

        Area(String... cities) {
            this.cities = Lists.newArrayList(cities);
        }

        public static Area parse(String city) {
            for (Area area : values()) {
                if (area.cities.contains(city)) {
                    return area;
                }
            }

            return null;
        }

        public static boolean isSameArea(String left, String right) {
            return Objects.equals(parse(left), parse(right));
        }

    }


}
