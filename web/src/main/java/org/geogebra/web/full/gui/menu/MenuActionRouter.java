package org.geogebra.web.full.gui.menu;

import java.util.Collections;

import org.geogebra.common.gui.menu.Action;
import org.geogebra.common.gui.menu.ActionableItem;
import org.geogebra.common.gui.menu.MenuItem;
import org.geogebra.common.gui.menu.SubmenuItem;
import org.geogebra.common.main.Localization;
import org.geogebra.web.full.gui.HeaderView;
import org.geogebra.web.full.gui.menu.action.MenuActionHandler;
import org.geogebra.web.html5.gui.FastClickHandler;

import com.google.gwt.user.client.ui.Widget;

class MenuActionRouter {

	private MenuActionHandler menuActionHandler;
	private MenuViewController menuViewController;
	private Localization localization;

	MenuActionRouter(MenuActionHandler menuActionHandler,
					 MenuViewController menuViewController,
					 Localization localization) {
		this.menuActionHandler = menuActionHandler;
		this.menuViewController = menuViewController;
		this.localization = localization;
	}

	void handleMenuItem(MenuItem menuItem) {
		if (menuItem instanceof ActionableItem) {
			handleAction(((ActionableItem) menuItem).getAction());
		} else if (menuItem instanceof SubmenuItem) {
			handleSubmenu((SubmenuItem) menuItem);
		}
	}

	private void handleAction(Action action) {
		menuActionHandler.executeMenuAction(action);
		menuViewController.setMenuVisible(false);
	}

	private void handleSubmenu(SubmenuItem submenuItem) {
		final MenuView menuView = new MenuView();
		menuViewController.setMenuItemGroups(menuView,
				Collections.singletonList(submenuItem.getGroup()));
		HeaderView headerView = menuViewController.createHeaderView();
		headerView.setCaption(localization.getMenu(submenuItem.getLabel()));
		headerView.getBackButton().addFastClickHandler(new FastClickHandler() {
			@Override
			public void onClick(Widget source) {
				menuViewController.hideSubmenu();
			}
		});
		HeaderedMenuView submenu = new HeaderedMenuView(menuView);
		submenu.setHeaderView(headerView);
		menuViewController.showSubmenu(submenu);
	}
}
