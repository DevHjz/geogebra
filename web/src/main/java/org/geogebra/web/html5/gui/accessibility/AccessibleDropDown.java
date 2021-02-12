package org.geogebra.web.html5.gui.accessibility;

import java.util.Arrays;
import java.util.List;

import org.geogebra.common.kernel.StringTemplate;
import org.geogebra.common.kernel.geos.GeoElement;
import org.geogebra.common.kernel.geos.GeoList;
import org.geogebra.common.kernel.geos.ScreenReaderBuilder;
import org.geogebra.web.html5.gui.util.ListItem;
import org.geogebra.web.html5.gui.util.UnorderedList;
import org.geogebra.web.html5.gui.view.button.StandardButton;
import org.geogebra.web.html5.main.AppW;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import elemental2.dom.DomGlobal;
import jsinterop.base.Js;

public class AccessibleDropDown implements AccessibleWidget {
	private final GeoList list;
	private final AppW app;
	private StandardButton button;
	private UnorderedList options;
	private Label label;

	public AccessibleDropDown(GeoList geo, AppW app, AccessibilityView view) {
		this.list = geo;
		this.app = app;

		button = new StandardButton(null, "", 0, 0);
		button.getElement().setAttribute("aria-haspopup", "listbox");
		options = new UnorderedList();
		options.getElement().setAttribute("role", "listbox");
		options.getElement().setTabIndex(-1);
		label = new Label();
		String labelId = DOM.createUniqueId();
		label.getElement().setId(labelId);
		String buttonId = DOM.createUniqueId();
		button.getElement().setId(buttonId);
		button.getElement().setAttribute("aria-labeledby", labelId + " " + buttonId);

		options.setVisible(false);
		button.addFastClickHandler(e -> {
			options.setVisible(!options.isVisible());
			if (options.isVisible()) {
				view.show();
				options.getElement().focus();
			}
		});
		update();
	}

	@Override
	public List<? extends Widget> getWidgets() {
		return Arrays.asList(label, button, options);
	}

	@Override
	public void update() {
		ScreenReaderBuilder sb = new ScreenReaderBuilder();
		list.addAuralCaption(sb);
		label.setText(sb.toString());
		button.setText(list.getSelectedItemDisplayString(StringTemplate.screenReader));
		options.clear();
		for (int i = 0; i < list.size(); i++) {
			ListItem option = new ListItem();
			String optionId = DOM.createUniqueId();
			option.getElement().setId(optionId);
			if (i == list.getSelectedIndex()) {
				options.getElement().setAttribute("aria-activedescendant", optionId);
			}
			option.setText(list.getItemDisplayString(i, StringTemplate.screenReader));
			option.getElement().setAttribute("role", "option");
			final int idx = i;
			option.addDomHandler(e -> list.setSelectedIndex(idx, true),
					ClickEvent.getType());
			options.add(option);
		}
	}

	@Override
	public void setFocus(boolean focus) {
		if (focus && !hasFocus()) {
			//button.getElement().focus();
		}
	}

	@Override
	public boolean isCompatible(GeoElement geo) {
		return geo instanceof GeoList && ((GeoList) geo).drawAsComboBox();
	}

	private boolean hasFocus() {
		Element active = Js.uncheckedCast(DomGlobal.document.activeElement);
		return options.getElement().isOrHasChild(active)
				|| button.getElement().isOrHasChild(active)
				|| label.getElement().isOrHasChild(active);
	}
}
