package org.launchcode.budget_planning_backend.models;

import java.util.ArrayList;
import java.util.List;

public class DummyObjectsToBeDeleted {

    private static List<UserGroup> dummyGroups = new ArrayList<>();
    private static List<Chore> dummyChores = new ArrayList<>();
    private static final int OPENED_USER_GROUP_ID = 2;

    private static void setUpDummyUserGroups() {
        UserGroup group1 = new UserGroup("Smiths", "our family");
        UserGroup group2 = new UserGroup("Adams family", "our family");
        group1.setId(1);
        group2.setId(2);
        dummyGroups.add(group1);
        dummyGroups.add(group2);
    }

    public static UserGroup getGroupByName(String groupName) {
        setUpDummyUserGroups();
        for (UserGroup group : dummyGroups) {
            if (group.getName().equalsIgnoreCase(groupName)) {
                return group;
            }
        }
        return null;
    }

    public static void setUpDummyChores() {

    }

    public static int getOpenedUserGroupId() {
        return OPENED_USER_GROUP_ID;
    }
}
