package com.spring.common.util;

import org.junit.Test;
import org.springframework.util.StringUtils;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StringUtilsTests {


    /**
     * 检查给定的{@code String}是否包含实际的文本
     * <p>更具体地说，如果* {@code String}不是{@code null}，它的长度大于0，并且它至少包含一个非空白字符，
     * 则此方法返回{@code true}
     */
    @Test
    public void testHasTextBlank() {
        //testHasTextBlank
        String blank = "          ";
        assertEquals(false, StringUtils.hasText(blank));
        //testHasTextNullEmpty
        assertEquals(false, StringUtils.hasText(null));
        assertEquals(false, StringUtils.hasText(""));
        //testHasTextValid
        assertEquals(true, StringUtils.hasText(" t "));
        assertEquals(true, StringUtils.hasText("t"));

    }

    /**
     * 从给定的{@code String}中修剪<i>所有空格
     */
    @Test
    public void testTrimAllWhitespace() {
        assertEquals("", StringUtils.trimAllWhitespace(""));
        assertEquals("", StringUtils.trimAllWhitespace(" "));
        assertEquals("", StringUtils.trimAllWhitespace("\t"));
        assertEquals("a", StringUtils.trimAllWhitespace(" a"));
        assertEquals("a", StringUtils.trimAllWhitespace("a "));
        assertEquals("a", StringUtils.trimAllWhitespace(" a "));
        assertEquals("ab", StringUtils.trimAllWhitespace(" a b "));
        assertEquals("abc", StringUtils.trimAllWhitespace(" a b  c "));
    }


    /**
     * 将逗号分隔列表（例如，CSV文件中的行）转换为字符串数组。
     */
    @Test
    public void testCommaDelimitedListToStringArrayMatchWords() {
        // Could read these from files
        String[] sa = new String[] {"foo", "bar", "big"};
        doTestCommaDelimitedListToStringArrayLegalMatch(sa);
        doTestStringArrayReverseTransformationMatches(sa);

        sa = new String[] {"a", "b", "c"};
        doTestCommaDelimitedListToStringArrayLegalMatch(sa);
        doTestStringArrayReverseTransformationMatches(sa);

        // Test same words
        sa = new String[] {"AA", "AA", "AA", "AA", "AA"};
        doTestCommaDelimitedListToStringArrayLegalMatch(sa);
        doTestStringArrayReverseTransformationMatches(sa);
    }

    private void doTestStringArrayReverseTransformationMatches(String[] sa) {
        String[] reverse =
                StringUtils.commaDelimitedListToStringArray(StringUtils.arrayToCommaDelimitedString(sa));
        assertEquals("Reverse transformation is equal",
                Arrays.asList(sa),
                Arrays.asList(reverse));
    }

    private void doTestCommaDelimitedListToStringArrayLegalMatch(String[] components) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < components.length; i++) {
            if (i != 0) {
                sb.append(",");
            }
            sb.append(components[i]);
        }
        String[] sa = StringUtils.commaDelimitedListToStringArray(sb.toString());
        assertTrue("String array isn't null with legal match", sa != null);
        assertEquals("String array length is correct with legal match", components.length, sa.length);
        assertTrue("Output equals input", Arrays.equals(sa, components));
    }


    @Test
    public void testCleanPath() {
        assertEquals("mypath/myfile", StringUtils.cleanPath("mypath/myfile"));
        assertEquals("mypath/myfile", StringUtils.cleanPath("mypath\\myfile"));
        assertEquals("mypath/myfile", StringUtils.cleanPath("mypath/../mypath/myfile"));
        assertEquals("mypath/myfile", StringUtils.cleanPath("mypath/myfile/../../mypath/myfile"));
        assertEquals("../mypath/myfile", StringUtils.cleanPath("../mypath/myfile"));
        assertEquals("../mypath/myfile", StringUtils.cleanPath("../mypath/../mypath/myfile"));
        assertEquals("../mypath/myfile", StringUtils.cleanPath("mypath/../../mypath/myfile"));
        assertEquals("/../mypath/myfile", StringUtils.cleanPath("/../mypath/myfile"));
        assertEquals("/mypath/myfile", StringUtils.cleanPath("/a/:b/../../mypath/myfile"));
        assertEquals("file:///c:/path/to/the%20file.txt", StringUtils.cleanPath("file:///c:/some/../path/to/the%20file.txt"));
    }
}
