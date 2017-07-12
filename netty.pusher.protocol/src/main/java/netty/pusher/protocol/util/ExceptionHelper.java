package netty.pusher.protocol.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public class ExceptionHelper {
	
	private ExceptionHelper(){
		
	}
	
	/**
	 * get exception stack trace
	 * @param e
	 * @return
	 */
	public static String getStackTrace(Throwable e) {
		String stackTrace = "";
		Writer writer = null;
		PrintWriter printWriter = null;
		try {
			writer = new StringWriter();
			printWriter = new PrintWriter(writer);
			e.printStackTrace(printWriter);
			stackTrace = writer.toString();
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			if(printWriter != null) {
				try {
					printWriter.close();
				} catch(Exception ex) {
					ex.printStackTrace();
				}
			}
			
			if(writer != null) {
				try {
					writer.close();
				} catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		
		return stackTrace;
	}
	
}
