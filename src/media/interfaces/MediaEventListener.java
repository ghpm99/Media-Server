package media.interfaces;

import javafx.beans.value.ChangeListener;
import javafx.util.Duration;

public interface MediaEventListener extends ChangeListener<Duration>{

	public void onReady();
	
	public void onError();
	
	
	
}
