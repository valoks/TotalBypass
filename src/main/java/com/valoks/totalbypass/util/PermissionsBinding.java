package com.valoks.totalbypass.util;

import com.valoks.totalbypass.TotalBypass;
import com.velocitypowered.api.proxy.Player;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.model.user.UserManager;
import net.luckperms.api.node.Node;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PermissionsBinding {

    private final List<ExternalBinding> bindings = new ArrayList<>();

    public void bind() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        tryBind(LuckPermsBinding.class);
    }

    public boolean addPermission(Player player, String permission) {
        bindings.forEach(binding -> binding.addPermission(player, permission));

        return !bindings.isEmpty();
    }

    public void removePermission(Player player, String permission) {
        bindings.forEach(binding -> binding.removePermission(player, permission));
    }

    private <T extends ExternalBinding> void tryBind(Class<T> binding) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        try {
            // Create a new instance of the binding
            T instance = binding.getDeclaredConstructor().newInstance();

            // Add the binding to the list of bindings
            bindings.add(instance);
        } catch (IllegalStateException e) {
            // Binding is not loaded
        }
    }
}

interface ExternalBinding {
    void addPermission(Player player, String permission);
    void removePermission(Player player, String permission);
}

class LuckPermsBinding implements ExternalBinding {

    private final LuckPerms api;

    public LuckPermsBinding() throws IllegalStateException {
        this.api = LuckPermsProvider.get();

        TotalBypass.INSTANCE.getLogger().info("Bound to LuckPerms");
    }

    @Override
    public void addPermission(Player player, String permission) {
        UserManager userManager = api.getUserManager();

        if(!userManager.isLoaded(player.getUniqueId())) return;

        User user = userManager.getUser(player.getUniqueId());

        if(user == null) return;

        Node node = Node.builder(permission).value(true).build();

        user.data().add(node);

        userManager.saveUser(user);
    }

    @Override
    public void removePermission(Player player, String permission) {
        UserManager userManager = api.getUserManager();

        if(!userManager.isLoaded(player.getUniqueId())) return;

        User user = userManager.getUser(player.getUniqueId());

        if(user == null) return;

        Collection<Node> nodes = user.data().toCollection();

        Node node = nodes.stream().filter(n -> n.getKey().equals(permission)).findFirst().orElse(null);

        if(node == null) return;

        user.data().remove(node);

        userManager.saveUser(user);
    }
}
