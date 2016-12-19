package io.jpress.listener;

import java.util.List;

import io.jpress.menu.MenuGroup;
import io.jpress.menu.MenuItem;
import io.jpress.menu.MenuManager;
import io.jpress.message.Message;
import io.jpress.message.MessageListener;
import io.jpress.message.annotation.Listener;
import io.jpress.template.Template;
import io.jpress.template.TemplateManager;
import io.jpress.template.TplModule;
import io.jpress.utils.StringUtils;

/**
 * Created by dengwei06015 on 2016/12/14.
 */
@Listener(action = MenuManager.EDITOR_INIT_MENU, async = false)
public class MenuInitListener implements MessageListener {

	@Override
	public void onMessage(Message message) {
		Object temp = message.getData();
		if (temp == null && !(temp instanceof MenuManager)) {
			return;
		}

		MenuManager menuMnager = (MenuManager) temp;
		initModuleMenuGroup(menuMnager);
	}

	public void initModuleMenuGroup(MenuManager menuMnager) {
		Template t = TemplateManager.me().currentTemplate();
		if (t == null || t.getModules() == null) {
			return;
		}

		List<TplModule> modules = t.getModules();
		for (TplModule module : modules) {
			String iconClass = module.getIconClass();
			if (StringUtils.isBlank(iconClass)) {
				iconClass = "fa fa-file-text-o";
			}
			MenuGroup group = new MenuGroup(module.getName(), iconClass, module.getTitle());

			group.addMenuItem(new MenuItem("list", "/admin/content?m=" + module.getName(), module.getListTitle()));
			group.addMenuItem(new MenuItem("edit", "/admin/content/edit?m=" + module.getName(), module.getAddTitle()));

			menuMnager.addMenuGroup(group);
		}
	}

}
