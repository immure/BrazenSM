package co.uk.brazen.web.pages;

import com.vaadin.ui.Panel;

public interface Page<T> {
	
	public void draw(Panel panel, T data);

}
