package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents a Person's roles in the address book.
 * Guarantees: immutable;
 */
public class Role {
    public static final String MESSAGE_CONSTRAINTS =
            "Role Type can be matched by different kinds of keywords, and is case-insensitive:\n"
            + "1. Tutor: TA, Tutor\n"
            + "2. Professor: Prof, Professor\n"
            + "3. If no role is explicitly provided after the module code, role type will be assumed to be Student";

    private final HashMap<RoleType, ArrayList<String>> roles;

    /**
     * Constructs a {@code Role}.
     *
     * @param roles HashMap representing the roles of a person.
     */
    public Role(HashMap<RoleType, ArrayList<String>> roles) {
        this.roles = roles;
    }

    /**
     * Returns true if a given string is a valid role type.
     */
    public static boolean isValidRoleType(String test) {
        test = test.toLowerCase().trim();
        boolean isStudent = test.isBlank();
        boolean isTutor = test.equals("tutor") || test.equals("ta");
        boolean isProfessor = test.equals("professor") || test.equals("prof");
        return isStudent || isTutor || isProfessor;
    }

    /**
     * Converts String input role type to its corresponding enum type.
     */
    public static RoleType convertRoleInput(String input) {
        input = input.toLowerCase().trim();
        switch(input) {
        case "tutor": // Fall through
        case "ta":
            return RoleType.TUTOR;
        case "professor": // Fall through
        case "prof":
            return RoleType.PROFESSOR;
        case "":
            return RoleType.STUDENT;
        default:
            return RoleType.STUDENT;
        }
    }

    /**
     * Performs deep copy on roles to ensure that Role is immutable.
     */
    private HashMap<RoleType, ArrayList<String>> deepCopyRoles() {
        // Instantiate new HashMap to for copying
        HashMap<RoleType, ArrayList<String>> newRoles = new HashMap<>();

        // Copying the ArrayList of modules for each role
        ArrayList<String> studentRoles = new ArrayList<>(roles.get(RoleType.STUDENT));
        ArrayList<String> tutorRoles = new ArrayList<>(roles.get(RoleType.TUTOR));
        ArrayList<String> professorRoles = new ArrayList<>(roles.get(RoleType.PROFESSOR));

        // Assign copied ArrayList of Modules to each role in the new HashMap
        newRoles.put(RoleType.STUDENT, studentRoles);
        newRoles.put(RoleType.TUTOR, tutorRoles);
        newRoles.put(RoleType.PROFESSOR, professorRoles);
        return newRoles;
    }

    /**
     * Add a new entry to a person's {@code Role}.
     *
     * @param roleType A valid role type keyword.
     */
    public Role add(String module, String roleType) {
        HashMap<RoleType, ArrayList<String>> newRoles = this.deepCopyRoles();

        // Validate the input roleType and module
        requireNonNull(roleType);
        checkArgument(isValidRoleType(roleType), MESSAGE_CONSTRAINTS);

        // Update the new Roles HashMap and create new Role
        RoleType roleTypeEnumValue = convertRoleInput(roleType);
        newRoles.get(roleTypeEnumValue).add(module);
        return new Role(newRoles);
    }

    @Override
    public String toString() {
        StringBuilder roleDescription = new StringBuilder();
        for (RoleType role: roles.keySet()) {
            // Add header for the role, such as Tutor of...
            roleDescription.append(String.format("%s of: ", role));
            // Add modules as comma separated String
            ArrayList<String> modules = roles.get(role);
            for (int i = 0; i < modules.size(); i++) {
                roleDescription.append(modules.get(i));
                if (i < modules.size() - 1) {
                    roleDescription.append(", ");
                }
            }
            // Add new line after finishing each role
            roleDescription.append("\n");
        }
        return roleDescription.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Role)) {
            return false;
        }
        Role otherRole = (Role) other;
        return this.roles.equals(otherRole.roles);
    }

    @Override
    public int hashCode() {
        return roles.hashCode();
    }
}
