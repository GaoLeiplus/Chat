package Finally02;

import java.io.Closeable;
import java.io.IOException;

/*
 * �ر����ķ���
 */
public class CloseUtil {

	
	public static void closeAll(Closeable... io)
	{
		for(Closeable temp:io)
		{
			if(null!=temp)
				try {
					temp.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
}
