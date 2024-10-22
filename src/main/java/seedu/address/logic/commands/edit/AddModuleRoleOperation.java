package seedu.address.logic.commands.edit;

import java.util.HashMap;

import seedu.address.model.person.ModuleCode;
import seedu.address.model.person.ModuleRoleMap;
import seedu.address.model.person.RoleType;

/**
 * Represents an operation to add a person's module roles.
 */
public class AddModuleRoleOperation extends EditModuleRoleOperation {

    private final ModuleRoleMap moduleRoleMapToAdd;

    /**
     * Constructor for AddModuleRoleOperation.
     * @param moduleRoleMapToAdd ModuleRoleMap to add.
     */
    public AddModuleRoleOperation(ModuleRoleMap moduleRoleMapToAdd) {
        this.moduleRoleMapToAdd = moduleRoleMapToAdd;
    }

    /**
     * Adds module role pairs to a ModuleRoleMap in place.
     * @param moduleRoleMapToEdit
     * @return A ModuleRoleMap of all failed module role pairs.
     */
    @Override
    protected ModuleRoleMap execute(ModuleRoleMap moduleRoleMapToEdit) {
        HashMap<ModuleCode, RoleType> roles = new HashMap<>(moduleRoleMapToEdit.getRoles());
        ModuleRoleMap ret = new ModuleRoleMap(roles);
        ret.putAll(moduleRoleMapToAdd);
        return ret;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AddModuleRoleOperation otherAddModuleRoleOperation)) {
            return false;
        }
        return moduleRoleMapToAdd.equals(otherAddModuleRoleOperation.moduleRoleMapToAdd);
    }

    @Override
    public String toString() {
        return "+ " + moduleRoleMapToAdd.toString();
    }
}
