import static org.junit.Assert.*;

import org.ccps.common.util.Eryptogram;
import org.junit.Test;

public class EcpTest {

	@Test
	public void test() throws Exception {
		System.out.println(Eryptogram.decryptOp("362662BFCA76E9D9C9C7688F184730AA", "ourhooha"));
	}

}
