package util;

import com.twx.core.util.Category;
import com.twx.core.util.MessageUtil;
import junit.framework.TestCase;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Created by vincent.tong on 2016/7/14.
 */
public class CategoryTest extends TestCase {

    public void testCategory() throws Exception {
        MessageUtil.onTime(new Category(5.2).equals(new Category(5.20000)));
        MessageUtil.onTime(new Category(5).equals(new Category(new BigInteger("5"))));
        MessageUtil.onTime(new Category(8).equals(new Category(new Short("8"))));
        MessageUtil.onTime(new Category(2).equals(new Category(new BigDecimal("2.000"))));
        MessageUtil.onTime(new Category(9.00).equals(new Category(new BigDecimal("9.0000"))));
        MessageUtil.onTime(new Category("", 9.00).equals(new Category(null, new BigDecimal("9.0000"))));
        MessageUtil.onTime(new Category("here", 9.00).equals(new Category("here", new BigDecimal("9.0000"), "sdf")));
    }

}