package netty.pusher.util.async;

public class AsyncTask {
	
	private IAsyncHandler asyncHandler;
	
	public AsyncTask(IAsyncHandler asyncHandler){
		this.asyncHandler = asyncHandler;
	}
	
	public IAsyncHandler getAsyncHandler() {
		return asyncHandler;
	}

	public void setAsyncHandler(IAsyncHandler asyncHandler) {
		this.asyncHandler = asyncHandler;
	}
	
}
