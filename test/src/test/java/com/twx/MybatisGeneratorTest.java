package com.twx;

import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.twx.core.util.FileUtil;
import com.twx.core.util.StringUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * created by tongwenxin on 2018/11/5
 */
public class MybatisGeneratorTest {

    @Test
    public void test001() throws Exception {
        String fileName = "F:\\工作\\天阳\\accumulatePoints\\origin.txt";
        List<String> lines = FileUtil.readLines(fileName);

        StringBuilder builder = new StringBuilder();

        lines.forEach(line -> generatorJavaBean(builder, line));

        builder.append(FileUtil.lineSeparator());
        builder.append(FileUtil.lineSeparator());
        lines.forEach(line -> generatorResultTag(builder, line));

        builder.append(FileUtil.lineSeparator());
        builder.append(FileUtil.lineSeparator());
        lines.forEach(line -> generatorColumnTag(builder, line));

        builder.append(FileUtil.lineSeparator());
        builder.append(FileUtil.lineSeparator());
        lines.forEach(line -> generatorQueryTag(builder, line));

        builder.append(FileUtil.lineSeparator());
        builder.append(FileUtil.lineSeparator());
        lines.forEach(line -> generatorInsertTagPre(builder, line));

        builder.append(FileUtil.lineSeparator());
        builder.append(FileUtil.lineSeparator());
        lines.forEach(line -> generatorInsertTagPost(builder, line));

        builder.append(FileUtil.lineSeparator());
        builder.append(FileUtil.lineSeparator());
        lines.forEach(line -> generatorModifyTag(builder, line));


        FileUtil.writeFile(builder.toString(), "F:\\工作\\天阳\\accumulatePoints\\result.txt", Charsets.UTF_8);
    }

    private void generatorJavaBean(StringBuilder builder, String line) {
        String name = getDBName(line);
        String camelName = StringUtil.underline2camel(name);

        builder.append("    /**")
                .append(FileUtil.lineSeparator())
                .append("     * ")
                .append(getComment(line))
                .append(FileUtil.lineSeparator())
                .append("     */")
                .append(FileUtil.lineSeparator())
                .append("    private ")
                .append(getJavaType(line))
                .append(" ")
                .append(camelName)
                .append(";")
                .append(FileUtil.lineSeparator())
                .append(FileUtil.lineSeparator());
    }

    private void generatorResultTag(StringBuilder builder, String line) {
        String name = getDBName(line);

        builder.append("<result column=\"")
                .append(name)
                .append("\" property=\"")
                .append(StringUtil.underline2camel(name))
                .append("\" jdbcType=\"")
                .append(getDBType(line))
                .append("\"/>")
                .append(FileUtil.lineSeparator());
    }

    private void generatorColumnTag(StringBuilder builder, String line) {
        String name = getDBName(line);

        builder.append("        ")
                .append("a.")
                .append(name)
                .append(',')
                .append(FileUtil.lineSeparator());
    }

    private void generatorQueryTag(StringBuilder builder, String line) {
        String name = getDBName(line);
        String camelName = StringUtil.underline2camel(name);

        builder.append("        <if test=\"")
                .append(camelName)
                .append(" != null\">")
                .append(FileUtil.lineSeparator())
                .append("            and a.")
                .append(name)
                .append(" = #{")
                .append(camelName)
                .append(", jdbcType=")
                .append(getDBType(line))
                .append("}")
                .append(FileUtil.lineSeparator())
                .append("        </if>")
                .append(FileUtil.lineSeparator());
    }

    private void generatorInsertTagPre(StringBuilder builder, String line) {
        String name = getDBName(line);

        builder.append("        ")
                .append(name)
                .append(',')
                .append(FileUtil.lineSeparator());
    }

    private void generatorInsertTagPost(StringBuilder builder, String line) {
        String name = getDBName(line);
        String camelName = StringUtil.underline2camel(name);

        builder.append("        #{")
                .append(camelName)
                .append(", jdbcType=")
                .append(getDBType(line))
                .append("},")
                .append(FileUtil.lineSeparator());
    }

    private void generatorModifyTag(StringBuilder builder, String line) {
        String name = getDBName(line);
        String camelName = StringUtil.underline2camel(name);

        builder.append("            <if test=\"")
                .append(camelName)
                .append(" != null\">")
                .append(FileUtil.lineSeparator())
                .append("               ")
                .append(name)
                .append(" = #{")
                .append(camelName)
                .append(", jdbcType=")
                .append(getDBType(line))
                .append("},")
                .append(FileUtil.lineSeparator())
                .append("           </if>")
                .append(FileUtil.lineSeparator());
    }

    private String getDBName(String line) {
        Iterator<String> iterator = Splitter.on(";").omitEmptyStrings().trimResults().split(line).iterator();
        return iterator.hasNext()? iterator.next() : "";
    }

    private String getDBType(String line) {
        if (line.contains("int")) {
            return "INTEGER";
        }

        if (line.contains("datetime") || line.contains("date")) {
            return "DATE";
        }

        if (line.contains("double")) {
            return "DOUBLE";
        }

        if (line.contains("decimal")) {
            return "DECIMAL";
        }

        return "VARCHAR";
    }

    private String getJavaType(String line) {
        if (line.contains("int")) {
            return "Integer";
        }

        if (line.contains("datetime")) {
            return "LocalDateTime";
        }

        if (line.contains("date")) {
            return "LocalDate";
        }

        if (line.contains("double") || line.contains("decimal")) {
            return "BigDecimal";
        }

        return "String";
    }

    private String getComment(String line) {
        ArrayList<String> list = Lists.newArrayList(Splitter.on('\'').omitEmptyStrings().trimResults().split(line));
        return list.size() > 1 ? list.get(1) : "";
    }

}
