package gg.steve.mc.splugin.addon;

import gg.steve.mc.splugin.SPlugin;
import gg.steve.mc.splugin.cmd.AbstractCommand;
import gg.steve.mc.splugin.cmd.CommandManager;
import gg.steve.mc.splugin.event.EventManager;
import gg.steve.mc.splugin.manager.Loadable;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public abstract class AbstractAddon implements Loadable {
    private final String identifier;
    private final SPlugin sPlugin;
    private String name;
    private List<Listener> listeners;
    private Map<String, AbstractCommand> commands;

    public AbstractAddon(String identifier) {
        this.identifier = identifier;
        this.sPlugin = SPlugin.getSPluginInstance();
    }

    public void register() {
        this.onLoad();
        if (this.commands != null && !this.commands.isEmpty()) this.commands.forEach((s, command) -> CommandManager.getInstance().registerCommand(command));
        if (this.listeners != null && !this.listeners.isEmpty()) this.listeners.forEach(listener -> EventManager.getInstance().registerListener(listener));
    }

    public void unregister() {
        this.onShutdown();
        if (this.commands != null && !this.commands.isEmpty()) this.commands.clear();
        if (this.listeners != null && !this.listeners.isEmpty()) this.listeners.clear();
    }

    public boolean registerCommand(AbstractCommand command) {
        if (this.commands == null) this.commands = new HashMap<>();
        if (this.commands.containsKey(command.getName())) return false;
        return this.commands.put(command.getName(), command) != null;
    }

    public boolean unregisterCommand(String command) {
        if (this.commands == null || this.commands.isEmpty()) return true;
        if (!this.commands.containsKey(command)) return false;
        return this.commands.remove(command) != null;
    }

    public boolean registerListener(Listener listener) {
        if (this.listeners == null) this.listeners = new ArrayList<>();
        if (this.listeners.contains(listener)) return false;
        return this.listeners.add(listener);
    }

    public boolean unregisterListener(Listener listener) {
        if (this.listeners == null || this.listeners.isEmpty()) return true;
        return this.listeners.remove(listener);
    }

    public abstract String getVersion();

    public abstract String getAuthor();

    public abstract void registerCommands();

    public abstract void registerEvents();

    public abstract void registerFiles();

    public abstract void registerGuis();

    public abstract void registerClickActions();
}